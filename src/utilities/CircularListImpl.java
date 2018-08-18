package utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the interface CircularList<E>.
 */

interface CircularList<E> extends Serializable, Iterable<E> {

    void shift();

    E getHead();

    int size();
    
    boolean remove(Object elem);
}

public class CircularListImpl<E> extends ArrayList<E> implements CircularList<E> {
    
	private static final long serialVersionUID = 8742821974151260090L;
	
	private int head;

    public CircularListImpl(final List<E> list) {
        super(list);
        this.head = -1;
    }

    @Override
    public void shift() {
    	this.head = ++this.head % super.size();
    }

    @Override
    public E getHead() {
        return super.get(head);
    }

    /**
     * TODO: Metodo non utilizzato. Controllare se necessario.
     * 
     * */
    @Override
    public E get(final int index) {
        if (index < super.size()) {
            return super.get(index);
        }
        
        return null;
    }

    @Override
    public boolean remove(final Object elem) {
        if (super.contains(elem)) {
            if (indexOf(elem) <= this.head && this.head != 0) {
                this.head--;
            }
            super.remove(elem);
        }
        return false;
    }

    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < super.size(); i++) {
            if (i == this.head) {
                temp += "->";
            } 
            temp += super.get(i).toString();
            temp += ", ";
        }
        return temp;
    }
}
