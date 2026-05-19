package com.sonik.infrastructure.persistence;

import com.sonik.domain.exceptions.DataAccessException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

public class AuxiliaryMethods {

    /**
     * Attempts to roll back the given transaction and throws a {@link DataAccessException} regardless of outcome.
     * @param ex The exception that triggered the rollback
     * @param tx The transaction to roll back
     * @throws DataAccessException <ul>
     *     <li>{@link DataAccessException#REVERT_ERROR} If the rollback itself fails</li>
     *     <li>{@link DataAccessException#CHANGES_REVERTED} If the rollback succeeds</li>
     * </ul>
     */
    protected static void handleRollbackAndThrow(RuntimeException ex, EntityTransaction tx) throws DataAccessException {
        try {
            tx.rollback();
        } catch (IllegalStateException | PersistenceException | NullPointerException rollbackEx) {
            throw new DataAccessException(DataAccessException.REVERT_ERROR, rollbackEx);
        }
        throw new DataAccessException(DataAccessException.CHANGES_REVERTED, ex);
    }

}
