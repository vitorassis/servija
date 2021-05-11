package servija.controller.reqBodies.base;

import java.sql.Time;

public class Anuncio {
	public int localidade;
	public int tipoServico;
	public String descricao;
	public String descLonga;
	public double valor;
	public String tempo;
	public Disponibilidade[] disponibilidades;
	public String [] imagens;
}
