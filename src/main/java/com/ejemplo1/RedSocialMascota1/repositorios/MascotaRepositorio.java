package com.ejemplo1.RedSocialMascota1.repositorios;

import com.ejemplo1.RedSocialMascota1.entidades.Mascota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota,String> {
    
    @Query("SELECT c FROM Mascota c WHERE c.usuario.id = :id AND c.baja IS NULL")
    public List<Mascota> buscarMascotaPorUsuario(@Param("id")String id);
    
    @Query("SELECT c FROM Mascota c WHERE c.usuario.id != :id AND c.baja IS NULL")
    public List<Mascota> buscarMascotaParaMostrar(@Param("id")String id);
}


