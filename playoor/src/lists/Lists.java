package lists;

import nodo.Nodo;

import java.util.NoSuchElementException;

public interface Lists<T> {
    // verifica si la lista esta vacia
    boolean isEmpty() throws NoSuchElementException;

    // obtiene el ultimo nodo de la lista
    Nodo<T> getLastElement();

    // obtiene el nodo anterior al elemento buscado
    Nodo<T> getPrevElement(T elemento);

    // obtiene un nodo del indice deseado
    Nodo<T> getElement(T elemento);
    Nodo<T> getElementAt(long indice);

    boolean isThere(T elemento);
    boolean isThere(Nodo<T> nodo);

    void add(T elemento);
    void add(Nodo<T> nodo);
    void add(T elemento, int i);
    void add(Nodo<T> nodo, int i);

    void remove(T elemento);
    void remove(Nodo<T> nodo);
    void remove(Nodo<T> nodo, int i);

    void addAfter(T referencia, T elemento);
    void removeAfter(T referencia);
    void addStart(T elemento);
    long length();
    void removeAll(T elemento);
}








