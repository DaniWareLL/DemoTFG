package com.sonik.service;

import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.model.Song;

public interface MetadataService {
    /**
     *
     * @param searchPattern
     * @return
     * @throws AudioExtractorException
     */
    Song getMetadata(String searchPattern) throws AudioExtractorException;
}
