package utilities;

import java.io.Serializable;

/**
 * Implementation of the interface CircularList<E>.
 */
interface CircularList<E> extends Serializable, Iterable<E> {

    void shift();

    E getHead();
}
