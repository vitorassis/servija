package servija.model;

public enum eDiasSemana {
	DOMINGO(1), 
	SEGUNDA(2), 
	TERCA(3), 
	QUARTA(4), 
	QUINTA(5), 
	SEXTA(6), 
	SABADO(7);
	
	public int valor; 
	eDiasSemana(int dia) {
		valor = dia;
	}
	
	public int getValor() {
		return valor;
	}
}
