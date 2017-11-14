package ru.otus.homework03;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.UnaryOperator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MyArrayListTest {

    private MyArrayList<Integer> firstMyList = new MyArrayList<>();
    private MyArrayList<Integer> secondMyList = new MyArrayList<>();

    private void initMyList(){
        for(int i = 0; i < 40; i++){
            firstMyList.add(i);
        }
    }

    @Test
    public void sizeOfListShouldAutoIncrementWhenAddElement(){
        //init list with 40 elements (0 to 39)
        initMyList();


        //then
        assertEquals(40, firstMyList.size());
    }

    @Test
    public void addShouldReturnTrueWhenAddElement(){
        assertTrue(firstMyList.add(1));
    }

    @Test
    public void getShouldReturnValueAtTheSpecifiedPosition(){
        for(int i = 10; i < 40; i++){
            firstMyList.add(i);
        }

        //then
        assertEquals(10, (int)firstMyList.get(0));
        assertEquals(30, (int)firstMyList.get(20));

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getShouldThrowExceptionThenSpecifiedPositionIsWrong(){
        //init list with 40 elements (0 to 39)
        initMyList();

        firstMyList.get(50);
    }

    @Test
    public void addIntoMiddleListTest(){
        //init list with 40 elements (0 to 39)
        initMyList();

        //when
        firstMyList.add(10, 753);

        //then
        assertEquals(753, (int)firstMyList.get(10));
        assertEquals(41, firstMyList.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addShouldThrowExceptionThenSpecifiedPositionIsWrong(){
        firstMyList.add(50, 20);
    }

    @Test
    public void givenEmptyListThenIsEmptyShouldReturnTrue() {
        assertTrue(firstMyList.isEmpty());
    }

    @Test
    public void givenNotEmptyListThenIsEmptyShouldReturnFalse(){
        firstMyList.add(1);

        assertFalse(firstMyList.isEmpty());
    }

    @Test
    public void givenListWithNullAsElementThenContainsShouldReturnTrue(){
        firstMyList.add(null);

        assertTrue(firstMyList.contains(null));
    }

    @Test
    public void givenListWhithoutNullElementThenContainsShouldReturnFalse(){
        firstMyList.add(1);

        assertFalse(firstMyList.contains(null));
    }

    @Test
    public void whenListHaveSpecifiedElementThenContainsShouldReturnTrue(){
        //init list with 40 elements (0 to 39)
        initMyList();

        int specifiedElement = 30;
        assertTrue(firstMyList.contains(specifiedElement));


        specifiedElement = 50;
        assertFalse(firstMyList.contains(specifiedElement));
    }

    @Test
    public void whenFirstListContainsAllElementsFromSecondListThenContainsAllShouldReturnTrue(){
        //init list with 40 elements (0 to 39)
        initMyList();

        for(int i = 0; i < 10; i++){
            secondMyList.add(i);
        }

        assertTrue(firstMyList.containsAll(secondMyList));


        secondMyList.add(50);

        assertFalse(firstMyList.containsAll(secondMyList));
    }

    @Test(expected = NullPointerException.class)
    public void containsAllThrowExceptionThenSpecifiedCollectionIsNull(){
        firstMyList.containsAll(null);
    }

    @Test
    public void givenNotEmptyListThenToArrayShouldReturnArray(){
        for(int i = 0; i < 5; i++){
            firstMyList.add(i);
        }

        Integer[] testArrays = {0, 1, 2, 3, 4};

        assertEquals(testArrays, firstMyList.toArray());
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void removeShouldThrowExceptionThenSpecifiedIndexOutOfRange(){
        firstMyList.remove(50);
    }

    @Test
    public void whenRemoveElementAtTheSpecifiedPositionThenRemoveShouldReturnThisElement(){
        //init list with 40 elements (0 to 39)
        initMyList();
        int sizeBeforeRemove = firstMyList.size();

        assertEquals(0, (int)firstMyList.remove(0));

        assertEquals(--sizeBeforeRemove, firstMyList.size());
    }

    @Test
    public void whenRemoveElementAtTheSpecifiedPositionThenSizeShouldDecrementAndThisElementShouldBeRemoved(){
        for(int i = 0; i < 10; i++){
            firstMyList.add(i);
            if(i != 5){
                secondMyList.add(i);
            }
        }
        int sizeBeforeRemove = firstMyList.size();

        firstMyList.remove(5);
        assertEquals(--sizeBeforeRemove, firstMyList.size());
        assertTrue(secondMyList.containsAll(firstMyList));
    }

    @Test
    public void whenRemoveSpecifiedElementAndListContainItThenRemoveShouldReturnTrue(){
        //init list with 40 elements (0 to 39)
        initMyList();

        int sizeBeforeRemove = firstMyList.size();

        assertTrue(firstMyList.remove(new Integer(4)));
        assertEquals(--sizeBeforeRemove, firstMyList.size());
        assertFalse(firstMyList.contains(4));
    }

    @Test
    public void whenRemoveSpecifiedElementAndListDoesntContainItThenRemoveShouldReturnFalse(){
        //init list with 40 elements (0 to 39)
        initMyList();

        int sizeBeforeRemove = firstMyList.size();

        assertFalse(firstMyList.remove(new Integer(-4)));
        assertEquals(sizeBeforeRemove, firstMyList.size());

    }

    @Test(expected = NullPointerException.class)
    public void whenSpecifiedCollectionIsNullThenAddAllThrowNullPointerException(){
        //init list with 40 elements (0 to 39)
        initMyList();

        firstMyList.addAll(null);
    }


    @Test
    public void whenAddAllElementsIsSuccessThenAddAllShouldReturnTrue(){
        //init list with 40 elements (0 to 39)
        initMyList();

        for(int i = 50; i < 70; i++){
            secondMyList.add(i);
        }

        int firstListSize = firstMyList.size();
        int secondListSize = secondMyList.size();

        assertTrue(firstMyList.addAll(secondMyList));
        assertEquals(firstListSize + secondListSize, firstMyList.size());
        assertTrue(firstMyList.containsAll(secondMyList));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenIndexGreaterThenSizeOfListThenSetShouldThrowIndexOutOfBoundsException(){
        //init list with 40 elements (0 to 39)
        initMyList();

        firstMyList.set(55, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenIndexLessThenSizeOfListThenSetShouldThrowIndexOutOfBoundsException(){
        //init list with 40 elements (0 to 39)
        initMyList();

        firstMyList.set(-3, 5);
    }

    @Test
    public void whenSetElementAtTheSpecifiedPositionThenSetShouldReturnElementPreviouslyAtTheSpecifiedPosition(){
        //init list with 40 elements (0 to 39)
        initMyList();

        assertEquals(4, (int) firstMyList.set(4, 150));
        assertEquals(150, (int) firstMyList.get(4));
    }


    @Test
    public void givenNotEmptyListWhenClearListThenSizeShouldBeZero(){
        //init list with 40 elements (0 to 39)
        initMyList();
        firstMyList.clear();

        assertEquals(0, firstMyList.size());
    }

    @Test
    public void givenNotEmptyListWhenThisListContainSomeElementThenIndexOfShouldReturnIndexOfTheFirstOccurrenceThisElementInThisList(){
        //init list with 40 elements (0 to 39)
        initMyList();
        firstMyList.add(5);

        assertEquals(5, firstMyList.indexOf(5));
    }

    @Test
    public void givenNotEmptyListWhenThisListDontContainSomeElementThenIndexOfShouldReturnMinusOne(){
        //init list with 40 elements (0 to 39)
        initMyList();

        assertEquals(-1, firstMyList.indexOf(150));
    }

    @Test
    public void givenNotEmptyListWhenThisListContainSomeElementThenLastIndexOfShouldReturnIndexOfTheLastOccurrenceThisElementInThisList(){
        //init list with 40 elements (0 to 39)
        initMyList();
        firstMyList.add(5);

        assertEquals(40, firstMyList.lastIndexOf(5));
    }

    @Test
    public void givenNotEmptyListWhenThisListDontContainSomeElementThenLastIndexOfShouldReturnMinusOne(){
        //init list with 40 elements (0 to 39)
        initMyList();

        assertEquals(-1, firstMyList.lastIndexOf(150));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUseMethodSubListThenShouldThrowUnsupportedOperationException(){
        //init list with 40 elements (0 to 39)
        initMyList();

        firstMyList.subList(3, 4);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUsAddAllThenShouldThrowUnsupportedOperationException(){
        //init list with 40 elements (0 to 39)
        initMyList();

        firstMyList.addAll(3, secondMyList);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUsRemoveAllThenShouldThrowUnsupportedOperationException(){
        //init list with 40 elements (0 to 39)
        initMyList();

        firstMyList.removeAll(secondMyList);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUsRetainAllThenShouldThrowUnsupportedOperationException(){
        //init list with 40 elements (0 to 39)
        initMyList();

        firstMyList.retainAll(secondMyList);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUseReplaceAllThenShouldThrowUnsupportedOperationException(){
        //init list with 40 elements (0 to 39)
        initMyList();

        firstMyList.replaceAll(integer -> null);
    }

}
