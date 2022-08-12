package com.ejemplo1.RedSocialMascota1.servicios;

import com.ejemplo1.RedSocialMascota1.entidades.Foto;
import com.ejemplo1.RedSocialMascota1.entidades.Mascota;
import com.ejemplo1.RedSocialMascota1.entidades.Usuario;
import com.ejemplo1.RedSocialMascota1.enumeraciones.Sexo;
import com.ejemplo1.RedSocialMascota1.enumeraciones.Tipo;
import com.ejemplo1.RedSocialMascota1.errores.ErrorServicio;
import com.ejemplo1.RedSocialMascota1.repositorios.MascotaRepositorio;
import com.ejemplo1.RedSocialMascota1.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MascotaServicio  {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional
    public void agregarMascota(MultipartFile archivo, String idUsuario, String nombre, Sexo sexo,Tipo tipo) throws ErrorServicio {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();

        validar(nombre, sexo);

        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setSexo(sexo);
        mascota.setAlta(new Date());
        mascota.setUsuario(usuario);
        mascota.setTipo(tipo);
       
        Foto foto = fotoServicio.guardar(archivo);
        mascota.setFoto(foto);
        mascotaRepositorio.save(mascota);
    }

    @Transactional
    public void modificar(MultipartFile archivo, String idUsuario, String idMascota, String nombre, Sexo sexo,Tipo tipo) throws ErrorServicio {
        validar(nombre, sexo);

        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getUsuario().getId().equals(idUsuario)) {
                mascota.setNombre(nombre);
                mascota.setSexo(sexo);
                mascota.setTipo(tipo);

                String idFoto = null;
                if (mascota.getFoto() != null) {
                    idFoto = mascota.getFoto().getId();
                }
                Foto foto = fotoServicio.actualizar(idFoto, archivo);
                mascota.setFoto(foto);
                mascotaRepositorio.save(mascota);
            } else {
                throw new ErrorServicio("No tiene permiso suficiente para realizar la modificacion");
            }
        } else {
            throw new ErrorServicio("No existe la mascota que quiere modificar");

        }

    }

    @Transactional
    public void eliminar(String idUsuario, String idMascota) throws ErrorServicio {
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getUsuario().getId().equals(idUsuario)) {
                mascota.setBaja(new Date());
                mascotaRepositorio.save(mascota);
            } else {
                throw new ErrorServicio("No tiene permiso suficiente para realizar la modificacion");
            }
        } else {
            throw new ErrorServicio("No existe la mascota que quiere modificar");

        }
    }

    private void validar(String nombre, Sexo sexo) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la mascota es nulo");
        }

        if (sexo == null) {
            throw new ErrorServicio("El sexo de la mascota no puede ser nulo");

        }
    }
    
    @Transactional
    public Mascota buscarPorId(String id) throws ErrorServicio {
        Optional<Mascota> respuesta = mascotaRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ErrorServicio("No se encontro la mascota");
        }

    }
    
    public List<Mascota> buscarMascotaPorUsuario(String id){
        return mascotaRepositorio.buscarMascotaPorUsuario(id);
    }
    
    public List<Mascota> buscarMascotaParaMostrar(String id){
        return mascotaRepositorio.buscarMascotaParaMostrar(id);
    }


    
}
