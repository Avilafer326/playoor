package utilis;

import player.Reproductor;

import java.awt.image.ImagingOpException;
import java.io.File;
import java.util.NoSuchElementException;

import lists.CDLlist;

public class Utils {

    private final String CARPETA_MUSICA = "playoor/musica";
    private final CDLlist<String> listaCanciones = new CDLlist<>();
    private boolean modoRepeat = false;


    private Reproductor reproductor;

    public Utils() {
        this.setReproductor(null);
        cargarCanciones();
    }

    public void mostrarAyuda() {
        IO.println("\nComandos disponibles:");
        IO.println("  list              - Lista las canciones disponibles");
        IO.println("  play <número>     - Reproduce la canción con ese número");
        IO.println("  pause             - Pausa o reanuda la reproducción actual");
        IO.println("  stop              - Detiene la reproducción actual");
        IO.println("  next              - Siguiente canción");
        IO.println("  back              - Canción anterior");
        IO.println("  shuffle           - Canción aleatoria");
        IO.println("  repeat            - Activa/desactiva la repetición de la canción actual");
        IO.println("  seek              - Avanza 5 segundos a la reproducción actual");
        IO.println("  help / ayuda      - Muestra esta ayuda");
        IO.println("  exit / salir      - Salir del programa\n");
    }

    public void listarCanciones() {
        try {
            listaCanciones.isEmpty();
        } catch (Exception e) {
            IO.println("No hay canciones en la lista.");
            return;
        }
        IO.println("Canciones disponibles:");
        int i = 1;
        for (String ruta : listaCanciones) {
            IO.println(i++ + ". " + new File(ruta).getName());
        }
    }

    private void cargarCanciones() {
        File carpeta = new File(CARPETA_MUSICA);
        if (!carpeta.exists() || !carpeta.isDirectory()) return;
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
        if (archivos == null) return;
        for (File f : archivos) {
            listaCanciones.add(f.getAbsolutePath());
        }
    }

    //----------------------------------------------------------------------------------- Reproducir canción
    //El metodo recibe el indice de la cancion que quiere reproducir
    public void reproducirCancion(int index) {

        //Lanza la excepcion si la lista está vacía
        try {
            listaCanciones.isEmpty();
        } catch (Exception e) {
            IO.println("No hay canciones para reproducir.");
            return;
        }


        if (index < 1 || index > listaCanciones.getIndice()) {
            IO.println("Número inválido. Usa list para ver los números.");
            return;
        }
        //Valida si la opción está dentro del rango


        if (reproductor != null && reproductor.estaReproduciendo()) {
            reproductor.detener();
        }
        //Si ya hay una canción sonando primero la detiene antes de reproducir la nueva

        String ruta = listaCanciones.getElementAt(index - 1).getElemento();
        reproductor = new Reproductor(ruta);
        reproductor.reproducir();
    }
//----------------------------------------------------------------------------------- Detener canción

    public void detenerReproduccion() {
        if (reproductor != null && reproductor.estaReproduciendo()) {
            reproductor.detener();
            IO.println("Reproducción detenida.");
        } else {
            IO.println("No hay ninguna reproducción activa.");
        }
    }

//----------------------------------------------------------------------------------- Pausar canción

    public void pausaReanudar() {
        if (reproductor == null || (!reproductor.estaReproduciendo() && !reproductor.estaPausado())) {
            IO.println("No hay ninguna reproducción activa");
            return;
        }
        if (reproductor.estaReproduciendo()){
            reproductor.pausar();
        }else{
            reproductor.reanudar();
        }

    }

    //----------------------------------------------------------------------------------- Anterior canción

    public void anterior(){
        try{
            listaCanciones.isEmpty();
        } catch (Exception e) {
            IO.println("No hay canciones en la lista");
            return;
        }
        if (reproductor != null && reproductor.estaReproduciendo()){
            reproductor.detener();
        }
        String ruta = listaCanciones.previous();
        reproductor = new Reproductor(ruta);
        reproductor.reproducir();
    }

    //----------------------------------------------------------------------------------- Siguiente canción

    public void siguiente(){
        try{
            listaCanciones.isEmpty();
        } catch (Exception e) {
            IO.println("No hay canciones en la lista");
            return;
        }
        if (reproductor != null && reproductor.estaReproduciendo()){
            reproductor.detener();
        }

        //Si el repeat está activo reproduce la misma cancion actual al terminar
        String ruta = modoRepeat ? listaCanciones.cursor() : listaCanciones.next();
        reproductor = new Reproductor(ruta);
        reproductor.reproducir();
    }

    //----------------------------------------------------------------------------------- Shuffle

    public void shuffle(){
        try{
            listaCanciones.isEmpty();
        } catch (Exception e) {
            IO.println("No hay canciones en la lista");
            return;
        }
        if (reproductor != null && reproductor.estaReproduciendo()){
            reproductor.detener();
        }

        int random = (int)(Math.random()*listaCanciones.getIndice());
        String ruta = listaCanciones.getElementAt(random).getElemento();
        reproductor= new Reproductor(ruta);
        reproductor.reproducir();
        IO.println("Shuffle: " + new File(ruta).getName());
    }

    //----------------------------------------------------------------------------------- Modo Repeat

    public void repeat(){
        modoRepeat = listaCanciones.repeat();
        IO.println("Repeat " + (modoRepeat ? "Activado" : "Desactivado"));
    }

    //----------------------------------------------------------------------------------- Adelantar la canción

    public void seek(){
        if (reproductor == null || !reproductor.estaReproduciendo()){
            IO.println("No se está reproduciendo nada actualmente");
            return;
        }
        reproductor.seek(150);
        IO.println(">>> 5 segundos");
    }




    public Reproductor getReproductor() {
        return reproductor;
    }

    public void setReproductor(Reproductor reproductor) {
        this.reproductor = reproductor;
    }
}
