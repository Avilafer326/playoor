package queue;

import lists.DLList;
import nodo.Nodo;

import java.util.NoSuchElementException;

public class DQueue <T> implements Queue<T> {

    private final DLList<T> queue;

    public DQueue() {
        this.queue = new DLList<>();
    }

    @Override
    public void enqueue(T element) {
        queue.add(element);
    }

    @Override
    public T dequeue() throws NoSuchElementException {
        Nodo<T> nodo = queue.getElementAt(0);
        T element = nodo.getElemento();
        queue.remove(nodo);
        return element;
    }

    @Override
    public T front() throws NoSuchElementException {
        return queue.getElementAt(0).getElemento();
    }

    @Override
    public boolean isEmpty() throws NoSuchElementException {
        if (queue.length() == 0) {
            throw new NoSuchElementException("Queue vacio");
        }
        return false;
    }

    @Override
    public boolean isFull() throws NoSuchElementException {
        return false;
    }

    @Override
    public int size() {
        return (int) queue.length();
    }
}
