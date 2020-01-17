package com.orionsoftware.service;

import com.orionsoftware.model.Menu;

import java.util.List;

public interface IMenuService extends ICRUD<Menu>{

    List<Menu> listarMenuPorUsuario(String nombre);
}
