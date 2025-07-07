package com.qu3dena.lawconnect.backend.profiles.interfaces.rest;

import com.qu3dena.lawconnect.backend.profiles.domain.model.queries.GetClientByUserIdQuery;
import com.qu3dena.lawconnect.backend.profiles.domain.services.ClientCommandService;
import com.qu3dena.lawconnect.backend.profiles.domain.services.ClientQueryService;
import com.qu3dena.lawconnect.backend.profiles.interfaces.rest.resources.ClientResource;
import com.qu3dena.lawconnect.backend.profiles.interfaces.rest.resources.CreateClientResource;
import com.qu3dena.lawconnect.backend.profiles.interfaces.rest.transform.ClientResourceFromEntityAssembler;
import com.qu3dena.lawconnect.backend.profiles.interfaces.rest.transform.CreateClientCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/clients", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ClientsController {

    private final ClientCommandService commandService;
    private final ClientQueryService queryService;

    public ClientsController(ClientCommandService commandService, ClientQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create client profile", description = "Creates a new client profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client profile created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
    })
    public ResponseEntity<ClientResource> createClientProfile(
            @RequestBody CreateClientResource resource
    ) {
        var command = CreateClientCommandFromResourceAssembler.toCommandFromResource(resource);

        var client = commandService.handle(command);

        if (client.isEmpty())
            return ResponseEntity.badRequest().build();

        var clientResource = ClientResourceFromEntityAssembler.toResourceFromEntity(client.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(clientResource);
    }

    @GetMapping("{userId}")
    @Operation(summary = "Get client profile by User id", description = "Get client profile by User id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client profile found"),
            @ApiResponse(responseCode = "404", description = "Client profile not found")
    })
    public ResponseEntity<ClientResource> getClientProfileByDni(
            @PathVariable String userId
    ) {
        var query = new GetClientByUserIdQuery(UUID.fromString(userId));
        var maybeItem = queryService.handle(query);

        if (maybeItem.isEmpty())
            return ResponseEntity.notFound().build();

        var resource = ClientResourceFromEntityAssembler.toResourceFromEntity(maybeItem.get());
        return ResponseEntity.ok(resource);
    }
}
