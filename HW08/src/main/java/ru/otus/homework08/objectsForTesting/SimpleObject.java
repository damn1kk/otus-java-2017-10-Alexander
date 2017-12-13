package ru.otus.homework08.objectsForTesting;

public class SimpleObject {
    private String stringField;
    private int intField;
    private double doubleField;


    public SimpleObject(String stringField, int intField, double doubleField){
        this.stringField = stringField;
        this.intField = intField;
        this.doubleField = doubleField;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(!(obj instanceof SimpleObject))
            return false;

        SimpleObject that = (SimpleObject) obj;

        if(stringField == null) {
            if(that.stringField != null)
                return false;
        }else{
            if (!stringField.equals(that.stringField))
                return false;
        }
        if(intField != that.intField)
            return false;
        if(doubleField != that.doubleField)
            return false;

        return true;
    }

}
