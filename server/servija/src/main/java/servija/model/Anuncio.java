package servija.model;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "anuncios")
public class Anuncio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "anunciante_id", referencedColumnName = "usuario_id")
	private Anunciante anunciante;
	
	@ManyToOne
	@JoinColumn(name = "localidade_id", referencedColumnName = "id")
	private Localidade localidade;
	
	@ManyToOne
	@JoinColumn(name = "tpservico_id", referencedColumnName = "id")
	private TipoServico tipoServico;
	
	private String descricao;
	
	private String descrLonga;
	
	private double valor;
	
	private Time tempo;

	public Anuncio(int id, Anunciante anunciante, Localidade localidade, TipoServico tipoServico, String descricao,
			String descrLonga, double valor, Time tempo) {
		super();
		this.id = id;
		this.anunciante = anunciante;
		this.localidade = localidade;
		this.tipoServico = tipoServico;
		this.descricao = descricao;
		this.descrLonga = descrLonga;
		this.valor = valor;
		this.tempo = tempo;
	}

	public Anuncio() {
		super();
	}
	
	
	//============================================================

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Anunciante getAnunciante() {
		return anunciante;
	}

	public void setAnunciante(Anunciante anunciante) {
		this.anunciante = anunciante;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescrLonga() {
		return descrLonga;
	}

	public void setDescrLonga(String descrLonga) {
		this.descrLonga = descrLonga;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Time getTempo() {
		return tempo;
	}

	public void setTempo(Time tempo) {
		this.tempo = tempo;
	}
	
	
}
