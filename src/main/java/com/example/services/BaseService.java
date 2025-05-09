package com.example.services;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseService<T> {
    private static final EntityManagerFactory emf = buildEntityManagerFactory();
    private static final String persistenceUnitName = "default";
    protected final EntityManager em;
    private final Class<T> type;

    public BaseService(Class<T> type) {
        this.type = type;
        this.em = emf.createEntityManager();
    }

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory(persistenceUnitName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize EntityManagerFactory", e);
        }
    }

    public T findById(Object id) {
        return em.find(type, id);
    }

    public List<T> findAll() {
        String jpql = "SELECT t FROM " + type.getSimpleName() + " t";
        return em.createQuery(jpql, type).getResultList();
    }

    public List<T> findAllWhere(String condition, Map<String, Object> params) {
        String jpql = "SELECT t FROM " + type.getSimpleName() + " t WHERE " + condition;
        TypedQuery<T> query = em.createQuery(jpql, type);
        params.forEach(query::setParameter);
        return query.getResultList();
    }

    public boolean exists(Object id) {
        return findById(id) != null;
    }

    public boolean save(T entity) {
        try {
            return callInTransaction(em -> {
                em.persist(entity);
                return true;
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean update(T entity) {
        try {
            return callInTransaction(em -> {
                em.merge(entity);
                return true;
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(T entity) {
        try {
            return callInTransaction(em -> {
                em.remove(em.contains(entity) ? entity : em.merge(entity));
                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Object id) {
        T entity = findById(id);
        return entity != null && delete(entity);
    }

    public void close() {
        if (em.isOpen()) em.close();
    }

    protected void runInTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    protected <R> R callInTransaction(Function<EntityManager, R> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            R result = action.apply(em);
            tx.commit();
            return result;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
