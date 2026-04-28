# Descripción del Proyecto Playoor

**Playoor** es un reproductor de música MP3 en modo texto implementado en Java que ofrece una experiencia de reproducción de audio completa a través de una interfaz de línea de comandos. [1](#0-0) 

## Características Principales

- **Reproducción de MP3**: Utiliza la biblioteca JLayer para decodificar y reproducir archivos MP3 [2](#0-1) 
- **Interfaz de Línea de Comandos**: Control completo mediante comandos textuales como `play`, `pause`, `stop`, `next`, `back` [3](#0-2) 
- **Gestión de Playlists**: Creación, edición y reproducción de listas de reproducción personalizadas [4](#0-3) 
- **Modos de Reproducción**: Soporta shuffle (aleatorio) y repeat (repetición) [5](#0-4) 

## Arquitectura

El sistema sigue una arquitectura en tres capas: [6](#0-5) 

1. **Capa de Interfaz (CLI)**: `Main.java` maneja la entrada del usuario y ejecuta comandos
2. **Capa de Control (Utils)**: `Utils.java` gestiona el estado de la biblioteca musical y las playlists
3. **Capa de Audio (Reproductor)**: `Reproductor.java` maneja la reproducción en hilos separados para no bloquear la interfaz

## Funcionalidades Adicionales

- **Navegación Bidireccional**: Permite moverse hacia adelante y atrás en las listas de reproducción [7](#0-6) 
- **Búsqueda y Selección**: Comando `seek` para saltar a canciones específicas [8](#0-7) 
- **Gestión de Estado**: Mantiene el estado de reproducción actual y permite pausar/reanudar [9](#0-8) 
- **Descarga Automática**: Escanea automáticamente la carpeta `playoor/musica` en busca de archivos MP3 [10](#0-9) 

## Notas

El proyecto utiliza estructuras de datos personalizadas como `CDLlist` para la biblioteca musical y `DStack` para gestionar playlists, implementando un sistema de reproducción robusto y eficiente completamente en modo texto.
