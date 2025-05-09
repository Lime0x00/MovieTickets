package com.example.models;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "customer", schema = "MovieTickets1")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @OneToMany(mappedBy = "customer")
    private Set<com.example.models.Receipt> receipts = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<com.example.models.Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(Set<com.example.models.Receipt> receipts) {
        this.receipts = receipts;
    }

    
    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", age=" + age + ", name=" + name + '}';
    }
}