package com.sonik.infrastructure.persistence;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.User;
import com.sonik.domain.repository.UserRepository;
import jakarta.persistence.*;

import java.util.List;

/**
 * Implementation of {@link com.sonik.domain.repository.UserRepository UserRepository}
 */
public class JpaUserRepository implements UserRepository {

    /**
     * Only one instance for every method
     */
    private final EntityManagerFactory emf;

    public JpaUserRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<User> findAll() throws DataAccessException {
        return List.of();
        // ¿Realmente lo necesitamos? Creo que no
    }

    @Override
    public User findById(Long id) throws DataAccessException, ObjectNotFoundException {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            User user = em.find(User.class, id);

            if(user == null){
                throw new ObjectNotFoundException("User with id " + id + " not found");
            }

            return user;

        } catch (PersistenceException ex) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public User findByUsername(String username) throws DataAccessException, ObjectNotFoundException {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.userName = :userName",
                    User.class
            );
            query.setParameter("userName", username);

            User user = query.getSingleResult();

            return user;

        } catch (NoResultException e) {
            throw new ObjectNotFoundException("User with name " + username + " not found");

        } catch (PersistenceException ex) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, ex);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void create(User user) throws DataAccessException, DuplicateIdException {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            em.persist(user);

            tx.commit();

        } catch (EntityExistsException ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DuplicateIdException(ex);

        } catch (PersistenceException | IllegalStateException ex) {
            handleRollbackAndThrow(ex, tx);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void delete(User user) throws DataAccessException, ObjectNotFoundException {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            User temp = em.find(User.class, user.getId());
            if (temp == null) {
                throw new ObjectNotFoundException("User not found");
            }
            em.remove(temp);

            tx.commit();

        } catch (PersistenceException | IllegalStateException e) {
            handleRollbackAndThrow(e, tx);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public boolean existsByUsername(String username) throws DataAccessException {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.userName = :userName",
                    Long.class
            );
            query.setParameter("userName", username);

            Long count = query.getSingleResult();
            return count != 0;
        } catch (PersistenceException ex) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private static void handleRollbackAndThrow(RuntimeException ex, EntityTransaction tx) throws DataAccessException {
        if (tx != null && tx.isActive()) {
            try {
                tx.rollback();
            } catch (Exception rollbackEx) {
                throw new DataAccessException(DataAccessException.REVERT_ERROR, rollbackEx);
            }
        }
        throw new DataAccessException(DataAccessException.CHANGES_REVERTED, ex);
    }
}
