package com.sonik.infrastructure.persistence;

import com.sonik.config.AppContext;
import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.IncorrectArgumentException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.model.PlaylistsSongs;
import com.sonik.domain.model.Song;
import com.sonik.domain.repository.PlaylistRepository;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static com.sonik.infrastructure.persistence.AuxiliaryMethods.handleRollbackAndThrow;

/**
 * Implementation of {@link com.sonik.domain.repository.PlaylistRepository PlaylistRepository}
 */
public class JpaPlaylistRepository implements PlaylistRepository {

    private final EntityManagerFactory emf;

    public JpaPlaylistRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Playlist> findAllByUsername(String username) throws DataAccessException, ObjectNotFoundException, IncorrectArgumentException {

        try (EntityManager em = emf.createEntityManager()) {

            AppContext.getJpaUserRepository().findByUsername(username);
            TypedQuery<Playlist> query = em.createQuery("SELECT p FROM Playlist p WHERE p.user.userName =: username", Playlist.class);
            query.setParameter("username", username);
            List<Playlist> playlists = query.getResultList();
            return query.getResultList();

        } catch (PersistenceException | IllegalStateException e) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, e);
        }
    }

    @Override
    public void addSongToPlaylist(Playlist playlist, Song song) throws DuplicateIdException, IncorrectArgumentException, DataAccessException {

        if (song.getAggregationDate() == null) {
            song.setAggregationDate(LocalDate.now());
        }
        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {


            tx = em.getTransaction();
            tx.begin();

            AppContext.getJpaSongRepository().save(song);

            playlist = em.find(Playlist.class, playlist.getId());
            song = em.find(Song.class, song.getId());

            for (PlaylistsSongs playlistSong : playlist.getSongs()) {
                if (playlistSong.getSong().getTitle().equalsIgnoreCase(song.getTitle())) {
                    throw new DuplicateIdException("A song already exists with the same title.");
                }
            }

            PlaylistsSongs relation = new PlaylistsSongs(playlist, song, LocalDate.now());
            em.persist(relation);
            playlist.getSongs().add(relation);
            tx.commit();
        } catch (PersistenceException | IllegalStateException e) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                    throw new DataAccessException(DataAccessException.CHANGES_REVERTED, e);
                } catch (PersistenceException | IllegalStateException ex) {
                    e.printStackTrace();
                    throw new DataAccessException(DataAccessException.REVERT_ERROR, e);
                }
            }
        }
    }

    @Override
    public void removeSongFromPlaylist(Playlist playlist, Song song) throws DataAccessException {

        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager()) {

            tx = em.getTransaction();
            tx.begin();
            playlist = em.find(Playlist.class, playlist.getId());
            song = em.find(Song.class, song.getId());

            Iterator<PlaylistsSongs> iterator = playlist.getSongs().iterator();
            while (iterator.hasNext()) {

                PlaylistsSongs playlistSong = iterator.next();
                if (playlistSong.getSong().getTitle().equalsIgnoreCase(song.getTitle())) {
                    iterator.remove();
                }
            }

            em.persist(playlist);
            tx.commit();
        } catch (PersistenceException | IllegalStateException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
                throw new DataAccessException(DataAccessException.CHANGES_REVERTED, e);
            }
        }
    }

    /**
     * Finds a {@link com.sonik.domain.model.Playlist Playlist} by id and returns it
     * @param id The id from the playlist to search for
     * @return The playlist with the corresponding id
     * @throws DataAccessException If JPA finds any errors when searching for the playlist
     * @throws ObjectNotFoundException If no playlist is found with such id
     */
    public Playlist findById(Long id) throws DataAccessException, ObjectNotFoundException {
        try (EntityManager em = emf.createEntityManager()) {

            Playlist playlist = em.find(Playlist.class, id);
            if (playlist != null) {
                return playlist;
            }
            throw new ObjectNotFoundException("Playlist with id " + id + " not found");

        } catch (IllegalArgumentException iae) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, iae);
        }
    }

    public Playlist findByName(String name) throws DataAccessException, ObjectNotFoundException {
        try (EntityManager em = emf.createEntityManager()) {

            TypedQuery<Playlist> query = em.createQuery("SELECT p FROM Playlist p WHERE p.name =: name", Playlist.class);
            query.setParameter("name", name);

            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new ObjectNotFoundException("Playlist with name " + name + " not found");
        } catch (NonUniqueResultException nure) {
            throw new DataAccessException("Found more than one playlist with the same name, try searching by playlist id.", nure);
        } catch (PersistenceException ex) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, ex);
        }
    }

    /**
     * Saves a {@link com.sonik.domain.model.Playlist Playlist} to the database
     * @param playlist The playlist to save
     * @throws DuplicateIdException If a playlist with the same id already exists in the database
     * @throws DataAccessException If the database could not be accessed
     */
    public void save(Playlist playlist) throws DuplicateIdException, DataAccessException {

        EntityTransaction tx = null;

        try (EntityManager em = emf.createEntityManager()) {

            try {
                findByName(playlist.getName());
                throw new DuplicateIdException("Playlist with name " + playlist.getName()+ " already exists");
            } catch (ObjectNotFoundException e) {

                tx = em.getTransaction();
                tx.begin();

                em.persist(playlist);

                tx.commit();
            }

        } catch (EntityExistsException ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DuplicateIdException(ex);

        } catch (PersistenceException | IllegalStateException ex) {
            handleRollbackAndThrow(ex, tx);
        }
    }

    /**
     * Deletes a {@link com.sonik.domain.model.Playlist Playlist} from the database
     * @param playlist The playlist to be deleted
     * @throws DataAccessException When a playlist can't be deleted
     */
    public void delete(Playlist playlist) throws DataAccessException, ObjectNotFoundException {

        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {

            et = em.getTransaction();

            et.begin();
            if ((playlist = em.find(Playlist.class, playlist.getId())) == null) {
                throw new ObjectNotFoundException("The playlist you are trying to delete does not exist.");
            }
            em.remove(playlist);
            et.commit();

        } catch (IllegalArgumentException | PersistenceException iae) {
            handleRollbackAndThrow(iae, et);
        }
    }

}
