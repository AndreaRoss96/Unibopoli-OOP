
package utilities;

import java.util.ArrayList;
import java.util.List;

public class CircularListImpl<E> extends ArrayList<E> implements CircularList<E> {
    
	private static final long serialVersionUID = 8742821974151260090L;
	
	private int head;

    public CircularListImpl(final List<E> list) {
        super(list);
        this.head = 0;
    }

    @Override
    public void shift() {
    	this.head = ++this.head % super.size();
    }

    @Override
    public E getHead() {
        return super.get(head);
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
