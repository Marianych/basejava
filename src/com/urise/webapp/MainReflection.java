package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume();

        Field field = r.getClass().getDeclaredFields()[0];
        System.out.println(field.getName());
        field.setAccessible(true);
        System.out.println(field.get(r));
        field.set(r, "new uuid");
        //TODO invoke r.toString via reflection
        System.out.println(r);

        Method tostring = r.getClass().getDeclaredMethods()[1];
        try {
            System.out.println(tostring.invoke(r));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
