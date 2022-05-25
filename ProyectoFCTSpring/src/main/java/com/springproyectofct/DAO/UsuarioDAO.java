package com.springproyectofct.DAO;

import java.util.List;

import com.springproyectofct.modelo.Usuario;


public interface UsuarioDAO {

	List<Usuario> findAll();

	Usuario findOne(int id);

	boolean create(Usuario usuario);

	boolean update(Usuario usuario);

	boolean delete(Usuario usuario);

}
