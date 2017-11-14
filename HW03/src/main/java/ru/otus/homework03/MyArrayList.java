package ru.otus.homework03;

import java.util.*;
import java.util.function.UnaryOperator;

public class MyArrayList<E> implements List<E> {
    private int size;
    private E[] array;

    public MyArrayList (){
        this.size = 0;
        this.array = (E[]) new Object[10];
    }

    public MyArrayList (int capacity){
        this.size = 0;
        this.array = (E[]) new Object[capacity];
    }

    @Override
    public String toString() {
        Iterator<E> iterator = this.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        if(!iterator.hasNext())
            return "[]";
        else{
            stringBuilder.append("[");
            while(true){
                stringBuilder.append(iterator.next());
                if(iterator.hasNext()){
                    stringBuilder.append(",").append(" ");
                }else{
                    break;
                }
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        if(size < array.length){
            array[size++] = e;
            return true;
        }else{
            growArraySize();
            add(e);
            return true;
        }
    }

    @Override
    public void add(int index, E element) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        if(array.length > size){
            E[] partAfterIndex = (E[]) new Object[size - index];
            System.arraycopy(array, index, partAfterIndex, 0, size-index);
            array[index] = element;
            System.arraycopy(partAfterIndex, 0, array, index + 1, partAfterIndex.length);
            size++;
        }else{
            growArraySize();
            add(index, element);
        }
    }

    private void growArraySize(){
        E[] oldArray = this.array;
        E[] newArray = (E[]) new Object[size + 10];
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        this.array = newArray;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if(o == null){
            for(int i = 0; i < size; i++){
                if(array[i] == null){
                    return true;
                }
            }
        }
        for(int i = 0; i < size; i++){
            if(array[i].equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if(c == null){
            throw new NullPointerException();
        }

        for(Object o : c){
            if(!contains(o)){
                return false;
            }
        }
        return true;
    }

    @Override
    public E[] toArray() {
        E[] oldArray = this.array;
        E[] newArray = (E[]) new Object[size];
        System.arraycopy(oldArray, 0, newArray, 0, size);

        return newArray;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public E remove(int index) {
        if(index < size && index >= 0) {
            E element = array[index];
            System.arraycopy(array, index + 1, array, index, size - index - 1);
            size--;
            array[size] = null;
            return element;
        }else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean remove(Object o) {
        if(o == null){
            for(int i = 0; i < size; i++){
                if(array[i] == null){
                    remove(i);
                    return true;
                }
            }
        }else{
            for(int i = 0; i < size; i++){
                if(array[i].equals(o)){
                    remove(i);
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean addAll(Collection<? extends E> c) {
        if(c == null){
            throw new NullPointerException();
        }

        E[] arrayOfSpecifiedCollection = (E[]) c.toArray();
        E[] oldArray = this.array;

        int newLength = size + arrayOfSpecifiedCollection.length;
        E[] newArray = (E[]) new Object[newLength];

        System.arraycopy(oldArray, 0, newArray, 0, size);
        System.arraycopy(arrayOfSpecifiedCollection, 0, newArray, size, arrayOfSpecifiedCollection.length);
        this.array = newArray;
        this.size = newLength;
        return arrayOfSpecifiedCollection.length != 0;
    }

    @Override
    public E set(int index, E element) {
        if(index >= 0 && index < size()) {
            E oldValue = array[index];
            array[index] = element;
            return oldValue;
        }else{
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void clear() {
        for(int i = 0; i < size(); i++){
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < size; i++){
            if(o.equals(array[i])){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = size - 1; i >= 0; i--){
            if(array[i].equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        if(index >= 0 && index < size) {
            return array[index];
        }else{
            throw new IndexOutOfBoundsException();
        }
    }


    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if(index > 0 && index < size) {
            return new MyListIterator(index);
        }else {
            throw new IndexOutOfBoundsException();
        }
    }

    private class MyIterator implements Iterator<E>{
        int position;
        int lastReturned = -1;

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public E next() {
            if(position >= size){
                throw new NoSuchElementException();
            }
            lastReturned = position++;
            return array[lastReturned];
        }

        @Override
        public void remove() {
            if(lastReturned != -1){
                MyArrayList.this.remove(lastReturned);
                position = lastReturned;
                lastReturned = -1;
            }else{
                throw new IllegalStateException();
            }
        }
    }

    private class MyListIterator extends MyIterator implements ListIterator<E>{
        MyListIterator(int index){
            super();
            position = index;
        }

        @Override
        public boolean hasPrevious() {
            return position > 0;
        }

        @Override
        public int nextIndex() {
            return position;
        }

        @Override
        public int previousIndex() {
            return position - 1;
        }

        @Override
        public E previous() {
            if(position - 1 < 0){
                throw new NoSuchElementException();
            }
            lastReturned = --position;
            return array[lastReturned];
        }

        @Override
        public void set(E e) {
            if(lastReturned >= 0){
                MyArrayList.this.set(lastReturned, e);
            }else{
                throw new NoSuchElementException();
            }
        }

        @Override
        public void add(E e) {
            MyArrayList.this.add(position, e);
            position++;
        }
    }

    //----------------------------------------this methods are not supported:

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw new UnsupportedOperationException();
    }

}
