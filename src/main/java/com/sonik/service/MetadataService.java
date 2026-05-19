package com.sonik.service;

import com.sonik.domain.exceptions.AudioExtractorException;
import com.sonik.domain.model.Song;

import java.util.List;

public interface MetadataService {

    /**
     * Searches for songs matching the given pattern and returns their metadata.
     * The search source and prefix are taken from the current user's session preferences.
     * @param searchPattern The search term to query
     * @return A list of Songs populated with metadata from the search results
     * @throws AudioExtractorException If the search or metadata parsing fails
     */
    List<Song> getMetadata(String searchPattern) throws AudioExtractorException;
}
