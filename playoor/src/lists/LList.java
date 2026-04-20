package lists;

import nodo.Nodo;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LList<T> implements Lists<T>, Iterable<T> {

    private final Nodo<T> head;
    private long indice;

    public LList(Nodo<T> nodo) {
        this.head = new Nodo<>();
        this.indice = 0;
        if (nodo != null) {
            add(nodo);
        }
    }

    public LList() {
        this(null);
    }

    @Override
    public boolean isEmpty() throws NoSuchElementException {
        if (head.getRight() == null) {
            throw new NoSuchElementException("Lista vacia");
        }
        return false;
    }

    @Override
    public Nodo<T> getLastElement() {
        isEmpty();
        return getLastElement(head.getRight());
    }

    private Nodo<T> getLastElement(Nodo<T> actual) {
        if (actual.getRight() == null) {
            return actual;
        }
        return getLastElement(actual.getRight());
    }

    @Override
    public Nodo<T> getPrevElement(T elemento) {
        isEmpty();
        if (elemento == null) {
            return null;
        }
        return getPrevElement(head, elemento);
    }

    private Nodo<T> getPrevElement(Nodo<T> actual, T elemento) {
        if (actual.getRight() == null) {
            return null;
        }
        if (actual.getRight().getElemento().equals(elemento)) {
            return actual;
        }
        return getPrevElement(actual.getRight(), elemento);
    }

    @Override
    public Nodo<T> getElement(T elemento) {
        isEmpty();
        if (elemento == null) {
            return null;
        }
        Nodo<T> actual = head.getRight();
        while (actual != null) {
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
        while (actual != null) {
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
        while (actual != null) {
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
        if (head.getRight() == null) {
            head.setRight(nuevo);
        } else {
            Nodo<T> ultimo = getLastElement();
            ultimo.setRight(nuevo);
        }
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

        Nodo<T> nuevo = new Nodo<>(elemento);
        Nodo<T> anterior = getElementAt(i - 1);
        nuevo.setRight(anterior.getRight());
        anterior.setRight(nuevo);
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
        Nodo<T> anterior = getPrevElement(elemento);
        if (anterior == null) {
            return;
        }
        Nodo<T> borrar = anterior.getRight();
        anterior.setRight(borrar.getRight());
        borrar.setRight(null);
        indice--;
    }

    @Override
    public void remove(Nodo<T> nodo) {
        isEmpty();
        if (nodo == null) {
            return;
        }
        Nodo<T> anterior = head;
        while (anterior.getRight() != null) {
            if (anterior.getRight() == nodo) {
                Nodo<T> borrar = anterior.getRight();
                anterior.setRight(borrar.getRight());
                borrar.setRight(null);
                indice--;
                return;
            }
            anterior = anterior.getRight();
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
        nuevo.setRight(actual.getRight());
        actual.setRight(nuevo);
        indice++;
    }

    @Override
    public void removeAfter(T referencia) {
        isEmpty();
        if (referencia == null) {
            return;
        }
        Nodo<T> actual = getElement(referencia);
        if (actual == null || actual.getRight() == null) {
            return;
        }
        Nodo<T> borrar = actual.getRight();
        actual.setRight(borrar.getRight());
        borrar.setRight(null);
        indice--;
    }

    @Override
    public void addStart(T elemento) {
        if (elemento == null) {
            return;
        }
        Nodo<T> nuevo = new Nodo<>(elemento);
        nuevo.setRight(head.getRight());
        head.setRight(nuevo);
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
        Nodo<T> anterior = head;
        while (anterior.getRight() != null) {
            if (anterior.getRight().getElemento().equals(elemento)) {
                Nodo<T> borrar = anterior.getRight();
                anterior.setRight(borrar.getRight());
                borrar.setRight(null);
                indice--;
            } else {
                anterior = anterior.getRight();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("[");
        Nodo<T> actual = head.getRight();
        while (actual != null) {
            texto.append(actual.getElemento());
            if (actual.getRight() != null) {
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
                return actual != null;
            }

            @Override
            public T next() {
                T elemento = actual.getElemento();
                actual = actual.getRight();
                return elemento;
            }
        };
    }
}
