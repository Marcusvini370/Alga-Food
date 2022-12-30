package com.algafood.api.v1.openapi.controller;

import com.algafood.api.exceptionhandler.Problem;
import com.algafood.api.v1.model.UsuarioDTO;
import com.algafood.api.v1.model.input.SenhaInput;
import com.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algafood.api.v1.model.input.UsuarioInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Usuários")
@SecurityRequirement(name = "security_auth")
public interface UsuarioControllerOpenApi {

    @Operation(summary = "Lista os usuários")
    CollectionModel<UsuarioDTO> listar();

    @Operation(summary = "Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    UsuarioDTO buscar(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            Long usuarioId);

    @Operation(summary = "Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
    })
    UsuarioDTO adicionar(
            @RequestBody( description = "Representação de um novo usuário", required = true)
            UsuarioComSenhaInput usuarioInput);

    @Operation(summary = "Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    UsuarioDTO atualizar(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            Long usuarioId,

            @RequestBody( description = "Representação de um usuário com os novos dados",
                    required = true)
            UsuarioInput usuarioInput);

    @Operation(summary = "Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void alterarSenha(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            Long usuarioId,

            @RequestBody( description = "Representação de uma nova senha",
                    required = true)
            SenhaInput senha);
}