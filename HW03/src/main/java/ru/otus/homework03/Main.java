package ru.otus.homework03;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> myList = new MyArrayList<>();

        Integer[] arrayOfIntegers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Collections.addAll(myList, arrayOfIntegers);

        System.out.println("myList after adding elements from array: " + myList);

        MyArrayList<Integer> secondMyList = new MyArrayList<>();
        Integer[] secondArrayOfInteger = {15, 16, 17};
        Collections.addAll(secondMyList, secondArrayOfInteger);
        System.out.println("secondList: " + secondMyList);

        Collections.copy(myList, secondMyList);

        System.out.println("myList after copying elements from secondList: " + myList);

        Collections.sort(myList);

        System.out.println("myList after sorting elements: " + myList);

    }


}
