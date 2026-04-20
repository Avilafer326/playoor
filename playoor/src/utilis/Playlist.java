package utilis;

import queue.*;
import java.io.File;

public class Playlist {

    private final String nombre;
    private final DQueue<String> canciones;

    public Playlist(String nombre){
        this.nombre = nombre;
        this.canciones = new DQueue<>();
    }

    public void agregarCanciones(String ruta){
        canciones.enqueue(ruta);
        IO.println(new File(ruta).getName() + " agregada");
    }

    public String siguienteCancion(){
        return canciones.dequeue();
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
