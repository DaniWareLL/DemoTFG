package com.sonik.infrastructure.persistence;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.Song;
import com.sonik.domain.model.UserLibrary;
import com.sonik.domain.model.UserLibraryId;
import com.sonik.domain.repository.UserLibraryRepository;
import jakarta.persistence.*;

import java.util.List;

import static com.sonik.infrastructure.persistence.AuxiliaryMethods.handleRollbackAndThrow;

public class JpaUserLibraryRepository implements UserLibraryRepository {

    private EntityManagerFactory emf;

    public JpaUserLibraryRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(UserLibrary library) throws DataAccessException, DuplicateIdException {
        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {

            et = em.getTransaction();
            et.begin();
            em.persist(library);
            et.commit();

        } catch (EntityExistsException e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            throw new DuplicateIdException("User's library id already exists");
        } catch (IllegalArgumentException iae) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, iae);

        }
    }

    @Override
    public void delete(UserLibraryId id) throws DataAccessException {
        EntityTransaction et = null;
        try (EntityManager em = emf.createEntityManager()) {

            et = em.getTransaction();
            et.begin();
            UserLibrary entry = em.find(UserLibrary.class, id);
            if (entry != null) em.remove(entry);
            et.commit();
        } catch (IllegalArgumentException | PersistenceException iae) {
            handleRollbackAndThrow(iae, et);
        }
    }

    @Override
    public List<UserLibrary> findByUserId(int userId) throws DataAccessException {
        try (EntityManager em = emf.createEntityManager()) {

            List<UserLibrary> list = em.createQuery(
                    "SELECT ul FROM UserLibrary ul WHERE ul.user.id = :uid",
                    UserLibrary.class
            ).setParameter("uid", userId).getResultList();
            em.close();
            return list;
        } catch (Exception e) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, e);
        }
    }

    @Override
    public boolean exists(int userId, int songId) throws DataAccessException {
        try (EntityManager em = emf.createEntityManager()) {

            Long count = em.createQuery(
                            "SELECT COUNT(ul) FROM UserLibrary ul WHERE ul.user.id = :uid AND ul.song.id = :sid",
                            Long.class
                    ).setParameter("uid", userId)
                    .setParameter("sid", songId)
                    .getSingleResult();
            em.close();
            return count > 0;
        } catch (Exception e) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, e);
        }
    }
}
