package ru.otus.homework02.sizeof;

import java.util.*;

import static ru.otus.homework02.Main.COUNT;
import static ru.otus.homework02.Main.usedMemory;

public class SizeOfCollection<T> implements SizeOf {

    private final int length;
    private final Class<T> type;

    public SizeOfCollection(Class<T> type, int length){
        this.type = type;
        this.length = length;
    }

    @Override
    public int sizeOf() {
        usedMemory();
        System.gc();

        Object[] array = new Object[COUNT];

        System.gc();
        long heapBefore = usedMemory();
        for(int i = 0; i < COUNT; i++){
            if(type.isInstance(new ArrayList())){
                array[i] = createArrayList(length);
            }

            if(type.isInstance(new HashSet())){
                array[i] = createHashSet(length);
            }

        }

        System.gc();
        long heapAfter = usedMemory();
        final int size = Math.round(((float)(heapAfter - heapBefore))/COUNT);
        System.out.println("length: " + length + " " + array[0].getClass() + " size = " + size + " bytes");
        return size;
    }

    private ArrayList createArrayList(final int length){
        ArrayList myList = new ArrayList();
        for(int i = 0; i < length; i++){
            myList.add(new Integer(i));
        }
        myList.trimToSize();
        return myList;
    }

    private HashSet createHashSet(final int length){
        HashSet mySet = new HashSet();
        for(int i = 0; i < length; i++){
            mySet.add(new Integer(i));
        }
        return mySet;
    }
}
