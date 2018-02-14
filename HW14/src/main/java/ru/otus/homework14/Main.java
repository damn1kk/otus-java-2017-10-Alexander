package ru.otus.homework14;


public class Main {
    public static final int SIZE_OF_ARRAY = 18;
    public static final int NUMBER_OF_THREADS = 3;

    public static void main(String[] args) {
        int[] array = new int[SIZE_OF_ARRAY];
        for(int i = 0; i < SIZE_OF_ARRAY; i++){
            array[i] = (int)(Math.random() * 100);
        }

        System.out.println("Before sort: ");
        Sorter.printArray(array);
        int[] sortedArray = Sorter.multiThreadSortArray(array, NUMBER_OF_THREADS);
        System.out.println("After sort: ");
        Sorter.printArray(sortedArray);
    }
}
