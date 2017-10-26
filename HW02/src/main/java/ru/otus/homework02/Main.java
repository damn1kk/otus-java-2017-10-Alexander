package ru.otus.homework02;

import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        SizeOf objectSizeOf = new SizeOf(Object.class);
        System.out.println(objectSizeOf.toString());

        SizeOf shortSizeOf = new SizeOf(Short.class);
        System.out.println(shortSizeOf.toString());

        SizeOf integerSizeOf = new SizeOf(Integer.class);
        System.out.println(integerSizeOf.toString());

        SizeOf longSizeOf = new SizeOf(Long.class);
        System.out.println(longSizeOf.toString());

        System.out.println("-----------------------");

        SizeOf floatSizeOf = new SizeOf(Float.class);
        System.out.println(floatSizeOf.toString());

        SizeOf doubleSizeOf = new SizeOf(Double.class);
        System.out.println(doubleSizeOf.toString());

        SizeOf pureStringSizeOf = new SizeOf(String.class);
        System.out.println(pureStringSizeOf.toString());

        System.out.println("-----------------------");

        SizeOf stringSizeOf;
        for(int i = 0; i < 10; i++){
            stringSizeOf = new SizeOf(String.class, i);
            System.out.println(stringSizeOf.toString());
        }

        System.out.println("-----------------------");

        SizeOf arrayListSizeOf;
        for(int i = 0; i < 10; i++){
            arrayListSizeOf = new SizeOf(ArrayList.class, i);
            System.out.println(arrayListSizeOf.toString());
        }

        System.out.println("-----------------------");

        SizeOf hashSetSizeOf;
        for(int i = 0; i < 10; i++){
            hashSetSizeOf = new SizeOf(HashSet.class, i);
            System.out.println(hashSetSizeOf.toString());
        }

    }

}
