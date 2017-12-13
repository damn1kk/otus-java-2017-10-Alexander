package ru.otus.homework08;

import java.lang.reflect.Field;

public class ReflectionHelper {
    private ReflectionHelper(){}

    public static Field[] getAllFields(Object object){
        Class clazz = object.getClass();
        return clazz.getDeclaredFields();
    }


    public static Object getFieldValue(Field field, Object sourceObject) throws IllegalAccessException{
        boolean defaultAccessible = field.isAccessible();
        try{
            field.setAccessible(true);
            return field.get(sourceObject);
        }catch(IllegalAccessException ex) {
            ex.printStackTrace();
            throw ex;
        }finally {
            field.setAccessible(defaultAccessible);
        }
    }
}
