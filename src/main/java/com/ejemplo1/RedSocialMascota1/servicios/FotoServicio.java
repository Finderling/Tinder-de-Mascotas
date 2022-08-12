package com.ejemplo1.RedSocialMascota1.servicios;

import com.ejemplo1.RedSocialMascota1.entidades.Foto;
import com.ejemplo1.RedSocialMascota1.errores.ErrorServicio;
import com.ejemplo1.RedSocialMascota1.repositorios.FotoRepositorio;
import java.io.IOException;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    FotoRepositorio fotoRepositorio;
    
    @Transactional
    public Foto guardar(MultipartFile archivo) throws ErrorServicio {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }
    
    @Transactional
    public Foto actualizar(String idFoto,MultipartFile archivo) throws ErrorServicio {
         if (archivo != null) {
            try {
                Foto foto = new Foto();
                
                if (idFoto!= null) {
                    Optional<Foto> respuesta=fotoRepositorio.findById(idFoto);
                    if (respuesta.isPresent()) {
                        foto=respuesta.get();
                    }
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }
}
