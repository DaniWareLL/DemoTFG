package com.martinLillo.pruebas.vlcj;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class YoutubeAudioPlayerVLCJ {

    static Scanner sc = new Scanner(System.in);

    /**
     * Obtiene la URL directa del audio usando yt-dlp
     * Es una herramienta en línea de comandos que sirve para obtener un enlace temporal cifrado con solo el audio
     *
     * yt-dlp -> Ejecutable
     *
     * -f bestaudio -> format: (mejor calidad posible)
     *
     * --get-url -> Si no pusieramos esta opción, la función principal de la herramienta sería descargar el archivo.
     * En este caso solo imprime en stdout la URL directa del stream de YouTube.
     * Este enlace firmado caduca en minutos.
     *
     * ytsearch -> Busca en YouTube usando la cadena como consulta
     * IMPORTANTE (Obtiene el primer video de la búsqueda)
     *
     */
    public static String getStreamUrl(String videoUrl) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
                "yt-dlp",
                "-f", "bestaudio",
                "--get-url",
                "ytsearch:" + videoUrl

        );

        /* No se mezclan los errores por stdout que es donde yt-dlp imprime la URL
        * Si se ejecuta demo.java se entenderá el porqué -> En el proceso lanza unos WARNINGS
        */
        pb.redirectErrorStream(false);
        Process process = pb.start();


        /* Leemos la salida línea a línea del proceso
         * La comprobación se hacía por si el proceso devolvía alguna línea más de funcionamiento aparte de la URL
         * Si diese algún error hay que descomentarla
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String linea;
        String url = null;
        while ((linea = reader.readLine()) != null) {
                url = linea;
        }

        process.waitFor();

        if (url == null) {
            throw new RuntimeException("No se pudo obtener la URL del audio");  // Si... una runtime
        }

        return url;
    }

    /**
     * Descarga la cancion en .mp3 en el root del proyecto
     * @param busqueda
     * @throws Exception
     */
    public static void descargarMp3(String busqueda) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
                "yt-dlp",
                "-f", "bestaudio",
                "--extract-audio",
                "--audio-format", "mp3",
                "--audio-quality", "0",
                "--ffmpeg-location", "./ffmpeg-8.0.1-essentials_build/bin/ffmpeg.exe",
                "ytsearch:" + busqueda
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);
        }

        process.waitFor();
    }


    public static void getVersionYt_dlp () throws IOException {
        ProcessBuilder pb = new ProcessBuilder("yt-dlp", "--version");
        Process p = pb.start();
        DataInputStream dis = new DataInputStream(p.getInputStream());
        String line;
        while ((line = dis.readLine()) != null) {
            System.out.println("Version -> "+line);
        }
    }

    /**
     * Hay que ejecutar este comando asiduamente para tener la más alta versión del programa
     * Al rozar los límites legales, YouTube hay veces que cambia su arquitectura interna y yt-dlp tiene que cambiar también minimamente.
     * @throws IOException
     */
    public static void updateVersionYt_dlp () throws IOException {
        ProcessBuilder pb = new ProcessBuilder("yt-dlp", "-U");
        Process p = pb.start();
        DataInputStream dis = new DataInputStream(p.getInputStream());
        String line;
        while ((line = dis.readLine()) != null) {
            System.out.println(line);
        }
    }

    static void main(String[] args) {


        AudioPlayerVLCJ vlcj = new AudioPlayerVLCJ();

        try {
            getVersionYt_dlp();
            updateVersionYt_dlp();

            System.out.println("Introduzca el nombre de la canción: ");
            String busqueda = sc.nextLine();

            System.out.println("Buscando canción...");
            String streamUrl = getStreamUrl(busqueda);

            System.out.println("URL obtenida:");
            System.out.println(streamUrl);


            descargarMp3(busqueda);

            vlcj.playStream(streamUrl);

            System.out.println("Pulsa ENTER para detener la reproducción...");
            System.in.read();

            vlcj.stop();
            vlcj.release();

        } catch (Exception e) {
            e.printStackTrace();
            vlcj.release();
        }

    }
}