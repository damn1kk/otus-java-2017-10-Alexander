package ru.otus.homework05;

import ru.otus.homework05.myTestFramework.annotations.After;
import ru.otus.homework05.myTestFramework.annotations.Test;

public class TestClass2 {
    @Test
    public void Test(){
        System.out.println("TestClass2 Test1 method");
    }

    @After
    public void afterMethod(){
        System.out.println("TestClass2 After method");
    }
}
