package ru.otus.homework05;


import ru.otus.homework05.myTestFramework.FrameWork;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("Run test with class name:\n");
        FrameWork.start(TestClass.class);
        System.out.println("------------------------------\nRun test from package:\n");
        FrameWork.start("ru.otus.homework05");
    }
}
