package com.sonik.infrastructure.persistence;

import com.sonik.domain.exceptions.DataAccessException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

public class AuxiliaryMethods {

    protected static void handleRollbackAndThrow(RuntimeException ex, EntityTransaction tx) throws DataAccessException {
        try {
            tx.rollback();
        } catch (IllegalStateException | PersistenceException | NullPointerException rollbackEx) {
            throw new DataAccessException(DataAccessException.REVERT_ERROR, rollbackEx);
        }
        throw new DataAccessException(DataAccessException.CHANGES_REVERTED, ex);
    }

}
