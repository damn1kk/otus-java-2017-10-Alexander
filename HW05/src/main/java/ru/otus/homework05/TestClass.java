package ru.otus.homework05;

import ru.otus.homework05.myTestFramework.annotations.After;
import ru.otus.homework05.myTestFramework.annotations.Before;
import ru.otus.homework05.myTestFramework.annotations.Test;

import static ru.otus.homework05.myTestFramework.Assert.assertEquals;
import static ru.otus.homework05.myTestFramework.Assert.assertNotEquals;

public class TestClass {

    public void init(){
        System.out.println("TestClass1 init without annotation");
    }

    @After
    public void finish(){
        System.out.println("TestClass1 After method");
    }

    @Test
    public void firstTest(){
        assertEquals(5, 5);
        System.out.println("TestClass1 Test1 method");
    }

    @Before
    public void init2(){
        System.out.println("TestClass1 Before method");
    }

    @Test
    public void secondTest(){
        String actualString = "asd";
        assertNotEquals(new String("asd"), actualString);
        System.out.println("TestClass1 Test2 method");
    }

    @Test
    public void thirdTest(){
        String actualString = " xzxz";
        assertEquals(new String("asd"), actualString);
        System.out.println("TestClass1 Test3 method");
    }

}
