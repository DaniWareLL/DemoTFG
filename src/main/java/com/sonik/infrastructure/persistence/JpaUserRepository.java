package com.sonik.infrastructure.persistence;

import com.sonik.domain.exceptions.DataAccessException;
import com.sonik.domain.exceptions.DuplicateIdException;
import com.sonik.domain.exceptions.ObjectNotFoundException;
import com.sonik.domain.model.User;
import com.sonik.domain.repository.UserRepository;
import jakarta.persistence.*;

import java.util.List;

import static com.sonik.infrastructure.persistence.AuxiliaryMethods.handleRollbackAndThrow;

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
    public User findById(Long id) throws DataAccessException, ObjectNotFoundException {

        try (EntityManager em = emf.createEntityManager();) {

            User user = em.find(User.class, id);

            if(user == null){
                throw new ObjectNotFoundException("User with id " + id + " not found");
            }

            return user;

        } catch (PersistenceException ex) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, ex);
        }
    }

    @Override
    public User findByUsername(String username) throws DataAccessException, ObjectNotFoundException {

        try (EntityManager em = emf.createEntityManager()) {

            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.userName = :userName",
                    User.class
            );
            query.setParameter("userName", username);

            User user = query.getSingleResult();

            return user;

        } catch (NoResultException e) {
            throw new ObjectNotFoundException("User with name " + username + " not found");
        } catch (NonUniqueResultException nure) {
            throw new DataAccessException("Found more than one user with the same name, try searching by user id.", nure);
        } catch (PersistenceException ex) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, ex);
        }
    }

    @Override
    public void create(User user) throws DataAccessException, DuplicateIdException {

        EntityTransaction tx = null;

        try (EntityManager em = emf.createEntityManager();) {

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
        }
    }

    @Override
    public void delete(User user) throws DataAccessException, ObjectNotFoundException {

        EntityTransaction tx = null;
        try (EntityManager em = emf.createEntityManager();) {

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
        }
    }

    @Override
    public boolean existsByUsername(String username) throws DataAccessException {

        try (EntityManager em = emf.createEntityManager()) {

            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.userName = :userName",
                    Long.class
            );
            query.setParameter("userName", username);

            Long count = query.getSingleResult();
            return count != 0;
        } catch (NoResultException nrfe) {
            return false;
        } catch (PersistenceException ex) {
            throw new DataAccessException(DataAccessException.CONNECTION_ERROR, ex);
        }
    }
}
