package servija.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import servija.model.Cidade;
import servija.model.Estado;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
	boolean existsCidadeByNomeAndEstado(String nome, Estado estado);
	List<Cidade> getCidadeByEstado(Estado estado);
}
