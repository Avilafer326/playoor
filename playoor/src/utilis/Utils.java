package utilis;

import player.Reproductor;

import java.io.File;

public class Utils {

    private  final String CARPETA_MUSICA = "playoor/musica";
    private Reproductor reproductor ;

    public Utils() {
        this.setReproductor(null);
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
        File carpeta = new File(CARPETA_MUSICA);
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            IO.println("La carpeta '" + CARPETA_MUSICA + "' no existe. Créala y agrega archivos MP3.");
            return;
        }

        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
        if (archivos == null || archivos.length == 0) {
            IO.println("No hay archivos MP3 en la carpeta '" + CARPETA_MUSICA + "'.");
        } else {
            IO.println("Canciones disponibles:");
            for (int i = 0; i < archivos.length; i++) {
                IO.println((i + 1) + ". " + archivos[i].getName());
            }
        }
    }

    public void reproducirCancion(int index) {
        File carpeta = new File(CARPETA_MUSICA);
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
        if (archivos == null || archivos.length == 0) {
            IO.println("No hay canciones para reproducir.");
            return;
        }
        if (index < 1 || index > archivos.length) {
            IO.println("Número de canción inválido. Usa list para ver los números.");
            return;
        }

        // Si hay una reproducción actual, la detenemos
        if (reproductor != null && reproductor.estaReproduciendo()) {
            reproductor.detener();
        }

        String rutaCompleta = archivos[index - 1].getAbsolutePath();
        reproductor =  new Reproductor(rutaCompleta);
        reproductor.reproducir();
    }

    public void detenerReproduccion() {
        if (reproductor != null && reproductor.estaReproduciendo()) {
            reproductor.detener();
            IO.println("Reproducción detenida.");
        } else {
            IO.println("No hay ninguna reproducción activa.");
        }
    }

    public Reproductor getReproductor() {
        return reproductor;
    }

    public void setReproductor(Reproductor reproductor) {
        this.reproductor = reproductor;
    }
}
