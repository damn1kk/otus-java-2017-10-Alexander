package ru.otus.homework02;

import java.util.ArrayList;
import java.util.HashSet;

public class SizeOf {
    private final Class type;
    private final Runtime runtime = Runtime.getRuntime();
    private static final int COUNT = 1_000;
    private int lengthOfObject = 0;
    private final boolean hasLength;

    public SizeOf(Class type) {
        this.type = type;
        hasLength = false;
    }

    public SizeOf(Class type, int length){
        this.type = type;
        this.lengthOfObject = length;
        hasLength = true;
    }

    public long sizeOf(){
        usedMemory();
        System.gc();

        Object[] array = new Object[COUNT];

        System.gc();
        long heapBefore = usedMemory();

        for(int i = -3; i < COUNT; i++){
            Object object = null;

            if(hasLength){
                object = createObject(i, lengthOfObject);
            }else {
                object = createObject(i);
            }

            if(i < 0){
                object = null;
                System.gc();
                heapBefore = usedMemory();
            } else {
                array[i] = object;
            }
        }
        System.gc();
        long heapAfter = usedMemory();

        long size = (heapAfter - heapBefore)/COUNT;
        if(hasLength){
            System.out.println("size of " + type.getName() + " with length " + lengthOfObject + " is " + size + " bytes");
        }else {
            System.out.println("size of " + type.getName() + " is " + size + " bytes");
        }
        return size;
    }

    private Object createObject(int i){
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

    private Object createObject(int i, int length){
        if(type.isInstance(new String())){
            return createString(length);
        }

        if(type.isInstance(new ArrayList())){
            return createArrayList(length);
        }

        if(type.isInstance(new HashSet())){
            return createHashSet(length);
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

    private long usedMemory(){
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
