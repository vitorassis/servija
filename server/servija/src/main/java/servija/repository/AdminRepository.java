package servija.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import servija.model.Administrador;

public interface AdminRepository extends JpaRepository<Administrador, Integer>{
}
