package servija.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "disponibilidades")
public class Disponibilidade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "anuncio_id", referencedColumnName = "id")
	@JsonBackReference
	private Anuncio anuncio;
	
	@Transient
	private eDiasSemana diaEnum;
	
	@Column(name = "dia_semana", nullable = false)
	private String diaSemana;
	
	@Column(name = "hr_ini", nullable = false)	
	private Time inicio;
	
	@Column(name = "hr_fim", nullable =  false)
	private Time fim;

	public Disponibilidade(int id, Anuncio anuncio, String diaSemana, Time inicio, Time fim) {
		super();
		this.id = id;
		this.anuncio = anuncio;
		this.setDiaSemana(diaSemana);
		this.inicio = inicio;
		this.fim = fim;
	}

	public Disponibilidade() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Anuncio getAnuncio() {
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio) {
		this.anuncio = anuncio;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaEnum = eDiasSemana.lookup(diaSemana);
		if(diaEnum != null)
			this.diaSemana = diaSemana;
		else
			this.diaSemana = null;
	}

	public Time getInicio() {
		return inicio;
	}

	public void setInicio(Time inicio) {
		this.inicio = inicio;
	}

	public Time getFim() {
		return fim;
	}

	public void setFim(Time fim) {
		this.fim = fim;
	}
	
	
}
