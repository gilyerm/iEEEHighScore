package Experiments.Local.v1;

import sun.misc.Queue;

import java.util.LinkedList;

public class BufferQueue<T> {
    private LinkedList<T> linkedList;
    private int maxSize;
    public BufferQueue(int maxSize,T defualt) {
        this.maxSize=maxSize;
        this.linkedList=new LinkedList<>();
        for (int i = 0; i < maxSize; i++) {
            this.push(defualt);
        }
    }

    public void push(T t) {
        if (this.linkedList.size()>=maxSize) this.pop();
        this.linkedList.addFirst(t);
    }
    private T pop(){
        return this.linkedList.removeLast();
    }
    public T peek(){
        return this.linkedList.peek();
    }

    public T cell(int index){
        if (index<0||index>=maxSize) throw new IndexOutOfBoundsException("bad Index");
        return linkedList.get(index);
    }

    @Override
    public String toString() {
        return linkedList.toString();
    }

    public int getMaxSize() {
        return maxSize;
    }
}
