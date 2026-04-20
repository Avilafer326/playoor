import utilis.Playlist;
import utilis.Utils;

void main(String[] args) {
    Utils utilis = new Utils();

    IO.println("=== Reproductor MP3 en modo texto ===");
    utilis.mostrarAyuda();

    while (true) {
        IO.print("> ");
        String comando = IO.readln().trim().toLowerCase();

        if (comando.equals("exit") || comando.equals("salir")) {
            if (utilis.getReproductor() != null && utilis.getReproductor().estaReproduciendo()) {
                utilis.getReproductor().detener();
            }
            IO.println("¡Hasta luego!");
            break;
        } else if (comando.equals("list") || comando.equals("lista")) {
            utilis.listarCanciones();
        } else if (comando.startsWith("play ")) {
            String indexStr = comando.substring(5).trim();
            try {
                int index = Integer.parseInt(indexStr);
                utilis.reproducirCancion(index);
            } catch (NumberFormatException e) {
                IO.println("Debes indicar el número de canción. Ejemplo: play 2");
            }
        } else if (comando.equals("pause")) {
            utilis.pausaReanudar();
        } else if (comando.equals("stop")) {
            utilis.detenerReproduccion();
        } else if (comando.equals("next")) {
            utilis.siguiente();
        } else if (comando.equals("back")) {
            utilis.anterior();
        } else if (comando.equals("shuffle")) {
            utilis.shuffle();
        } else if (comando.equals("repeat")) {
            utilis.repeat();
        } else if (comando.equals("seek")) {
            utilis.seek();
        } else if (comando.equals("newpl")) {
            IO.print("Nombre de la playlist: ");
            String nombre = IO.readln().trim();
            Playlist pl = new Playlist(nombre);
            utilis.listarCanciones();
            IO.println("Escribe los números de canciones (0 para terminar):");
            while (true) {
                IO.print("  Canción #: ");
                String entrada = IO.readln().trim();
                try {
                    int idx = Integer.parseInt(entrada);
                    if (idx == 0) break;
                    utilis.agregarAPlaylist(pl, idx);
                } catch (NumberFormatException e) {
                    IO.println("Número inválido.");
                }
            }
            utilis.agregarPlaylist(pl);
        } else if (comando.equals("playlists")) {
            utilis.reproducirStackPlaylists();
        } else if (comando.equals("editpl")) {
            IO.print("Nombre de la playlist: ");
            String nombre = IO.readln().trim();
            Playlist pl = utilis.buscarPlaylist(nombre);
            boolean esNueva = false;
            if (pl == null) {
                IO.println("Playlist no encontrada. Creando nueva...");
                pl = new Playlist(nombre);
                esNueva = true;
            }

            while (true) {
                IO.println("\n--- Editando: " + nombre + " (" + pl.size() + " canciones) ---");
                IO.println("add         - Agregar canción");
                IO.println("remove      - Eliminar canción");
                IO.println("view        - Ver canciones en la playlist");
                IO.println("save        - Guardar y salir");
                IO.println("cancel      - Cancelar sin guardar");
                IO.print("> ");

                String opcion = IO.readln().trim().toLowerCase();

                if (opcion.equals("add")) {
                    utilis.listarCanciones();
                    IO.print("  Canción #: ");
                    try {
                        int idx = Integer.parseInt(IO.readln().trim());
                        utilis.agregarAPlaylist(pl, idx);
                    } catch (NumberFormatException e) {
                        IO.println("Número inválido.");
                    }

                } else if (opcion.equals("remove")) {
                    pl.listarCanciones();
                    IO.print("  Canción #: ");
                    try {
                        int idx = Integer.parseInt(IO.readln().trim());
                        pl.eliminarCancion(idx);
                    } catch (NumberFormatException e) {
                        IO.println("Número inválido.");
                    }

                } else if (opcion.equals("view")) {
                    pl.listarCanciones();

                } else if (opcion.equals("save")) {
                    if (pl.size() == 0) {
                        IO.println("La playlist está vacía, no se guardó.");
                    } else if (esNueva) {
                        utilis.agregarPlaylist(pl);
                    } else {
                        IO.println("Playlist '" + nombre + "' actualizada.");
                    }
                    break;

                } else if (opcion.equals("cancel")) {
                    IO.println("Playlist cancelada.");
                    break;
                } else {
                    IO.println("Opción no reconocida.");
                }
            }
        } else if (comando.equals("help") || comando.equals("ayuda")) {
            utilis.mostrarAyuda();
        } else {
            IO.println("Comando no reconocido. Escribe 'help' para ver los comandos.");
        }
    }
}