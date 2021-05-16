package servija.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import servija.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	List<Categoria> findAllByNomeContains(String nome);
}
