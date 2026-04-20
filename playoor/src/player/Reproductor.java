package player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Reproductor implements Runnable {

    private Player player;
    private Thread hilo;
    private final String archivo;
    private final Object lock = new Object();
    private boolean reproduciendo;
    private boolean pausado;
    private boolean detenerManual = false;
    private volatile Runnable alTerminar;
    private volatile int framesPendientes = 0;

    public Reproductor(String archivo) {
        this.archivo = archivo;
        this.reproduciendo = false;
    }

    // Inicia la reproducción en un nuevo hilo
    public void reproducir() {
        if (reproduciendo || pausado) return;
        detenerManual = false;
        hilo = new Thread(this);
        hilo.setDaemon(true);
        hilo.start();
    }

    // Detiene la reproducción
    public void detener() {
        detenerManual = true;
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

            boolean hayMas = true;
            while (hayMas && !detenerManual) {
                synchronized (lock) {
                    while (pausado && !detenerManual) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                }
                if (detenerManual) break;

                if (framesPendientes > 0) {
                    try {
                        hayMas = player.play(framesPendientes);
                        framesPendientes = 0;
                        IO.println(">>> 5 segundos");
                    }catch (JavaLayerException e){
                        break;
                    }
                }else{
                    try{
                        hayMas = player.play(1);
                    }catch (JavaLayerException e){
                        if (!pausado) IO.println("Error:" + e.getMessage());
                        break;
                    }
                }
            }
            //player.play(); // Bloquea hasta que termine el archivo o se cierre
        } catch (FileNotFoundException | JavaLayerException e) {
            IO.println("Error al reproducir: " + e.getMessage());
        } finally {
            reproduciendo = false;
            if (!pausado && !detenerManual) {
                IO.println("Reproducción finalizada.");
                if (alTerminar != null) alTerminar.run();
            }
        }
    }

    public boolean estaReproduciendo() {
        return reproduciendo;
    }

    public void pausar() {
        if (!reproduciendo) return;
        pausado = true;
        reproduciendo = false;
        IO.println("Pausado");
    }

    public void reanudar() {
        //if (!reproduciendo && pausado) {
        if (!pausado) return;
        pausado = false;
        reproduciendo = true;
        synchronized (lock) {
            lock.notifyAll();
        }
        IO.println("Reanudando...");
    }

    public void seek(int frames) {
        if (!reproduciendo) {
            IO.println("No se está reproduciendo nada actualmente");
        }
    }

    public boolean estaPausado() {
        return pausado;
    }

    public void setAlTerminar(Runnable callback) {
        this.alTerminar = callback;
    }


}