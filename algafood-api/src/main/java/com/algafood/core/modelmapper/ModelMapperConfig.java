package com.algafood.core.modelmapper;

import com.algafood.api.v1.model.EnderecoDTO;
import com.algafood.api.v1.model.input.ItemPedidoInput;
import com.algafood.api.v2.model.input.CidadeInputV2;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.model.Endereco;
import com.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
                .addMappings(mapper -> mapper.skip(Cidade::setId));

//		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class) //classe que queremos transcrever uma para outra
//		.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete); //adicionar um mapeamento

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        //mapeamento de tipo do endreço pro type model/dto
        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);

        //origem e destino aonde quer atribuir o tipo dele é string, destino e valor = src , src pega a origem do nome do estado
        enderecoToEnderecoModelTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoDtoDest, value) -> enderecoDtoDest.getCidade().setEstado(value));
        return modelMapper;
    }


}
