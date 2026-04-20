package stack;

import lists.DLList;
import nodo.Nodo;

import java.util.NoSuchElementException;

public class DStack<T> implements Stack<T> {
    private final DLList<T> stack;


    public DStack() {
        this.stack = new DLList<>();
    }


    @Override
    public boolean isEmpty() throws NoSuchElementException {
        return stack.length() == 0;
    }

    @Override
    public boolean isFull() throws IllegalStateException {
        return false;
    }

    @Override
    public void push(T item) {
        stack.add(item);
    }

    @Override
    public T pop() throws NoSuchElementException {
        Nodo<T> nodo = stack.getLastElement();
        T item = nodo.getElemento();
        stack.remove(nodo);
        return item;
    }

    @Override
    public T peek() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException("Stack vacio");
        return stack.getLastElement().getElemento();

    }
}
