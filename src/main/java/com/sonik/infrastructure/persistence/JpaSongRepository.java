package com.sonik.infrastructure.persistence;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Song;
import com.sonik.domain.repository.SongRepository;
import jakarta.persistence.*;
import static com.sonik.infrastructure.persistence.AuxiliaryMethods.handleRollbackAndThrow;


/**
 * Implementation of {@link com.sonik.domain.repository.SongRepository SongRepository}
 */
public class JpaSongRepository implements SongRepository {

    private EntityManagerFactory emf;

    public JpaSongRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Finds a {@link com.sonik.domain.model.Song Song} by id and returns it
     * @param id The id from the song to search for
     * @return The song with the corresponding id
     * @throws DataAccessException If JPA finds any errors when searching for the song
     * @throws ObjectNotFoundException If no song is found with such id
     */
    @Override
    public Song findById(Long id) throws DataAccessException, ObjectNotFoundException {
        try (EntityManager em = emf.createEntityManager()) {

            Song song = em.find(Song.class, id);
            if (song != null) {
                return song;
            }
            throw new ObjectNotFoundException("Song with id " + id + " not found");

        } catch (IllegalArgumentException iae) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, iae);
        }
    }

    /**
     * Saves a {@link com.sonik.domain.model.Song Song} to the database
     * @param song The song to save
     * @throws DuplicateIdException If a song with the same id already exists in the database
     * @throws DataAccessException If the database could not be accessed
     */
    @Override
    public void save(Song song) throws DuplicateIdException, DataAccessException {

        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {

            et = em.getTransaction();
            et.begin();
            em.persist(song);
            et.commit();

        } catch (EntityExistsException e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            throw new DuplicateIdException("Song with id " + song.getId() + " already exists");
        } catch (IllegalArgumentException iae) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, iae);

        }
    }

    /**
     * Deletes a {@link com.sonik.domain.model.Song Song} from the database
     * @param song The song to be deleted
     * @throws DataAccessException When a song can't be deleted
     */
    @Override
    public void delete(Song song) throws DataAccessException, ObjectNotFoundException {

        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {

            et = em.getTransaction();
            if (em.find(Song.class, song.getId()) == null) {
                throw new ObjectNotFoundException("The song you are trying to delete does not exist.");
            }
            et.begin();
            em.remove(song);
            et.commit();
        } catch (IllegalArgumentException | PersistenceException iae) {
            handleRollbackAndThrow(iae, et);
        }
    }
}
