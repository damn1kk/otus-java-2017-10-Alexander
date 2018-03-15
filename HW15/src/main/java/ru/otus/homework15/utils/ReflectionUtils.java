package ru.otus.homework15.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReflectionUtils {

    private static final int RANDOM_STRING_LENGTH = 5;
    private static final int RANDOM_NUMERIC_UPPER_LIMIT = 10;
    private static final Random random = new Random();

    public static Field[] getAllFields(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        Field[] fieldsSuperClass = clazz.getSuperclass().getDeclaredFields();
        Field[] resultFields = new Field[fields.length + fieldsSuperClass.length];
        System.arraycopy(fieldsSuperClass, 0, resultFields,0, fieldsSuperClass.length);
        System.arraycopy(fields, 0, resultFields, fieldsSuperClass.length, fields.length);
        return resultFields;
    }

    public static <T> Constructor<T> getConstructorWithFullParameters(Class<T> clazz) throws NoSuchMethodException{
        Field[] allFields = getAllFields(clazz);

        Class<?>[] fieldTypes = new Class<?>[allFields.length];
        for(int i = 0; i < fieldTypes.length; i++){
            fieldTypes[i] = allFields[i].getType();
        }

        return clazz.getConstructor(fieldTypes);
    }

    public static <T> Constructor[] getConstructorsWithPrimitiveParameters(Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        List<Constructor> resultList = new ArrayList<>();

        for(Constructor c : constructors){
            boolean constructorWithPrimitives = true;
            Object[] array = c.getParameterTypes();

            for(Object o : array){
                if(!(o.equals(java.lang.String.class) || o.equals(short.class) || o.equals(int.class) ||
                        o.equals(long.class) || o.equals(boolean.class) || o.equals(float.class) ||
                        o.equals(double.class))){
                    constructorWithPrimitives = false;
                }
            }
            if(constructorWithPrimitives){
                resultList.add(c);
            }
        }

        return resultList.toArray(constructors);
    }

    public static <T> Constructor<T> getConstructorWithLotsOfParameters(Constructor<T>[] constructors){
        Constructor<T> constructorWithLotsOfParameters = constructors[0];
        for(Constructor<T> c : constructors){
            if(c.getParameterTypes().length > constructorWithLotsOfParameters.getParameterTypes().length){
                constructorWithLotsOfParameters = c;
            }
        }
        return constructorWithLotsOfParameters;
    }

    public static <T> T newInstanceWithIdAndRandomPrimitiveParameters(final Class<T> clazz, Number id) throws
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException{
        Constructor<T>[] constructors = ReflectionUtils.getConstructorsWithPrimitiveParameters(clazz);
        if(constructors.length > 0){
            Constructor<T> constructor = ReflectionUtils.getConstructorWithLotsOfParameters(constructors);

            Class<?>[] types = constructor.getParameterTypes();
            Object[] parameters = new Object[types.length];
            boolean firstNumber = true;
            for(int i = 0; i < types.length; i++){
                if(types[i].equals(int.class) || types[i].equals(Integer.class) ||
                        types[i].equals(long.class) || types[i].equals(Long.class) ||
                        types[i].equals(short.class) || types[i].equals(Short.class)){
                    if(firstNumber){
                        parameters[i] = id;
                        firstNumber = false;
                    }else {
                        parameters[i] = random.nextInt(RANDOM_NUMERIC_UPPER_LIMIT);
                    }
                }else{
                    parameters[i] = StringUtils.generateRandomString(RANDOM_STRING_LENGTH);
                }
            }
            return constructor.newInstance(parameters);
        }else{
            throw new NoSuchMethodException();
        }
    }

    public static <T> T newInstanceEmptyConstructor(final Class<T> clazz) throws
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException{

        Constructor<T> constructor = clazz.getConstructor();
        return constructor.newInstance();
    }

    public static <T> T changeSomeStringField(T object, String newStringValue) throws IllegalAccessException, NoSuchMethodException {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field f : fields){
            if(f.getType().equals(java.lang.String.class)){
                f.setAccessible(true);
                f.set(object, newStringValue);
                f.setAccessible(false);
                return object;
            }
        }
        throw new NoSuchMethodException();
    }
}
