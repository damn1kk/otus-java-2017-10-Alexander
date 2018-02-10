package ru.otus.homework14;

import java.util.Arrays;

public class Sorter extends Thread {
    public static int[] multiThreadSortArray(int[] inputArray){
        int[] array = new int[inputArray.length];
        int[] outputArray = new int[inputArray.length];
        System.arraycopy(inputArray, 0, array, 0, inputArray.length);
        int lengthOfChunk = array.length/4;

        int[] firstArray = new int[lengthOfChunk];
        int[] secondArray = new int[lengthOfChunk];
        int[] thirdArray = new int[lengthOfChunk];
        int[] fourthArray = new int[lengthOfChunk];

        System.arraycopy(array, 0, firstArray, 0, lengthOfChunk);
        System.arraycopy(array, lengthOfChunk, secondArray, 0, lengthOfChunk);
        System.arraycopy(array, lengthOfChunk * 2, thirdArray, 0, lengthOfChunk);
        System.arraycopy(array, lengthOfChunk * 3, fourthArray, 0, lengthOfChunk);

        Sorter sorter1 = new Sorter(firstArray);
        Sorter sorter2 = new Sorter(secondArray);
        Sorter sorter3 = new Sorter(thirdArray);
        Sorter sorter4 = new Sorter(fourthArray);

        try {
            sorter1.start();
            sorter1.join();
            sorter2.start();
            sorter2.join();
            sorter3.start();
            sorter3.join();
            sorter4.start();
            sorter4.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        int firstIndex = 0, secondIndex = 0, thirdIndex = 0, fourthIndex = 0;
        for(int i = 0; i < outputArray.length; i++){
            int firstValue = getValueFromArrayOrIntegerMaxValue(firstIndex, firstArray);
            int secondValue = getValueFromArrayOrIntegerMaxValue(secondIndex, secondArray);
            int thirdValue = getValueFromArrayOrIntegerMaxValue(thirdIndex, thirdArray);
            int fourthValue = getValueFromArrayOrIntegerMaxValue(fourthIndex, fourthArray);

            int min = findMin(firstValue, secondValue, thirdValue, fourthValue);
            outputArray[i] = min;
            if(min == firstValue){
                firstIndex++;
            }else if(min == secondValue){
                secondIndex++;
            }else if(min == thirdValue){
                thirdIndex++;
            }else{
                fourthIndex++;
            }
        }

        return outputArray;
    }

    private static int getValueFromArrayOrIntegerMaxValue(int index, int[] array){
        if(index < array.length){
            return array[index];
        }else{
            return Integer.MAX_VALUE;
        }
    }

    private static int findMin(int firstValue, int secondValue, int thirdValue, int fourthValue){
        if(firstValue <= secondValue && firstValue <= thirdValue && firstValue <= fourthValue){
            return firstValue;
        }else if(secondValue <= firstValue && secondValue <= thirdValue && secondValue <= fourthValue){
            return secondValue;
        }else if(thirdValue <= firstValue && thirdValue <= secondValue && thirdValue <= fourthValue){
            return thirdValue;
        }else{
            return fourthValue;
        }
    }

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

}
