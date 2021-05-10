package servija.model;

public enum eDiasSemana {
	DOMINGO("Domingo"), 
	SEGUNDA("Segunda-Feira"), 
	TERCA("Terça-Feira"), 
	QUARTA("Quarta-Feira"), 
	QUINTA("Quinta-Feira"), 
	SEXTA("Sexta-Feira"), 
	SABADO("Sábado");
	
	public String valor; 
	eDiasSemana(String dia) {
		valor = dia;
	}
	
	public String getValor() {
		return valor;
	}
	
	public static eDiasSemana lookup(String dia) {
		return servija.helper.LookupUtil.lookup(eDiasSemana.class, dia);
	}
}
