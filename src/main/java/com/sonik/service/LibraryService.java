package com.sonik.service;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Song;

import java.util.List;

/**
 * Handles the user's favourite songs (library)
 */
public interface LibraryService {

    void addFavouriteSong(Song song) throws ObjectNotFoundException, DataAccessException, IncorrectArgumentException, DuplicateIdException;

    void removeFavouriteSong(Song song);

    List<Song> getFavouriteSongs();

    public boolean isFavourite(Song song) throws ObjectNotFoundException, DataAccessException;
}
