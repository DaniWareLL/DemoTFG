package com.martinLillo.ffmpeg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class demo {
    static void main() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "yt-dlp",
                "-f", "bestaudio",
                "--get-url",
                "ytsearch:" + "hola"
        );

        pb.redirectErrorStream(false); // Mezcla stdout + stderr para no perder nada
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // Imprime t0do lo que sale del proceso
        }

        process.waitFor();
    }
}
