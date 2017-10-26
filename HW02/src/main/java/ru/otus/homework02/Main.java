package ru.otus.homework02;

import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        SizeOf objectSizeOf = new SizeOf(Object.class);
        objectSizeOf.sizeOf();

        /*SizeOf shortSizeOf = new SizeOf(Short.class);
        shortSizeOf.sizeOf();

        SizeOf integerSizeOf = new SizeOf(Integer.class);
        integerSizeOf.sizeOf();

        SizeOf longSizeOf = new SizeOf(Long.class);
        longSizeOf.sizeOf();

        SizeOf floatSizeOf = new SizeOf(Float.class);
        floatSizeOf.sizeOf();

        SizeOf doubleSizeOf = new SizeOf(Double.class);
        doubleSizeOf.sizeOf();*/

        SizeOf pureStringSizeOf = new SizeOf(String.class);
        pureStringSizeOf.sizeOf();

        SizeOf stringSizeOf;
        for(int i = 0; i < 10; i++){
            stringSizeOf = new SizeOf(String.class, i);
            stringSizeOf.sizeOf();
        }

        SizeOf arrayListSizeOf;
        for(int i = 0; i < 10; i++){
            arrayListSizeOf = new SizeOf(ArrayList.class, i);
            arrayListSizeOf.sizeOf();
        }

        SizeOf hashSetSizeOf;
        for(int i = 0; i < 10; i++){
            hashSetSizeOf = new SizeOf(HashSet.class, i);
            hashSetSizeOf.sizeOf();
        }

    }

}
