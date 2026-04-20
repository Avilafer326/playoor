package nodo;
//TDA definicion de un nodo
public class Nodo<T> {
    private T elemento;
    private Nodo<T> right,left;

    public Nodo(T elemento) {
        this.elemento = elemento;
        right = null;
        left = null;
    }

    public Nodo<T> getLeft() {
        return left;
    }

    public void setLeft(Nodo<T> left) {
        this.left = left;
    }
    public Nodo(){
        this(null);
    }

    public T getElemento() {
        return elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public Nodo<T> getRight() {
        return right;
    }

    public void setRight(Nodo<T> right) {
        this.right = right;
    }
}
