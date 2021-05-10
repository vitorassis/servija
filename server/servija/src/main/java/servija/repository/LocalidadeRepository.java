package servija.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import servija.model.Localidade;

public interface LocalidadeRepository extends JpaRepository<Localidade, Integer> {
	@Query(nativeQuery = true, value=
		"SELECT * FROM localidades "
		+ "inner JOIN cidades ON cidadeprinc_id = cidades.id "
		+ "INNER JOIN estados on estados.id = estado_id "
		+ "ORDER by sigla;"
	)
	List<Localidade> findAllOrderByEstado();
}