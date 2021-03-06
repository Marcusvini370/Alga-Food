package com.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algafood.api.AlgaLinks;
import com.algafood.api.controller.UsuarioController;
import com.algafood.api.dto.UsuarioDTO;
import com.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    public UsuarioModelAssembler() {
    	super(UsuarioController.class, UsuarioDTO.class);
	}

	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
    
	@Override
    public UsuarioDTO toModel(Usuario usuario) {
    	  UsuarioDTO usuarioModel = createModelWithId(usuario.getId(), usuario);
          modelMapper.map(usuario, usuarioModel);
          
          usuarioModel.add(algaLinks.linkToUsuarios("usuarios"));
          
          usuarioModel.add(algaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
          
          return usuarioModel;
    }
    
	  @Override
	    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
	        return super.toCollectionModel(entities)
	            .add(linkTo(UsuarioController.class).withSelfRel());
	    }            
}      