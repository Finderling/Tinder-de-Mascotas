package com.ejemplo1.RedSocialMascota1.repositorios;

import com.ejemplo1.RedSocialMascota1.entidades.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepositorio extends JpaRepository<Zona, String> {
    
}
