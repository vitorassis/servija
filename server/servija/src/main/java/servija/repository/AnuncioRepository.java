package servija.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import servija.model.Anuncio;

public interface AnuncioRepository extends JpaRepository<Anuncio, Integer> {
	
}
