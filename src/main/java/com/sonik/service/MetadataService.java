package com.sonik.service;

import com.sonik.domain.model.Song;

public interface MetadataService {
    Song getMetadata(String searchPattern) throws Exception;
}
