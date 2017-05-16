package com.mileto.domain.entity;

import java.util.Date;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import com.mileto.domain.business.BoardMessage;
import com.mileto.persistence.DataProviderSingleton;

public class MovCarregamento implements Comparable {
	
	//private Date momento;
	private String placa;
	private String motorista;
	private String pedido;	
	private String status;
	private String instrucao;
	private String produto;
	private RekTransportadora transportadora;
	
	
	public MovCarregamento(String pedido, String placa, String motorista, String status, String instrucao, String produto) {
		super();
		this.pedido = pedido;
		this.placa = placa;
		this.motorista = motorista;
		this.status = status;
		this.instrucao = instrucao;
		this.produto = produto;
	
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
	
	
	

}
