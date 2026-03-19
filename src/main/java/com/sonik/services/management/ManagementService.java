package com.sonik.services.management;

import com.sonik.infrastructure.ytDlp.YtDlpClient;

import java.util.List;

public class ManagementService {

    private final YtDlpClient client;

    public ManagementService(YtDlpClient client) {
        this.client = client;
    }

    /**
     * Devuelve la versión actual de yt-dlp instalada.
     */
    public String getVersion() throws Exception {
        return client.execute(List.of("yt-dlp", "--version"));
    }

    /**
     * Actualiza yt-dlp a la última versión disponible.
     * Necesario porque YouTube cambia su arquitectura frecuentemente.
     */
    public String update() throws Exception {
        return client.execute(List.of("yt-dlp", "-U"));
    }
}