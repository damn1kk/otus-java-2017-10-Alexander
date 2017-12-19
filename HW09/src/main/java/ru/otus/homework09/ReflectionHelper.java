package ru.otus.homework09;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectionHelper {

    public static <T> Constructor<T> getConstructorWithFullParameters(Class<T> clazz) throws NoSuchMethodException{
        Field[] allFields = getAllFields(clazz);

        Class<?>[] fieldTypes = new Class<?>[allFields.length];
        for(int i = 0; i < fieldTypes.length; i++){
            fieldTypes[i] = allFields[i].getType();
        }

        return clazz.getConstructor(fieldTypes);
    }

    public static Field[] getAllFields(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        Field[] fieldsSuperClass = clazz.getSuperclass().getDeclaredFields();
        Field[] resultFields = new Field[fields.length + fieldsSuperClass.length];
        System.arraycopy(fieldsSuperClass, 0, resultFields, 0, fieldsSuperClass.length);
        System.arraycopy(fields, 0, resultFields, fieldsSuperClass.length, fields.length);
        return resultFields;
    }

    public static <T> T newInstanceWithParameters(Class<T> clazz, Object[] parameters) throws
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException{

        Constructor<T> constructor = ReflectionHelper.getConstructorWithFullParameters(clazz);
        return constructor.newInstance(parameters);
    }

    public static String getFieldsValueToString(Object user){
        Class<?> clazz = user.getClass();
        Field[] fields = ReflectionHelper.getAllFields(clazz);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < fields.length; i++){

            Object fieldValue;
            try {
                fieldValue = getFieldValue(fields[i], user);
            }catch(IllegalAccessException ex){
                System.err.println("Can not get field value");
                throw new RuntimeException(ex);
            }
            Class<?> fieldType = fields[i].getType();

            String fieldValueToString;
            if(fieldType == String.class || fieldType == Character.class){
                fieldValueToString = "'" + fieldValue + "'";
            }else{
                fieldValueToString = fieldValue.toString();
            }

            if(i + 1 == fields.length){
                sb.append(fieldValueToString).append(");");
            }else{
                sb.append(fieldValueToString).append(", ");
            }
        }
        return sb.toString();
    }

    public static Object getFieldValue(Field field, Object object) throws IllegalAccessException{
        boolean defaultAccessible = field.isAccessible();
        if(!defaultAccessible){
            field.setAccessible(true);
        }
        Object valueObject = null;
        try {
            valueObject = field.get(object);
        }catch(IllegalAccessException ex){
            throw ex;
        }finally{
            field.setAccessible(defaultAccessible);
        }
        return valueObject;
    }
}
