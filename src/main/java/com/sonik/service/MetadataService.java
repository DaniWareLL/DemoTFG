package com.sonik.service;

import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.model.Song;

import java.util.List;

public interface MetadataService {
    /**
     *
     * @param searchPattern
     * @return
     * @throws AudioExtractorException
     */
    List<Song> getMetadata(String searchPattern) throws AudioExtractorException;
}
