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
