package utilis;

import player.Reproductor;

import java.io.File;
import java.util.NoSuchElementException;

import lists.CDLlist;

public class Utils {

    private final String CARPETA_MUSICA = "playoor/musica";
    private final CDLlist<String> listaCanciones = new CDLlist<>();

    private Reproductor reproductor;

    public Utils() {
        this.setReproductor(null);
        cargarCanciones();
    }

    public void mostrarAyuda() {
        IO.println("\nComandos disponibles:");
        IO.println("  list              - Lista las canciones disponibles");
        IO.println("  play <número>     - Reproduce la canción con ese número");
        IO.println("  stop              - Detiene la reproducción actual");
        IO.println("  help / ayuda      - Muestra esta ayuda");
        IO.println("  exit / salir      - Sale del programa\n");
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
        String ruta = listaCanciones.next();
        reproductor = new Reproductor(ruta);
        reproductor.reproducir();
    }









    public Reproductor getReproductor() {
        return reproductor;
    }

    public void setReproductor(Reproductor reproductor) {
        this.reproductor = reproductor;
    }
}
