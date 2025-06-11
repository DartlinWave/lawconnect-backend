package com.qu3dena.lawconnect.backend.profiles.application.internal.commandservices;

import com.qu3dena.lawconnect.backend.profiles.domain.model.aggregates.ClientAggregate;
import com.qu3dena.lawconnect.backend.profiles.domain.model.commands.CreateClientCommand;
import com.qu3dena.lawconnect.backend.profiles.domain.model.valueobjects.Dni;
import com.qu3dena.lawconnect.backend.profiles.domain.model.valueobjects.FullName;
import com.qu3dena.lawconnect.backend.profiles.domain.services.ClientCommandService;
import com.qu3dena.lawconnect.backend.profiles.infrastructure.persistence.jpa.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link ClientCommandService} interface.
 * <p>
 * This service handles commands related to client operations,
 * such as creating a new client.
 *
 * @author GonzaloQu3dena
 * @since 1.0
 */
@Service
@Transactional
public class ClientCommandServiceImpl implements ClientCommandService {

    private final ClientRepository clientRepository;

    /**
     * Constructs a new instance of {@code ClientCommandServiceImpl}.
     *
     * @param clientRepository the repository for managing client entities
     */
    public ClientCommandServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ClientAggregate> handle(CreateClientCommand command) {

        if (clientRepository.existsByDni_Value(command.dni()))
            throw new IllegalArgumentException("Client with DNI " + command.dni() + " already exists.");

        var client = ClientAggregate.create(
                command.userId(),
                new FullName(command.firstname(), command.lastname()),
                command.contactInfo(),
                new Dni(command.dni())
        );

        var saved = clientRepository.save(client);

        return Optional.of(saved);
    }
}