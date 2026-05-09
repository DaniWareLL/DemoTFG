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

        if (AppContext.getJpaUserLibraryRepository().exists(user.getId(), song.getId()))
            return; //  Ya existe

        UserLibrary entry = new UserLibrary(user, song, LocalDate.now());
        AppContext.getJpaUserLibraryRepository().create(entry);
    }

    @Override
    public void removeFavouriteSong(Song song) {
        try {
            User user = AppContext.getUserService().getPreferences().getUser();
            UserLibraryId id = new UserLibraryId(user.getId(), song.getId());
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
}
