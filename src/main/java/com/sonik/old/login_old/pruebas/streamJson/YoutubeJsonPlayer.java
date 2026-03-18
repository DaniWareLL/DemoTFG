package com.sonik.old.login_old.pruebas.streamJson;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;
import org.json.*;

public class YoutubeJsonPlayer {

    static Scanner sc = new Scanner(System.in);

    public static class StreamInfo {
        public String url;
        public Map<String, String> headers;
    }

    public static StreamInfo getStreamInfo(String query) throws Exception {

        ProcessBuilder pb = new ProcessBuilder(
                "yt-dlp",
                "-f", "bestaudio",
                "-J",
                "ytsearch:" + query
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder json = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            // Filtrar warnings que rompen el JSON
            if (line.startsWith("{") || line.startsWith("[")) {
                json.append(line);
            }
        }

        process.waitFor();

        StreamInfo info = new StreamInfo();
        info.headers = new HashMap<>();

        JSONObject root = new JSONObject(json.toString());
        JSONObject entry = root.getJSONArray("entries").getJSONObject(0);

        info.url = entry.getString("url");

        JSONObject headers = entry.getJSONObject("http_headers");
        for (String key : headers.keySet()) {
            info.headers.put(key, headers.getString(key));
        }

        return info;
    }

    public static void playStream_ffmpeg(StreamInfo info) throws Exception {

        List<String> cmd = new ArrayList<>();
        cmd.add("ffmpeg-8.0.1-essentials_build\\bin\\ffmpeg.exe");

        for (Map.Entry<String, String> h : info.headers.entrySet()) {
            cmd.add("-headers");
            cmd.add(h.getKey() + ": " + h.getValue());
        }

        cmd.add("-i");
        cmd.add(info.url);

        cmd.add("-vn");
        cmd.add("-f");
        cmd.add("s16le");
        cmd.add("-ac");
        cmd.add("2");
        cmd.add("-ar");
        cmd.add("44100");
        cmd.add("pipe:1");

        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(false);

        Process ffmpeg = pb.start();

        new Thread(() -> {
            try (BufferedReader err = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
                while (err.readLine() != null) {}
            } catch (IOException ignored) {}
        }).start();

        AudioFormat format = new AudioFormat(44100, 16, 2, true, false);
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

    public static void main(String[] args) {
        try {
            System.out.println("Introduzca el nombre de la canción: ");
            String busqueda = sc.nextLine();

            System.out.println("Buscando canción...");
            StreamInfo info = getStreamInfo(busqueda);

            System.out.println("URL obtenida:");
            System.out.println(info.url);

            System.out.println("Reproduciendo audio...");
            playStream_ffmpeg(info);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}