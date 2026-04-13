package com.sonik.infrastructure.persistence;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Playlist;
import com.sonik.domain.repository.PlaylistRepository;
import jakarta.persistence.*;

import static com.sonik.infrastructure.persistence.AuxiliaryMethods.handleRollbackAndThrow;

/**
 * Implementation of {@link com.sonik.domain.repository.PlaylistRepository PlaylistRepository}
 */
public class JpaPlaylistRepository implements PlaylistRepository {

    private final EntityManagerFactory emf;

    public JpaPlaylistRepository(EntityManagerFactory emf) {
        this.emf = emf;
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

            Playlist playlist = query.getSingleResult();

            return playlist;
        } catch (NoResultException e) {
            throw new ObjectNotFoundException("Playlist with name " + name + " not found");
        } catch (NonUniqueResultException nure) {
            throw new DataAccessException("Found more than one playlist with the same name, try searching by user id.", nure);
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

        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {

            et = em.getTransaction();
            et.begin();
            em.persist(playlist);
            et.commit();

        } catch (EntityExistsException e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            throw new DuplicateIdException("Playlist with id " + playlist.getId() + " already exists");
        } catch (IllegalArgumentException iae) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, iae);
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

            if (em.find(Playlist.class, playlist.getId()) == null) {
                throw new ObjectNotFoundException("The playlist you are trying to delete does not exist.");
            }
            et.begin();
            em.remove(playlist);
            et.commit();

        } catch (IllegalArgumentException | PersistenceException iae) {
            handleRollbackAndThrow(iae, et);
        }
    }
}
