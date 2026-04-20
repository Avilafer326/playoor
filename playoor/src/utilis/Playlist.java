package utilis;

import queue.*;
import stack.*;
import java.io.File;

public class Playlist {

    private final String nombre;
    private final DQueue<String> canciones;
    private final DStack<String> historial = new DStack<>();

    public Playlist(String nombre){
        this.nombre = nombre;
        this.canciones = new DQueue<>();
    }

    public void agregarCanciones(String ruta){
        canciones.enqueue(ruta);
        IO.println(new File(ruta).getName() + " agregada");
    }

    public String siguienteCancion(){
        String cancion = canciones.dequeue();
        historial.push(cancion);
        return cancion;
    }

    public String anteriorCancion() {
        if (historial.isEmpty()) return null;

        String actual = historial.pop();
        if (historial.isEmpty()){
            historial.push(actual);
            return actual;
        }

        String anterior = historial.pop();
        historial.push(anterior);
        canciones.enqueue(actual);
        return anterior;
    }
    public void listarCanciones() {
        if (estaVacia()) {
            IO.println("La playlist está vacía.");
            return;
        }
        IO.println("Canciones en la playlist:");
        // Vaciamos, mostramos y reconstruimos
        DQueue<String> temp = new DQueue<>();
        int i = 1;
        while (!estaVacia()) {
            String ruta = canciones.dequeue();
            IO.println(i++ + ". " + new File(ruta).getName());
            temp.enqueue(ruta);
        }
        // Restaurar
        while (true) {
            try {
                temp.isEmpty();
                canciones.enqueue(temp.dequeue());
            } catch (Exception e) {
                break;
            }
        }
    }

    public void eliminarCancion(int index) {
        if (estaVacia()) {
            IO.println("La playlist está vacía.");
            return;
        }
        DQueue<String> temp = new DQueue<>();
        int i = 1;
        boolean eliminada = false;
        while (!estaVacia()) {
            String ruta = canciones.dequeue();
            if (i == index) {
                IO.println("Eliminada: " + new File(ruta).getName());
                eliminada = true;
            } else {
                temp.enqueue(ruta);
            }
            i++;
        }
        if (!eliminada) IO.println("Número inválido.");
        // Restaurar
        while (true) {
            try {
                temp.isEmpty();
                canciones.enqueue(temp.dequeue());
            } catch (Exception e) {
                break;
            }
        }
    }


    public boolean tieneAnterior() {
        return !historial.isEmpty();
    }

    public boolean estaVacia(){
        try{
            canciones.isEmpty();
            return false;
        }catch (Exception e){
            return true;
        }
    }

    public int size(){
        return canciones.size();
    }

    public String getNombre(){
        return nombre;
    }

    public void mostrar(){
        IO.println("Playlist: " + nombre + " (" + size() + " canciones)");
    }




}
