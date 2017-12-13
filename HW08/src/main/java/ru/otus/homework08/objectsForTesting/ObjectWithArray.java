package ru.otus.homework08.objectsForTesting;

import java.util.Arrays;

public class ObjectWithArray {
    private int intField;
    private int[] intArray;

    private String stringField;
    private String[] stringArray;

    private SimpleObject objectField;
    private SimpleObject[] objectArray;

    public ObjectWithArray(int intField, int[] intArray, String stringField, String[] stringArray,
                           SimpleObject objectField, SimpleObject[] objectArray) {
        this.intField = intField;
        this.intArray = intArray;
        this.stringField = stringField;
        this.stringArray = stringArray;
        this.objectField = objectField;
        this.objectArray = objectArray;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof ObjectWithArray))
            return false;

        ObjectWithArray that = (ObjectWithArray)obj;
        if(intField != that.intField)
            return false;
        if(!Arrays.equals(intArray, that.intArray))
            return false;

        if(stringField == null){
            if(that.stringField != null)
                return false;
        }else {
            if (!stringField.equals(that.stringField))
                return false;
        }

        if(!Arrays.equals(stringArray, that.stringArray))
            return false;

        if(objectField == null){
            if(that.objectField != null)
                return false;
        }else {
            if (!objectField.equals(that.objectField))
                return false;
        }

        if(!Arrays.equals(objectArray, that.objectArray))
            return false;

        return true;
    }

}
