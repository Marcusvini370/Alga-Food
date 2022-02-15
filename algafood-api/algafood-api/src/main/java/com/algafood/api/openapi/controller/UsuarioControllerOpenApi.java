package com.algafood.api.openapi.controller;

import java.util.List;

import com.algafood.api.dto.UsuarioDTO;
import com.algafood.api.dto.input.SenhaInput;
import com.algafood.api.dto.input.UsuarioComSenhaInput;
import com.algafood.api.dto.input.UsuarioInput;
import com.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    List<UsuarioDTO> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "ID do usuário inválido",
                content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
        content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class)))
    })
    UsuarioDTO buscar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId);

    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
    })
    UsuarioDTO adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true)
            UsuarioComSenhaInput usuarioInput);
    
    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
        content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class)))
    })
    UsuarioDTO atualizar(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            
            @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados",
                required = true)
            UsuarioInput usuarioInput);

    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
        content = @Content(mediaType = "application/json",schema = @Schema(implementation = Problem.class)))
    })
    void alterarSenha(
            @ApiParam(value = "ID do usuário", example = "1", required = true)
            Long usuarioId,
            
            @ApiParam(name = "corpo", value = "Representação de uma nova senha", 
                required = true)
            SenhaInput senha);
}