package servija.model;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "anuncios")
public class Anuncio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "anunciante_id", referencedColumnName = "usuario_id")
	@JsonBackReference
	private Anunciante anunciante;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "localidade_id", referencedColumnName = "id")
	private Localidade localidade;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "tpservico_id", referencedColumnName = "id")
	private TipoServico tipoServico;
	
	private String descricao;
	
	private String descrLonga;
	
	private double valor;
	
	private LocalTime tempo;
	
	@OneToMany(mappedBy = "anuncio")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonManagedReference
	private Set<Disponibilidade> disponibilidades;
	
	@OneToMany(mappedBy="anuncio")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonManagedReference
	private Set<Imagem> imagens;

	public Anuncio(int id, Anunciante anunciante, Localidade localidade, TipoServico tipoServico, String descricao,
			String descrLonga, double valor, LocalTime tempo) {
		this();
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
		this.disponibilidades = new HashSet<Disponibilidade>();
		this.imagens = new HashSet<Imagem>();
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

	public LocalTime getTempo() {
		return tempo;
	}

	public void setTempo(LocalTime tempo) {
		this.tempo = tempo;
	}

	public void addImagem(Imagem imagem) {
		this.imagens.add(imagem);		
	}

	public void addDisponibilidade(Disponibilidade disponibilidade) {
		this.disponibilidades.add(disponibilidade);		
	}

	public void setTempo(String tempo) {
		this.tempo = LocalTime.of(Integer.parseInt(tempo.split(":")[0]), 
				Integer.parseInt(tempo.split(":")[1]));
		
	}

	public Set<Disponibilidade> getDisponibilidades() {
		// TODO Auto-generated method stub
		return this.disponibilidades;
	}

	public Set<Imagem> getImagens() {
		// TODO Auto-generated method stub
		return this.imagens;
	}
}
