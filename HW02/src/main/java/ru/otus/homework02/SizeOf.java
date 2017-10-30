package ru.otus.homework02;

public class SizeOf {
    private final Class type;
    private final Runtime runtime = Runtime.getRuntime();
    private static final int COUNT = 10_000;
    private int lengthOfObject = 0;
    private final boolean hasLength;
    private ObjectGenerator objectGenerator;

    public SizeOf(Class type) {
        this.type = type;
        hasLength = false;
        this.objectGenerator = new ObjectGenerator(type);
    }

    public SizeOf(Class type, int length){
        this.type = type;
        hasLength = true;
        this.lengthOfObject = length;
        this.objectGenerator = new ObjectGenerator(type, length);
    }

    public long sizeOf(){
        usedMemory();
        gc();

        Object[] array = new Object[COUNT];

        gc();
        long heapBefore = usedMemory();

        for(int i = -10; i < COUNT; i++){
            Object object = null;

            if(hasLength){
                object = objectGenerator.createObject();
            }else {
                object = objectGenerator.createObject(i);
            }

            if(i < 0){
                object = null;
                System.gc();
                heapBefore = usedMemory();
            } else {
                array[i] = object;
            }
        }
        gc();
        long heapAfter = usedMemory();

        long size = (heapAfter - heapBefore)/COUNT;
        Object obj = array[0];
        return size;
    }

    private long usedMemory(){
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private void gc(){
        long heap1, heap2;
        heap1 = usedMemory();
        heap2 = Long.MAX_VALUE;
        int i = 0, count = 10;
        while(heap1 < heap2 || i < count){
            runtime.runFinalization();
            runtime.gc();
            i++;

            heap2 = heap1;
            heap1 = usedMemory();
        }
    }

    @Override
    public String toString(){
        if(hasLength){
            return "size of " + this.type.getName() + " with length " + lengthOfObject + " is " + sizeOf() + " bytes";
        }else{
            return "size of " + this.type.getName() + " is " + sizeOf() + " bytes";
        }
    }
}
