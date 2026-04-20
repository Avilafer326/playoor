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
        }  else if (comando.equals("pause")) {
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
        } else if (comando.equals("help") || comando.equals("ayuda")) {
            utilis.mostrarAyuda();
        } else {
            IO.println("Comando no reconocido. Escribe 'help' para ver los comandos.");
        }
    }

}
