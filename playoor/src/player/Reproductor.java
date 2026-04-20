package player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Reproductor implements Runnable {
    private final String archivo;
    private Player player;
    private Thread hilo;
    private boolean reproduciendo;
    private boolean pausado;

    public Reproductor(String archivo) {
        this.archivo = archivo;
        this.reproduciendo = false;
    }

    // Inicia la reproducción en un nuevo hilo
    public void reproducir() {
        if (reproduciendo) {
            IO.println("Ya hay una reproducción en curso. Detenla primero.");
            return;
        }
        hilo = new Thread(this);
        hilo.start();
    }

    // Detiene la reproducción
    public void detener() {
        if (player != null) {
            player.close();
        }
        reproduciendo = false;
        hilo = null;
    }

    @Override
    public void run() {
        try {
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
            reproduciendo = true;
            IO.println("Reproduciendo: " + archivo);
            player.play(); // Bloquea hasta que termine el archivo o se cierre
        } catch (FileNotFoundException | JavaLayerException e) {
            IO.println("Error al reproducir: " + e.getMessage());
        } finally {
            reproduciendo = false;
            if (!pausado){
                IO.println("Reproducción finalizada.");
            }
        }
    }

    public boolean estaReproduciendo() {
        return reproduciendo;
    }

    public void pausar(){
        if (!reproduciendo)return;
        pausado = true;
        player.close();
        reproduciendo = false;
        IO.println("Pausado");
    }

    public void reanudar(){
        if (reproduciendo || !pausado){
            pausado = false;
            hilo = new Thread(this);
            hilo.start();
            IO.println("Reanudando...");
        }
    }
    public boolean estaPausado(){
        return pausado;
    }


}