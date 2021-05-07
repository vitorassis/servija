package servija.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import servija.model.TipoServico;

public interface TipoServicoRepository extends JpaRepository<TipoServico, Integer> {

	List<TipoServico> getAllByNomeContains(String nome);

}
