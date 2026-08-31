package no.uio.ifi.task;

import java.util.Map;

public abstract class Mapper<T,R> {
    protected final Map<R, LinkedStack<T>> layer;

    protected Mapper(Map<R, LinkedStack<T>> layer) {
        this.layer = layer;
    }

    abstract void transform(T input);
}
