package com.sonik.infrastructure.ytDlp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Patrón Facade: esta clase actúa como una fachada que simplifica el uso de un
 * subsistema complejo (la ejecución de procesos del sistema operativo y el uso
 * de la herramienta externa yt-dlp). Desde fuera, el cliente solo necesita
 * llamar a un métod0 simple sin conocer detalles de procesos, streams o
 * configuración.
 *
 * Patrón Conector: la clase también funciona como un conector hacia un sistema
 * externo (yt-dlp), encapsulando la comunicación con un proceso nativo y
 * ofreciendo una interfaz Java limpia para interactuar con él.
 */

public class YtDlpClient {

    /**
     *
     * @param args
     * @return
     * @throws Exception
     */
    public String execute (List<String> args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(args);
        pb.redirectErrorStream(false);
        Process process = pb.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        process.waitFor();
        return output.toString().trim();
    }
}