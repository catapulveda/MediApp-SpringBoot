package com.orionsoftware.service.impl;

import com.orionsoftware.model.Usuario;
import com.orionsoftware.repo.IUsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IUsuarioServiceImpl implements UserDetailsService {

    @Autowired
    IUsuarioRepo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findOneByUsername(username);
        if (usuario == null){
            throw new UsernameNotFoundException(String.format("Usuario no existe", username));
        }

        /*CON LOS ROLES Y USUARIOS DE MI ESTRUCTURA VOY A POBLAR LA ESTRUCTURA DE ROLES Y USUARIOS
        QUE POR DEFECTO SPRING RECOMIENDA*/

        List<GrantedAuthority> roles = new ArrayList<>();
        usuario.getRoles().forEach(rol -> {
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        });

        /*
        Tiene que ser asi, poblando la estructura de spring security, con la data que nosotros tenemos.
        * */
        UserDetails userDetails = new User(usuario.getUsername(), usuario.getPassword(), roles);
        return userDetails;
    }
}
