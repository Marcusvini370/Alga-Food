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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algafood.core.validation.Groups;
import com.algafood.core.validation.ValorZeroIncluiDescricao;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

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

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@NotNull
	@PositiveOrZero // @DecimalMin("0")
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@CreationTimestamp // deve ser atribuído com data e hora local no momento em que a entidade for
						// salva pela primeira vez
	@Column(nullable = false, columnDefinition = "datetime") // tira os milisegundos no salvamento pro bd
	private OffsetDateTime dataCadastro;

	@UpdateTimestamp // atribuir hora e local sempre que fizer atualização da entidade
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	@Valid // valida tbm as propriedades de cozinha
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;

	@Embedded // Endereco faz parte da entidade Restaurante
	private Endereco endereco;

	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), // o
																											// JoinColumns
																											// define as
																											// colunas
																											// da chave
																											// estrangeira
																											// dessa
																											// tabela
																											// intermediária
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")) // o inverse define a coluna estrangeira da
																			// intermediária com referência a entidade
																			// FormaPagamento
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

}
