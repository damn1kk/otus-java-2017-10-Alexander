package ru.otus.homework02.sizeof;

import static ru.otus.homework02.Main.COUNT;
import static ru.otus.homework02.Main.usedMemory;

public class SizeOfEmptyString implements SizeOf {
    @Override
    public int sizeOf() {
        usedMemory();
        System.gc();
        Object[] array = new Object[COUNT];

        System.gc();
        long heapBefore = usedMemory();
        for(int i = 0; i < COUNT; i++){
            array[i] = new String(" ");
        }

        System.gc();
        long heapAfter = usedMemory();
        final int size = Math.round(((float)(heapAfter - heapBefore))/COUNT);
        System.out.println(array[0].getClass() + " size = " + size + " bytes");
        return size;
    }
}
