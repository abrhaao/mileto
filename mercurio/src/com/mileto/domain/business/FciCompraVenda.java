package com.mileto.domain.business;

import com.mileto.domain.entity.RekProduto;

public class FciCompraVenda {

	private RekProduto protuo;
	private Double quantidade;
	private Double vi;
	private Double valor;
	private String compraVenda;
	
	public FciCompraVenda(String compraVenda, RekProduto protuo) {
		super();
		this.protuo = protuo;
		this.compraVenda = compraVenda;
	}
	public RekProduto getProduto() {
		return protuo;
	}
	public void setProduto(RekProduto protuo) {
		this.protuo = protuo;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getVi() {
		return vi;
	}
	public void setVi(Double vi) {
		this.vi = vi;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public String getCompraVenda() {
		return compraVenda;
	}
	public void setCompraVenda(String compraVenda) {
		this.compraVenda = compraVenda;
	}
	
	
}
