package com.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algafood.core.validation.ValorZeroIncluiDescricao;


import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	
	@Column(nullable = false)
	private String nome;

	
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@CreationTimestamp // deve ser atribuído com data e hora utc no momento em que a entidade for salva pela primeira vez
	@Column(nullable = false, columnDefinition = "datetime") // tira os milisegundos no salvamento pro bd
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp // atribuir hora e utc sempre que fizer atualização da entidade
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;


	@Embedded // Endereco faz parte da entidade Restaurante
	private Endereco endereco;
	
	private boolean ativo = Boolean.TRUE; //Instancia true como padrão

	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), 
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}

}
