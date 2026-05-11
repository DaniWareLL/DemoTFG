package com.sonik.service.impl;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Song;
import com.sonik.domain.model.User;
import com.sonik.domain.model.UserLibrary;
import com.sonik.domain.model.UserLibraryId;
import com.sonik.service.LibraryService;

import java.time.LocalDate;
import java.util.List;

public class LibraryServiceImpl implements LibraryService {

    @Override
    public void addFavouriteSong(Song song) throws DuplicateIdException, DataAccessException, IncorrectArgumentException, ObjectNotFoundException {
        User user = AppContext.getUserService().getPreferences().getUser();

        //  Verificar si la canción ya existe en la tabla Song poe Url publica
        if(!AppContext.getJpaSongRepository().existsUrl(song.getOriginalUrl())){
            AppContext.getJpaSongRepository().save(song);
        }

        //  Verificar si ya es favorito
        if (isFavourite(song)) {
            return;
        }

        //  Crear relación en UserLibrary
        UserLibrary entry = new UserLibrary(user, song, LocalDate.now());
        AppContext.getJpaUserLibraryRepository().create(entry);
    }

    @Override
    public void removeFavouriteSong(Song song) {
        try {
            // Asegurar que tenemos la canción persistida
            Song persistedSong = AppContext.getJpaSongRepository().findByUrl(song.getOriginalUrl());
            if (persistedSong == null) {
                return;
            }
            User user = AppContext.getUserService().getPreferences().getUser();
            UserLibraryId id = new UserLibraryId(user.getId(), persistedSong.getId());
            AppContext.getJpaUserLibraryRepository().delete(id);
        } catch (Exception ignored) {}
    }

    @Override
    public List<Song> getFavouriteSongs() {
        try {
            User user = AppContext.getUserService().getPreferences().getUser();
            return AppContext.getJpaUserLibraryRepository()
                    .findByUserId(user.getId())
                    .stream()
                    .map(UserLibrary::getSong)
                    .toList();
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public boolean isFavourite(Song song) throws DataAccessException, ObjectNotFoundException {
        // Buscar la canción persistida por URL
        Song persistedSong = AppContext.getJpaSongRepository().findByUrl(song.getOriginalUrl());
        if (persistedSong == null) {
            return false;
        }

        int userId = AppContext.getUserService().getPreferences().getUser().getId();
        return AppContext.getJpaUserLibraryRepository().exists(userId, persistedSong.getId());
    }

}
