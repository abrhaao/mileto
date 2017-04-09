package com.mileto.domain.business;

import com.mileto.domain.entity.RekProduto;

public class FciProducao {
	
	private RekProduto produto;
	private Double quantidade;
	
	public FciProducao(RekProduto produto) {
		super();
		this.produto = produto;
	}

	public RekProduto getProduto() {
		return produto;
	}

	public void setProduto(RekProduto produto) {
		this.produto = produto;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	
	
	

}
