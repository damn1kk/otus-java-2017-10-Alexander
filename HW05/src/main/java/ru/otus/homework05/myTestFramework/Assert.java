package ru.otus.homework05.myTestFramework;

public class Assert {

    private static void printPassedResult(){
        String testClass = Thread.currentThread().getStackTrace()[3].getMethodName();
        System.out.println(testClass + " PASSED");
    }

    private static void printFailedResult(){
        String testClass = Thread.currentThread().getStackTrace()[3].getMethodName();
        System.out.println(testClass + " FAILED");
    }


    public static void assertEquals(long expected, long actual){
        if(expected == actual)
            printPassedResult();
        else
            printFailedResult();
    }

    public static void assertNotEquals(long expected, long actual){
        if(expected != actual)
            printPassedResult();
        else
            printFailedResult();
    }

    public static void assertEquals(Object expected, Object actual){
        if(expected.equals(actual))
            printPassedResult();
        else
            printFailedResult();
    }

    public static void assertNotEquals(Object expected, Object actual){
        if(!expected.equals(actual))
            printPassedResult();
        else
            printFailedResult();
    }

    public static void assertFalse(boolean condition){
        if(!condition)
            printPassedResult();
        else
            printFailedResult();
    }

    public static void assertTrue(boolean condition){
        if(condition)
            printPassedResult();
        else
            printFailedResult();
    }

    public static void assertNull(Object object){
        if(object == null)
            printPassedResult();
        else
            printFailedResult();
    }
}
