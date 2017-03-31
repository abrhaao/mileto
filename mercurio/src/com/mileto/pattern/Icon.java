package com.mileto.pattern;

import java.util.Map;

public class Icon {
	
	private String label;
	private String url;
	
	/** Pasta de Imagens **/
	public static final String pathIcons = "/img/icon/";
	
	/** �cones de cores aceitas **/
	public static final String VERDE 	= "bola_verde.png";
	public static final String VERMELHO	= "bola_vermelha.png";
	public static final String AZUL		= "bola_azul.png";
	public static final String CINZA	= "bola_cinza.png";
	//public static final String GRAFITE	= "bola_grafite.png";
	public static final String LARANJA	= "bola_laranja.png";
	public static final String ROSA		= "bola_rosa.png";
	public static final String DEFAULT	= "bola_default.png";
	
	public Icon(String pLabel, String pIcon) {
		this.label = pLabel;
		this.url = pathIcons + pIcon;
	}
	
	/**
	 * Retorna o caminho completo da imagem gr�fica do �cone desejado. 
	 * Utilizado sobre os beans.
	 * Em caso de n�o haver uma imagem associada ao c�digo, exibe o �cone default.
	 */
	public static String getGraphicIcon(Map<String, Icon> map, String key) {		
		try {
			return ((Icon)map.get(key)).getUrl();
		} catch (NullPointerException e) {
			return new Icon("DEFAULT", Icon.DEFAULT).getUrl();		
		}		 
	}
		

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Icon other = (Icon) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	

	
	
}
