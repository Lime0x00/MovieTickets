package com.example.views.cli;

import java.lang.reflect.Method;

public class View {

    protected void invokeMethod(Class<?> clazz, String methodName) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            Method method = clazz.getMethod(methodName);

            method.invoke(instance);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
