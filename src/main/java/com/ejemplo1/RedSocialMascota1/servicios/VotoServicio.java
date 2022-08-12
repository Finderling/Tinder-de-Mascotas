package com.ejemplo1.RedSocialMascota1.servicios;

import com.ejemplo1.RedSocialMascota1.entidades.Mascota;
import com.ejemplo1.RedSocialMascota1.entidades.Voto;
import com.ejemplo1.RedSocialMascota1.errores.ErrorServicio;
import com.ejemplo1.RedSocialMascota1.repositorios.MascotaRepositorio;
import com.ejemplo1.RedSocialMascota1.repositorios.VotoRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoServicio {

    @Autowired
    private VotoRepositorio votoRepositorio;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private NotificacionServicio notificacionServicio;

    public void votar(String idUsuario, String idMascota1, String idMascota2) throws ErrorServicio {
        if (idMascota1.equals(idMascota2)) {
            throw new ErrorServicio("No puede realizarse autovoto");
        }

        Voto voto = new Voto();
        voto.setFecha(new Date());

        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota1);
        if (respuesta.isPresent()) {
            Mascota mascota1 = respuesta.get();
            if (mascota1.getUsuario().getId().equals(idUsuario)) {
                voto.setMascota1(mascota1);

            } else {
                throw new ErrorServicio("No tiene permiso para realizar la operacion solicitada");
            }
        } else {
            throw new ErrorServicio("no existe la mascota vinculada a ese identificador");
        }

        Optional<Mascota> respuesta2 = mascotaRepositorio.findById(idMascota2);
        if (respuesta2.isPresent()) {
            Mascota mascota2 = respuesta.get();
            voto.setMascota2(mascota2);

            notificacionServicio.enviar("Tu mascota ha sido votada", "Red social de Mascota", mascota2.getUsuario().getMail());

        } else {
            throw new ErrorServicio("no existe la mascota vinculada a ese identificador");
        }

        votoRepositorio.save(voto);

    }

    public void respuesta(String idUsuario, String idVoto) throws ErrorServicio {
        Optional<Voto> respuesta = votoRepositorio.findById(idVoto);
        if (respuesta.isPresent()) {
            Voto voto = respuesta.get();
            voto.setRespuesta(new Date());
            if (voto.getMascota2().getUsuario().getId().equals(idUsuario)) {
                votoRepositorio.save(voto);

                notificacionServicio.enviar("Tu voto fue correaspondido", "Red social de Mascota", voto.getMascota1().getUsuario().getMail());

            } else {
                throw new ErrorServicio("No tiene permiso para realizar esta operacion");
            }
        } else {
            throw new ErrorServicio("no existe el voto solicitado");

        }
    }

}
