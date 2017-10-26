package ru.otus.homework02;

import java.util.ArrayList;
import java.util.HashSet;

public class ObjectGenerator {
    private final Class type;
    private int lengthOfObject = 0;

    public ObjectGenerator(Class type) {
        this.type = type;
    }

    public ObjectGenerator(Class type, int length) {
        this.type = type;
        this.lengthOfObject = length;
    }

    public Object createObject(int i){
        if(type.isInstance(new Object())){
            return new Object();
        }

        if(type.isInstance(new Short((short)i))){
            return new Short((short)i);
        }

        if(type.isInstance(new Integer(i))){
            return new Integer(i);
        }

        if(type.isInstance(new Long(i))){
            return new Long(i);
        }

        if(type.isInstance(new Float(i))){
            return new Float(i);
        }

        if(type.isInstance(new Double(i))){
            return new Double(i);
        }

        if(type.isInstance(new String(" "))){
            return new String(" ");
        }

        return null;
    }

    public Object createObject(){
        if(type.isInstance(new String())){
            return createString(this.lengthOfObject);
        }

        if(type.isInstance(new ArrayList())){
            return createArrayList(this.lengthOfObject);
        }

        if(type.isInstance(new HashSet())){
            return createHashSet(this.lengthOfObject);
        }

        return null;
    }

    private String createString(int length){
        char[] charArray = new char[length];
        for(int i = 0; i < length; i++){
            charArray[i] = (char)i;
        }
        return new String(charArray);
    }

    private ArrayList createArrayList(int length){
        ArrayList newArrayList = new ArrayList();
        for(int i = 0; i < length; i++){
            newArrayList.add(i);
        }
        return newArrayList;
    }

    private HashSet createHashSet(int length){
        HashSet newHashSet = new HashSet();
        for(int i = 0; i < length; i++){
            newHashSet.add(i);
        }
        return newHashSet;
    }
}
