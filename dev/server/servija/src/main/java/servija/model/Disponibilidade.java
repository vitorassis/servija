package servija.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
	
	@Column(name = "dia_semana", nullable = false)
	private int diaSemana;
	
	@Column(name = "hr_ini", nullable = false)	
	private LocalTime inicio;
	
	@Column(name = "hr_fim", nullable =  false)
	private LocalTime fim;

	public Disponibilidade(int id, Anuncio anuncio, int diaSemana, LocalTime inicio, LocalTime fim) {
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
	
	

	public Disponibilidade(Anuncio anuncio, int diaSemana, String hrIni, String hrFim) {
		super();
		this.anuncio = anuncio;
		this.setDiaSemana(diaSemana);
		setInicio(hrIni);
		setFim(hrFim);
	}

	private void setFim(String hrFim) {
		this.fim = LocalTime.of(Integer.parseInt(hrFim.split(":")[0]), 
				Integer.parseInt(hrFim.split(":")[1]));
		
	}

	private void setInicio(String hrIni) {
		this.inicio = LocalTime.of(Integer.parseInt(hrIni.split(":")[0]), 
				Integer.parseInt(hrIni.split(":")[1]));
		
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

	public int getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(int diaSemana) {
		if(diaSemana > 0 && diaSemana < 8)
			this.diaSemana = diaSemana;
	}

	public LocalTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalTime inicio) {
		this.inicio = inicio;
	}

	public LocalTime getFim() {
		return fim;
	}

	public void setFim(LocalTime fim) {
		this.fim = fim;
	}
	
	
}
