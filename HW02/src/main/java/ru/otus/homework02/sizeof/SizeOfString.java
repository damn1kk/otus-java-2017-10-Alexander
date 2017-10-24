package ru.otus.homework02.sizeof;

import static ru.otus.homework02.Main.COUNT;
import static ru.otus.homework02.Main.usedMemory;

public class SizeOfString implements SizeOf {

    private final int length;

    public SizeOfString(int length){
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
            array[i] = createString(length);
        }

        System.gc();
        long heapAfter = usedMemory();
        final int size = Math.round(((float)(heapAfter - heapBefore))/COUNT);
        System.out.println("length: " + length + " " + array[0].getClass() + " size = " + size + " bytes");
        return size;
    }

    private String createString (final int length) {
        char [] result = new char [length];
        for (int i = 0; i < length; ++ i)
            result[i] = (char) i;
        return new String (result);
    }
}
