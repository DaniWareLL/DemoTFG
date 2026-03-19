package com.sonik.services.streaming;

import com.sonik.infrastructure.ytDlp.YtDlpClient;

import java.util.List;

public class StreamingService {

    private final YtDlpClient client;
    private final String searchPrefix;

    public StreamingService(YtDlpClient client, String searchPrefix) {
        this.client       = client;
        this.searchPrefix = searchPrefix;
    }

    /**
     * Obtiene la URL directa del stream de audio usando yt-dlp.
     * El enlace firmado caduca en minutos.
     *
     * @param searchPattern nombre de la canción
     * @return URL directa del stream de audio
     */
    public String[] getStreamUrl(String searchPattern) throws Exception {
        String result = client.execute(List.of(
                "yt-dlp",
                "-f",        "bestaudio",
                "--get-url",
                searchPrefix + searchPattern
        ));

        if (result == null || result.isBlank()) {
            throw new RuntimeException("No se pudo obtener la URL del audio");
        }

        // yt-dlp puede devolver múltiples líneas, nos quedamos con la última
        return result.split("\n");
    }
}