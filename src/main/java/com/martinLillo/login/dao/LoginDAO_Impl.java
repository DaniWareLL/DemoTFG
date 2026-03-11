package com.martinLillo.login.dao;

import com.martinLillo.login.exceptions.DataAccessException;
import com.martinLillo.login.exceptions.DuplicateIdException;
import com.martinLillo.login.exceptions.ObjectNotFoundException;
import com.martinLillo.login.model.User;
import jakarta.persistence.*;

public class LoginDAO_Impl implements LoginDAO {

    /**
     * Only one instance for every method
     */
    private final EntityManagerFactory emf;

    public LoginDAO_Impl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void createUser(User user) throws DataAccessException, DuplicateIdException {
        EntityTransaction tx = null;
        if(!existsUser(user)) {
            try {
                EntityManager em = emf.createEntityManager();
                tx = em.getTransaction();
                tx.begin();
                em.persist(user);
                System.out.println("****USUARIO CREADO****");
                em.getTransaction().commit();
                em.close();
            } catch (EntityExistsException ex) {
                throw new DuplicateIdException(ex);
            } catch (PersistenceException | IllegalStateException e) {
                if (tx != null) {
                    tx.rollback();
                    throw new DataAccessException(DataAccessException.CHANGES_REVERTED, e);
                } else {
                    throw new DataAccessException(DataAccessException.REVERT_ERROR, e);
                }
            }
        }
    }

    @Override
    public void deleteUser(User user) throws DataAccessException {
        EntityTransaction tx = null;
        try {
            EntityManager em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            User temp = em.find(User.class, user.getUserId());
            em.remove(temp);
            em.getTransaction().commit();
            em.close();
        } catch (PersistenceException | IllegalStateException e) {
            if (tx != null) {
                tx.rollback();
                throw new DataAccessException(DataAccessException.CHANGES_REVERTED, e);
            } else {
                throw new DataAccessException(DataAccessException.REVERT_ERROR, e);
            }
        }
    }

    @Override
    public boolean existsUser(User user) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = :userName",
                User.class
        );
        query.setParameter("userName", user.getUserName());

        return !query.getResultList().isEmpty();
    }

    @Override
    public void updateUser(User user) throws DataAccessException, ObjectNotFoundException {
        EntityTransaction tx = null;
        try {
            EntityManager em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            User temp = em.find(User.class, user.getUserId());
            em.merge(temp);
            tx.commit();
        } catch (PersistenceException | IllegalStateException e) {
            if (tx != null) {
                tx.rollback();
                throw new DataAccessException(DataAccessException.CHANGES_REVERTED, e);
            } else {
                throw new DataAccessException(DataAccessException.REVERT_ERROR, e);
            }
        } catch (IllegalArgumentException iae) {
            throw new ObjectNotFoundException();
        }
    }

    @Override
    public User findUser(String userName) throws ObjectNotFoundException {
        EntityManager em = emf.createEntityManager();
        final TypedQuery <User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = :userName", User.class);
        query.setParameter("userName", userName);
        User user = query.getSingleResult();
        if (user == null) throw new ObjectNotFoundException("User not found");
        return user;
    }
}
