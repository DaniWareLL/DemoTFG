package com.sonik.service;

public interface SettingService {

    /**
     * Devuelve la versión actual de yt-dlp instalada.
     */
    String getToolVersion() throws Exception;

    /**
     * Actualiza yt-dlp a la última versión disponible.
     * Necesario porque YouTube cambia su arquitectura frecuentemente.
     */
    String updateTool() throws Exception;

}
