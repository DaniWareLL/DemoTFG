package com.sonik.infrastructure.audio;

import com.sonik.service.audio.AudioExtractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class YtDlpClient implements AudioExtractor {

    @Override
    public String execute (List<String> args) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(args);
        pb.redirectErrorStream(false);
        Process process = pb.start();

        StringBuilder output = new StringBuilder();
        StringBuilder error = new StringBuilder();

        // Hilo para stdout
        Thread outThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            } catch (IOException ignored) {}
        });

        // Hilo para stderr
        Thread errThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    error.append(line).append("\n");
                }
            } catch (IOException ignored) {}
        });

        outThread.start();
        errThread.start();

        int exitCode = process.waitFor();
        outThread.join();
        errThread.join();

        if (exitCode != 0) {
            System.out.println("YT-DLP ERROR:");
            System.out.println(error);
        }

        return output.toString().trim();
    }
}