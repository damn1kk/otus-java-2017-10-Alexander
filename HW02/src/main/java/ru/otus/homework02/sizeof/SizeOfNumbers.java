package ru.otus.homework02.sizeof;

import static ru.otus.homework02.Main.COUNT;
import static ru.otus.homework02.Main.usedMemory;

public class SizeOfNumbers <T extends Number> implements SizeOf{

    private final Class<T> type;

    public SizeOfNumbers(Class<T> type){
        this.type = type;
    }

    @Override
    public int sizeOf(){
        usedMemory();
        System.gc();

        Object[] array = new Object[COUNT];

        System.gc();
        long heapBefore = usedMemory();
        for(int i = 0; i < COUNT; i++){
            if(type.isInstance(new Short((short)i))){
                array[i] = new Short((short)i);
            }

            if(type.isInstance(new Integer(i))){
                array[i] = new Integer(i);
            }

            if(type.isInstance(new Long(i))){
                array[i] = new Long(i);
            }

            if(type.isInstance(new Float(i))){
                array[i] =  new Float(i);
            }

            if(type.isInstance(new Double(i))){
                array[i] = new Double(i);
            }
        }
        System.gc();
        long heapAfter = usedMemory();
        final int size = Math.round(((float)(heapAfter - heapBefore))/COUNT);
        System.out.println(array[0].getClass() + " size = " + size + " bytes");
        return size;
    }
}
