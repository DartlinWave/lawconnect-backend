package com.qu3dena.lawconnect.backend.profiles.infrastructure.persistence.jpa.repositories;

import com.qu3dena.lawconnect.backend.profiles.domain.model.aggregates.ClientAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing client entities.
 * <p>
 * This repository provides methods for performing CRUD operations
 * and custom queries on {@link ClientAggregate} entities.
 *
 * @author GonzaloQu3dena
 * @since 1.0
 */
@Repository
public interface ClientRepository extends JpaRepository<ClientAggregate, UUID> {

    /**
     * Checks if a client exists by their DNI value.
     *
     * @param dni the DNI value to check
     * @return true if a client with the given DNI exists, false otherwise
     */
    boolean existsByDni_Value(String dni);

    /**
     * Finds a client by their DNI value.
     *
     * @param dni the DNI value of the client to find
     * @return an {@link Optional} containing the found {@link ClientAggregate}, or empty if no client is found
     */
    Optional<ClientAggregate> findByDni_Value(String dni);
}