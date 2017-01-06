package com.dms.erp.model;

/**
 * {@code UFBrasil} representa as Unidades da Federação do Brasil.
 * 
 * @author Diorgenes Morais
 * @version 1.0.0
 */
public enum UFBrasil {

	AC("Acre", "Rio Branco", Regions.NORTE), 
	AL("Alagoas", "Maceió", Regions.NORDESTE), 
	AP("Amapá", "Macapá", Regions.NORTE), 
	AM("Amazonas", "Manaus", Regions.NORTE), 
	BA("Bahia", "Salvador", Regions.NORDESTE), 
	CE("Ceará", "Fortaleza", Regions.NORDESTE), 
	DF("Distrito Federal", "Brasilia", Regions.CENTRO_OESTE), 
	ES("Espiríto Santo","Vitória", Regions.SUDESTE), 
	GO("Goiás", "Goiânia", Regions.CENTRO_OESTE), 
	MA("Maranhão", "São Luis", Regions.NORDESTE), 
	MT("Mato Grosso", "Cuiabá", Regions.CENTRO_OESTE), 
	MS("Mata Grosso do Sul", "Campo Grande", Regions.CENTRO_OESTE), 
	MG("Minas Gerais", "Belo Horizonte", Regions.SUDESTE), 
	PA("Pará", "Belém", Regions.NORTE), 
	PB("Paraíba", "João Pessoa", Regions.NORDESTE), 
	PR("Paraná", "Curitiba", Regions.SUL), 
	PE("Pernambuco", "Recife", Regions.NORDESTE), 
	PI("Piaui", "Teresina", Regions.NORDESTE), 
	RJ("Rio de Janeiro","Rio de Janeiro", Regions.SUDESTE), 
	RN("Rio Grande do Norte", "Natal", Regions.NORDESTE), 
	RS("Rio Grande do Sul", "Porto Alegre", Regions.SUL), 
	RO("Rondônia", "Porto Velho", Regions.NORTE), 
	RR("Roraima", "Boa Vista", Regions.NORTE), 
	SC("Santa Catarina", "Florianópolis", Regions.SUL), 
	SP("São Paulo","São Paulo", Regions.SUDESTE), 
	SE("Sergipe", "Aracaju", Regions.NORDESTE), 
	TO("Tocantins", "Palmas", Regions.NORTE);

	/**
	 * {@code Regions}
	 * 
	 * @author Diorgenes Morais
	 * @version 1.0.0
	 */
	public enum Regions {

		NORTE, SUL, LESTE, OESTE, NORDESTE, SUDESTE, CENTRO_OESTE
	}
	
	private String estado;
	private String capital;
	private Regions regiao;

	UFBrasil(String state, String capital, Regions region) {
		this.estado = state;
		this.capital = capital;
		this.regiao = region;
	}

	/**
	 * Estado brasileiro.
	 * 
	 * @return O Estado.
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Capital do Estado
	 * 
	 * @return A Capital do Estado brasileiro.
	 */
	public String getCapital() {
		return capital;
	}

	/**
	 * Região brasileira.
	 * 
	 * @return A Região da qual faz parte o Estado brasileiro.
	 */
	public Regions getRegiao() {
		return regiao;
	}
}
