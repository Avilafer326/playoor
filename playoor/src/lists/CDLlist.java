package lists;

import nodo.Nodo;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CDLlist<T> implements Lists<T>, Iterable<T> {

    private final Nodo<T> sentinela;
    private Nodo<T> cursor;
    private long indice;
    private boolean flag;

    public CDLlist() {
        this.sentinela = new Nodo<>();
        this.sentinela.setRight(sentinela);
        this.sentinela.setLeft(sentinela);
        this.cursor = sentinela;
        this.indice = 0;
        this.flag = false;
    }

    @Override
    public boolean isEmpty() throws NoSuchElementException {
        if (indice == 0) {
            throw new NoSuchElementException("Lista vacia");
        }
        return false;
    }

    @Override
    public Nodo<T> getLastElement() {
        isEmpty();
        return sentinela.getLeft();
    }

    @Override
    public Nodo<T> getPrevElement(T elemento) {
        isEmpty();
        if (elemento == null) {
            return null;
        }
        Nodo<T> actual = sentinela;
        while (actual.getRight() != sentinela) {
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
        Nodo<T> actual = sentinela.getRight();
        while (actual != sentinela) {
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
        Nodo<T> actual = sentinela.getRight();
        long contador = 0;
        while (actual != sentinela) {
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
        Nodo<T> actual = sentinela.getRight();
        while (actual != sentinela) {
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
        Nodo<T> ultimo = sentinela.getLeft();

        nuevo.setLeft(ultimo);
        nuevo.setRight(sentinela);
        ultimo.setRight(nuevo);
        sentinela.setLeft(nuevo);
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
        if (nodo == null || nodo == sentinela) {
            return;
        }
        Nodo<T> actual = sentinela.getRight();
        while (actual != sentinela) {
            if (actual == nodo) {
                Nodo<T> anterior = actual.getLeft();
                Nodo<T> siguiente = actual.getRight();
                anterior.setRight(siguiente);
                siguiente.setLeft(anterior);
                if (cursor == actual) {
                    cursor = siguiente == sentinela ? sentinela.getRight() : siguiente;
                }
                actual.setLeft(null);
                actual.setRight(null);
                indice--;
                if (indice == 0) {
                    cursor = sentinela;
                }
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
        Nodo<T> siguiente = actual.getRight();
        Nodo<T> nuevo = new Nodo<>(elemento);
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
        if (actual == null || actual.getRight() == sentinela) {
            return;
        }
        remove(actual.getRight());
    }

    @Override
    public void addStart(T elemento) {
        if (elemento == null) {
            return;
        }
        Nodo<T> primero = sentinela.getRight();
        Nodo<T> nuevo = new Nodo<>(elemento);
        nuevo.setLeft(sentinela);
        nuevo.setRight(primero);
        sentinela.setRight(nuevo);
        primero.setLeft(nuevo);
        if (indice == 0) {
            sentinela.setLeft(nuevo);
        }
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
        Nodo<T> actual = sentinela.getRight();
        while (actual != sentinela) {
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
        Nodo<T> actual = sentinela.getRight();
        while (actual != sentinela) {
            texto.append(actual.getElemento());
            if (actual.getRight() != sentinela) {
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
            private Nodo<T> actual = sentinela.getRight();

            @Override
            public boolean hasNext() {
                return actual != sentinela;
            }

            @Override
            public T next() {
                T elemento = actual.getElemento();
                actual = actual.getRight();
                return elemento;
            }
        };
    }

    public T next() {
        isEmpty();
        if (cursor == sentinela) {
            cursor = sentinela.getRight();
        } else {
            cursor = cursor.getRight();
            if (!flag && cursor == sentinela) {
                cursor = cursor.getRight();
            }
        }
        return cursor.getElemento();
    }

    public T previous() {
        isEmpty();
        if (cursor == sentinela) {
            cursor = sentinela.getLeft();
        } else {
            cursor = cursor.getLeft();
            if (!flag && cursor == sentinela) {
                cursor = cursor.getLeft();
            }
        }
        return cursor.getElemento();
    }

    public T cursor() {
        isEmpty();
        if (cursor == sentinela) {
            cursor = sentinela.getRight();
        }
        return cursor.getElemento();
    }

    public long getIndice() {
        return indice;
    }

    public boolean repeat() {
        flag = !flag;
        return flag;
    }
}
