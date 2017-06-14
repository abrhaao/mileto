package com.mileto.domain.entity;

import java.time.Instant;

public class MovCarregamento implements Comparable {
	
	//private Date momento;
	private String placa;
	private String veiculo;
	private String veiculoCidade;
	private String doca;
	private String motorista;
	private String pedido;	
	private String status;
	private String instrucao;
	private String produto;
	private RekTransportadora transportadora;
	
	/** Como esta entidade é monitorada em painés online, faz-se necessário os seguintes campos **/
	private Instant dataUltimaModificacao;
	

	public MovCarregamento(String pedido, String placa, String veiculo, String veiculoCidade, 
						   String motorista, String status, String instrucao, String produto, 
						   String transportadora, String icone, String doca) {
		super();
		this.pedido = pedido;
		this.placa = placa;
		this.veiculo = veiculo;
		this.veiculoCidade = veiculoCidade;
		this.motorista = motorista;
		this.status = status;
		this.instrucao = instrucao;
		this.produto = produto;
		this.doca = doca;
		
		RekTransportadora t = new RekTransportadora();
		t.setCodigo(null);
		t.setRazaoSocial(transportadora);
		t.setLogotipo(icone);
		
		this.transportadora = t;
	
	}

	
	@Override
	public int compareTo(Object myOtherObject) {
		String d = ((MovCarregamento)myOtherObject).getPedido();
		return this.pedido.compareTo(d);		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovCarregamento other = (MovCarregamento) obj;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.getPedido()))
			return false;
		return true;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMotorista() {
		return motorista;
	}

	public void setMotorista(String motorista) {
		this.motorista = motorista;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInstrucao() {
		return instrucao;
	}

	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public RekTransportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(RekTransportadora transportadora) {
		this.transportadora = transportadora;
	}


	public Instant getDataUltimaModificacao() {
		return dataUltimaModificacao;
	}


	public void setDataUltimaModificacao(Instant dataUltimaModificacao) {
		this.dataUltimaModificacao = dataUltimaModificacao;
	}


	public String getVeiculo() {
		return veiculo;
	}


	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}


	public String getVeiculoCidade() {
		return veiculoCidade;
	}


	public void setVeiculoCidade(String veiculoCidade) {
		this.veiculoCidade = veiculoCidade;
	}


	public String getDoca() {
		return doca;
	}


	public void setDoca(String doca) {
		this.doca = doca;
	}
	
	
	
	

}
