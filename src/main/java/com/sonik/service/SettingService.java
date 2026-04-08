package com.sonik.service;

import com.sonik.domain.exceptions.AudioExtractorException;

public interface SettingService {

    /**
     * Devuelve la versión actual de yt-dlp instalada.
     */
    String getToolVersion() throws AudioExtractorException;

    /**
     * Actualiza yt-dlp a la última versión disponible.
     * Necesario porque YouTube cambia su arquitectura frecuentemente.
     */
    String updateTool() throws AudioExtractorException;

}
