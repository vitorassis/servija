package servija.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import servija.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	boolean existsEstadoByNome(String nome);
	Estado getEstadoBySigla(String sigla);
}
