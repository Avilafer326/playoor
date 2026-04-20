package utilis;

import lists.CDLlist;
import player.Reproductor;
import stack.DStack;

import java.io.File;

public class Utils {

    private final String CARPETA_MUSICA = "playoor/musica";
    private final CDLlist<String> listaCanciones = new CDLlist<>();
    private final DStack<Playlist> stackPlaylists = new DStack<>();
    private boolean modoRepeat = false;
    private boolean reproduciendoPlaylist = false;


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
        IO.println("  newpl             - Crea una nueva playlist");
        IO.println("  playlists         - Reproduce el stack de playlists");
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
        if (reproductor != null && (reproductor.estaReproduciendo() || reproductor.estaPausado())) {
            reproduciendoPlaylist = false;
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
        if (reproductor.estaReproduciendo()) {
            reproductor.pausar();
        } else {
            reproductor.reanudar();
        }

    }

    //----------------------------------------------------------------------------------- Anterior canción

    public void anterior() {
        if (reproduciendoPlaylist) {
            try {
                Playlist actual = stackPlaylists.peek();
                if (actual.tieneAnterior()) {
                    if (reproductor != null) reproductor.detener();
                    String ruta = actual.anteriorCancion();
                    reproductor = new Reproductor(ruta);
                    reproductor.setAlTerminar(() -> {
                        if (reproduciendoPlaylist) reproducirSiguienteStack();
                    });
                    reproductor.reproducir();
                } else {
                    IO.println("No hay canción anterior en esta playlist.");
                }
            } catch (Exception e) {
                IO.println("Error al retroceder.");
            }
            return;
        }

        try {
            listaCanciones.isEmpty();
        } catch (Exception e) {
            IO.println("No hay canciones en la lista");
            return;
        }
        if (reproductor != null && reproductor.estaReproduciendo()) {
            reproductor.detener();
        }
        String ruta = listaCanciones.previous();
        reproductor = new Reproductor(ruta);
        reproductor.reproducir();
    }

    //----------------------------------------------------------------------------------- Siguiente canción

    public void siguiente() {
        try {
            listaCanciones.isEmpty();
        } catch (Exception e) {
            IO.println("No hay canciones en la lista");
            return;
        }

        if (reproductor != null) {
            reproductor.setAlTerminar(null);
            reproductor.detener();
        }

        if (reproduciendoPlaylist) {
            reproducirSiguienteStack();
        } else {

            //Si el repeat está activo reproduce la misma cancion actual al terminar
            String ruta = modoRepeat ? listaCanciones.cursor() : listaCanciones.next();
            reproductor = new Reproductor(ruta);
            reproductor.reproducir();
        }
    }

    //----------------------------------------------------------------------------------- Shuffle

    public void shuffle() {
        try {
            listaCanciones.isEmpty();
        } catch (Exception e) {
            IO.println("No hay canciones en la lista");
            return;
        }
        if (reproductor != null && reproductor.estaReproduciendo()) {
            reproductor.detener();
        }

        int random = (int) (Math.random() * listaCanciones.getIndice());
        String ruta = listaCanciones.getElementAt(random).getElemento();
        reproductor = new Reproductor(ruta);
        reproductor.reproducir();
        IO.println("Shuffle: " + new File(ruta).getName());
    }

    //----------------------------------------------------------------------------------- Modo Repeat

    public void repeat() {
        modoRepeat = listaCanciones.repeat();
        IO.println("Repeat " + (modoRepeat ? "Activado" : "Desactivado"));
    }

    //----------------------------------------------------------------------------------- Adelantar la canción

    public void seek() {
        if (reproductor == null || !reproductor.estaReproduciendo()) {
            IO.println("No se está reproduciendo nada actualmente");
            return;
        }
        reproductor.seek(500);
        IO.println(">>> Adelantando");
    }

    public Reproductor getReproductor() {
        return reproductor;
    }

    public void setReproductor(Reproductor reproductor) {
        this.reproductor = reproductor;
    }

    //----------------------------------------------------------------------------------- Métodos para el playlist

    public void agregarPlaylist(Playlist playlist) {
        stackPlaylists.push(playlist);
        IO.println("Playlist '" + playlist.getNombre() + "' agregada");
    }

    public void reproducirStackPlaylists() {
        if (stackPlaylists.isEmpty()) {
            IO.println("No hay playlists en el stack");
            return;
        }
        reproduciendoPlaylist = true;
        reproducirSiguienteStack();
    }

    public void reproducirSiguienteStack() {
        try {
            Playlist actual = stackPlaylists.peek();
            if (!actual.estaVacia()) {
                String ruta = actual.siguienteCancion();
                if (reproductor != null && reproductor.estaReproduciendo()) {
                    reproductor.detener();
                }
                reproductor = new Reproductor(ruta);
                reproductor.setAlTerminar(() -> {
                    if (reproduciendoPlaylist) reproducirSiguienteStack();
                });
                reproductor.reproducir();
            } else {
                stackPlaylists.pop();
                IO.println("Playlist terminada. Pasando a la siguiente...");
                if (!stackPlaylists.isEmpty()) {
                    reproducirSiguienteStack();
                } else {
                    IO.println("Todas las playlist termiaron");
                    reproduciendoPlaylist = false;
                }
            }
        } catch (Exception e) {
            IO.println("No hay más playlists");
            reproduciendoPlaylist = false;
        }
    }

    public void agregarAPlaylist(Playlist playlist, int index) {
        if (index < 1 || index > listaCanciones.getIndice()) {
            IO.println("Número inválido");
            return;
        }
        String ruta = listaCanciones.getElementAt(index - 1).getElemento();
        playlist.agregarCanciones(ruta);
    }
}
