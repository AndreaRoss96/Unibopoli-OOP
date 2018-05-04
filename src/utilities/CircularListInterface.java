package utilities;

import java.io.Serializable;

public interface CircularListInterface<E> extends Serializable, Iterable<E> {

    void shift();

    void setHead(int pos);

    E getHead();

    int size();
    
    boolean isEmpty();

    boolean contains(Object elem);

    boolean remove(Object elem);

}
