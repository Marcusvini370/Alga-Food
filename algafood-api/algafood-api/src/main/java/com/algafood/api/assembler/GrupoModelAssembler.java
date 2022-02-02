package com.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.api.dto.GrupoDTO;
import com.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public GrupoDTO toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDTO.class);
    }
    
    public List<GrupoDTO> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toModel(grupo))
                .collect(Collectors.toList());
    }
}
