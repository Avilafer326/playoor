package lists;

import nodo.Nodo;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DLList<T> implements Lists<T>, Iterable<T> {

    private final Nodo<T> head;
    private final Nodo<T> tail;
    private long indice;

    public DLList(Nodo<T> nodo) {
        this.head = new Nodo<>();
        this.tail = new Nodo<>();
        this.head.setRight(this.tail);
        this.tail.setLeft(this.head);
        this.indice = 0;
        if (nodo != null) {
            add(nodo);
        }
    }

    public DLList() {
        this(null);
    }

    @Override
    public boolean isEmpty() throws NoSuchElementException {
        if (head.getRight() == tail) {
            throw new NoSuchElementException("Lista vacia");
        }
        return false;
    }

    @Override
    public Nodo<T> getLastElement() {
        isEmpty();
        return tail.getLeft();
    }

    @Override
    public Nodo<T> getPrevElement(T elemento) {
        isEmpty();
        if (elemento == null) {
            return null;
        }
        Nodo<T> actual = head;
        while (actual.getRight() != tail) {
            if (actual.getRight().getElemento().equals(elemento)) {
                return actual;
            }
            actual = actual.getRight();
        }
        return null;
    }

    @Override
    public Nodo<T> getElement(T elemento) {
        isEmpty();
        if (elemento == null) {
            return null;
        }
        Nodo<T> actual = head.getRight();
        while (actual != tail) {
            if (actual.getElemento().equals(elemento)) {
                return actual;
            }
            actual = actual.getRight();
        }
        return null;
    }

    @Override
    public Nodo<T> getElementAt(long indice) {
        isEmpty();
        if (indice < 0 || indice >= this.indice) {
            throw new IndexOutOfBoundsException("Indice invalido");
        }
        Nodo<T> actual = head.getRight();
        long contador = 0;
        while (actual != tail) {
            if (contador == indice) {
                return actual;
            }
            actual = actual.getRight();
            contador++;
        }
        return null;
    }

    @Override
    public boolean isThere(T elemento) {
        return getElement(elemento) != null;
    }

    @Override
    public boolean isThere(Nodo<T> nodo) {
        if (nodo == null) {
            return false;
        }
        isEmpty();
        Nodo<T> actual = head.getRight();
        while (actual != tail) {
            if (actual == nodo) {
                return true;
            }
            actual = actual.getRight();
        }
        return isThere(nodo.getElemento());
    }

    @Override
    public void add(T elemento) {
        if (elemento == null) {
            return;
        }
        Nodo<T> nuevo = new Nodo<>(elemento);
        Nodo<T> ultimo = tail.getLeft();
        nuevo.setLeft(ultimo);
        nuevo.setRight(tail);
        ultimo.setRight(nuevo);
        tail.setLeft(nuevo);
        indice++;
    }

    @Override
    public void add(Nodo<T> nodo) {
        if (nodo == null) {
            return;
        }
        add(nodo.getElemento());
    }

    @Override
    public void add(T elemento, int i) {
        if (elemento == null) {
            return;
        }
        if (i < 0 || i > indice) {
            throw new IndexOutOfBoundsException("Indice invalido");
        }
        if (i == 0) {
            addStart(elemento);
            return;
        }
        if (i == indice) {
            add(elemento);
            return;
        }

        Nodo<T> actual = getElementAt(i);
        Nodo<T> anterior = actual.getLeft();
        Nodo<T> nuevo = new Nodo<>(elemento);

        nuevo.setLeft(anterior);
        nuevo.setRight(actual);
        anterior.setRight(nuevo);
        actual.setLeft(nuevo);
        indice++;
    }

    @Override
    public void add(Nodo<T> nodo, int i) {
        if (nodo == null) {
            return;
        }
        add(nodo.getElemento(), i);
    }

    @Override
    public void remove(T elemento) {
        isEmpty();
        if (elemento == null) {
            return;
        }
        Nodo<T> borrar = getElement(elemento);
        if (borrar == null) {
            return;
        }
        remove(borrar);
    }

    @Override
    public void remove(Nodo<T> nodo) {
        isEmpty();
        if (nodo == null) {
            return;
        }
        Nodo<T> actual = head.getRight();
        while (actual != tail) {
            if (actual == nodo) {
                Nodo<T> anterior = actual.getLeft();
                Nodo<T> siguiente = actual.getRight();
                anterior.setRight(siguiente);
                siguiente.setLeft(anterior);
                actual.setLeft(null);
                actual.setRight(null);
                indice--;
                return;
            }
            actual = actual.getRight();
        }
        remove(nodo.getElemento());
    }

    @Override
    public void remove(Nodo<T> nodo, int i) {
        isEmpty();
        if (nodo == null) {
            return;
        }
        Nodo<T> actual = getElementAt(i);
        if (actual == nodo || actual.getElemento().equals(nodo.getElemento())) {
            remove(actual);
        }
    }

    @Override
    public void addAfter(T referencia, T elemento) {
        isEmpty();
        if (referencia == null || elemento == null) {
            return;
        }
        Nodo<T> actual = getElement(referencia);
        if (actual == null) {
            return;
        }
        Nodo<T> nuevo = new Nodo<>(elemento);
        Nodo<T> siguiente = actual.getRight();
        nuevo.setLeft(actual);
        nuevo.setRight(siguiente);
        actual.setRight(nuevo);
        siguiente.setLeft(nuevo);
        indice++;
    }

    @Override
    public void removeAfter(T referencia) {
        isEmpty();
        if (referencia == null) {
            return;
        }
        Nodo<T> actual = getElement(referencia);
        if (actual == null || actual.getRight() == tail) {
            return;
        }
        remove(actual.getRight());
    }

    @Override
    public void addStart(T elemento) {
        if (elemento == null) {
            return;
        }
        Nodo<T> primero = head.getRight();
        Nodo<T> nuevo = new Nodo<>(elemento);
        nuevo.setLeft(head);
        nuevo.setRight(primero);
        head.setRight(nuevo);
        primero.setLeft(nuevo);
        indice++;
    }

    @Override
    public long length() {
        return indice;
    }

    @Override
    public void removeAll(T elemento) {
        isEmpty();
        if (elemento == null) {
            return;
        }
        Nodo<T> actual = head.getRight();
        while (actual != tail) {
            Nodo<T> siguiente = actual.getRight();
            if (actual.getElemento().equals(elemento)) {
                remove(actual);
            }
            actual = siguiente;
        }
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("[");
        Nodo<T> actual = head.getRight();
        while (actual != tail) {
            texto.append(actual.getElemento());
            if (actual.getRight() != tail) {
                texto.append(", ");
            }
            actual = actual.getRight();
        }
        texto.append("]");
        return texto.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = head.getRight();

            @Override
            public boolean hasNext() {
                return actual != tail;
            }

            @Override
            public T next() {
                T elemento = actual.getElemento();
                actual = actual.getRight();
                return elemento;
            }
        };
    }

    public Iterator<T> lefty() {
        return new Iterator<T>() {
            private Nodo<T> actual = tail.getLeft();

            @Override
            public boolean hasNext() {
                return actual != head;
            }

            @Override
            public T next() {
                T elemento = actual.getElemento();
                actual = actual.getLeft();
                return elemento;
            }
        };
    }
}
