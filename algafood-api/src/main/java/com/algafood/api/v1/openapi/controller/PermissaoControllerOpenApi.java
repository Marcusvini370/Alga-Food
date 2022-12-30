package com.algafood.api.v1.openapi.controller;

import com.algafood.api.v1.model.PermissaoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Permissões")
@SecurityRequirement(name = "security_auth")
public interface PermissaoControllerOpenApi {

    @Operation(summary = "Lista as permissões")
    CollectionModel<PermissaoDTO> listar();
    
}