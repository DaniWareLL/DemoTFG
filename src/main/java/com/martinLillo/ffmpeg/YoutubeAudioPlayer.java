package com.martinLillo.ffmpeg;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;

public class YoutubeAudioPlayer {

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
         * La comprobación se hacía por si el proceso devolvia alguna línea más de funcionamiento aparte de la URL
         * Si diese algún error hay que descomentarla
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String linea;
        String url = null;
        while ((linea = reader.readLine()) != null) {
//            if (linea.startsWith("http")) {
                url = linea;
//                break;
//            }
        }

        process.waitFor();

        if (url == null) {
            throw new RuntimeException("No se pudo obtener la URL del audio");  // Si... una runtime
        }

        return url;
    }

    /* Convertir el stream de audio usando ffmpeg y reproducirlo
     * Es una herramienta de línea de comandos capaz de leer, convertir, procesar, grabar, transmitir y reproducir prácticamente cualquier formato multimedia que exista.
     * Es el motor que usan miles de programas por debajo (VLC, OBS, Discord, WhatsApp, Instagram...)
     *
     * ffmpeg.exe -> Ejecutable
     * -i @streamUrl -> Usa la URL anteriormente generada
     * -vn -> Ignora video (solo audio)
     * -f s16le -> Formato al que lo convierte (streamable) (sin comprimir) (wav -> no es streamable)
     * pipe:1 -> lo envia por stdout (no a un archivo)
     */
    public static void playStream_ffmpeg(String streamUrl) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg-8.0.1-essentials_build\\bin\\ffmpeg.exe",
                "-i", streamUrl,
                "-vn",
                "-f", "s16le",
                "-ac", "2",
                "-ar", "44100",
                "pipe:1"

        );

        // NO mezclar stderr con stdout por lo ya comentado anteriormente
        pb.redirectErrorStream(false);

        Process ffmpeg = pb.start();

        // Ignorar logs de FFmpeg para que no pete y se llene
        new Thread(() -> {
            try (BufferedReader err = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                while (err.readLine() != null) {}
            } catch (IOException ignored) {/*No hago nada y lo correcto seria enviarlo a algun lado*/}
        }).start();

        // Formato PCM RAW
        AudioFormat format = new AudioFormat(
                44100,
                16,
                2,
                true,
                false
        );

        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format);
        line.start();

        InputStream pcm = ffmpeg.getInputStream();
        byte[] buffer = new byte[4096];
        int n;

        while ((n = pcm.read(buffer)) != -1) {
            line.write(buffer, 0, n);
        }

        line.drain();
        line.stop();
        line.close();

    }

    static void main(String[] args) {
        try {
            System.out.println("Introduzca el nombre de la canción: ");
            String busqueda = sc.nextLine();

            System.out.println("Buscando canción...");
            String streamUrl = getStreamUrl(busqueda);

            System.out.println("URL obtenida:");
            System.out.println(streamUrl);

            System.out.println("Reproduciendo audio...");
            playStream_ffmpeg(streamUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}