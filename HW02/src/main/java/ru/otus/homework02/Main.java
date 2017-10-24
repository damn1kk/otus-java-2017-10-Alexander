package ru.otus.homework02;

import ru.otus.homework02.sizeof.*;

import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    private static final Runtime runtime = Runtime.getRuntime();
    public static final int COUNT = 50_000;

    public  static long usedMemory() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void main(String[] args) throws Exception{

        SizeOf shortSizeOfNumbers = new SizeOfNumbers<>(Short.class);
        shortSizeOfNumbers.sizeOf();

        SizeOf integerSizeOfNumbers = new SizeOfNumbers<>(Integer.class);
        integerSizeOfNumbers.sizeOf();

        SizeOf longSizeOfNumbers = new SizeOfNumbers<>(Long.class);
        longSizeOfNumbers.sizeOf();

        System.out.println("-------------------------------------");

        SizeOf floatSizeOfNumbers = new SizeOfNumbers<>(Float.class);
        floatSizeOfNumbers.sizeOf();

        SizeOf doubleSizeOfNumbers = new SizeOfNumbers<>(Double.class);
        doubleSizeOfNumbers.sizeOf();

        System.out.println("--------------------------------------");

        SizeOf emptyStringSizeOf = new SizeOfEmptyString();
        emptyStringSizeOf.sizeOf();

        SizeOf stringSizeOf;
        for(int i = 0; i < 10; i++){
             stringSizeOf = new SizeOfString(i);
             stringSizeOf.sizeOf();
        }

        System.out.println("---------------------------------------");
        System.out.println("Array List and Hash Map will filled Integers\n");
        SizeOf arrayListSizeOf;
        for(int i = 0; i < 10; i++){
            arrayListSizeOf = new SizeOfCollection(ArrayList.class, i);
            arrayListSizeOf.sizeOf();
        }

        System.out.println("----------------------------------------");

        SizeOf hashSetSizeOf;
        for(int i = 0; i < 10; i++){
            hashSetSizeOf = new SizeOfCollection(HashSet.class, i);
            hashSetSizeOf.sizeOf();
        }
    }
}
