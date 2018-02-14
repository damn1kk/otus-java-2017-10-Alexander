package ru.otus.homework14;

import java.util.Arrays;

public class Sorter extends Thread {
    private int[] array;
    public Sorter(int[] inputArray){
        this.array = inputArray;
    }

    public int[] getArray() {
        return array;
    }

    public static void printArray(int[] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    @Override
    public void run() {
        System.out.println(this.getName() + " thread started");
        Arrays.sort(array);
    }

    public static int[] multiThreadSortArray(int[] inputArray, int numberOfThreads){
        int[] outputArray = new int[inputArray.length];

        int lengthOfChunk = inputArray.length / numberOfThreads;
        int lengthOfLastChunk = lengthOfChunk + (inputArray.length % numberOfThreads);

        int[][] allArrays = new int[numberOfThreads][];
        for (int i = 0; i < numberOfThreads; i++) {
            if (i != numberOfThreads - 1) {
                allArrays[i] = new int[lengthOfChunk];
                System.arraycopy(inputArray, i * lengthOfChunk, allArrays[i], 0, lengthOfChunk);
            } else {
                allArrays[i] = new int[lengthOfLastChunk];
                System.arraycopy(inputArray, i * lengthOfChunk, allArrays[i], 0, lengthOfLastChunk);
            }
        }

        Sorter[] sorters = new Sorter[numberOfThreads];
        for(int i = 0; i < numberOfThreads; i++){
            sorters[i] = new Sorter(allArrays[i]);
        }

        try {
            for (Sorter s : sorters) {
                s.start();
                s.join();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        int[] indexes = new int[numberOfThreads];
        for(int i = 0; i < indexes.length; i++){
            indexes[i] = 0;
        }

        for(int i = 0; i < outputArray.length; i++){
            int[] minValues = makeArrayOfMinValues(allArrays, indexes);
            int indexOfMinValue = findIndexOfMin(minValues);
            indexes[indexOfMinValue]++;
            outputArray[i] = minValues[indexOfMinValue];
        }

        return outputArray;
    }

    private static int[] makeArrayOfMinValues(int[][] allArrays, int[] indexes){
        int[] result = new int[allArrays.length];
        for(int i = 0; i < allArrays.length; i++){
            if(indexes[i] >= allArrays[i].length){
                result[i] = Integer.MAX_VALUE;
            }else {
                result[i] = allArrays[i][indexes[i]];
            }
        }
        return result;
    }

    private static int findIndexOfMin(int[] values){
        int min = values[0];
        int index = 0;
        for(int i = 0; i < values.length; i++){
            if(values[i] < min){
                min = values[i];
                index = i;
            }
        }
        return index;
    }
}
