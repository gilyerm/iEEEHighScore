package Experiments.Local;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.LinkedList;

public class BufferQueue<T> {

    private final LinkedList<T> linkedList;
    private final int maxSize;

    public BufferQueue(@NotNull final int maxSize, @Nullable final T defualt) {
        this.maxSize=maxSize;
        this.linkedList=new LinkedList<>();
        for (int i = 0; i < maxSize; i++) {
            this.push(defualt);
        }
    }

    public void push(@Nullable final T t) {
        if (this.linkedList.size()>=maxSize) this.pop();
        this.linkedList.addFirst(t);
    }
    private T pop(){
        return this.linkedList.removeLast();
    }
    public T peek(){
        return this.linkedList.peek();
    }

    public T cell(@NotNull final int index){
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
