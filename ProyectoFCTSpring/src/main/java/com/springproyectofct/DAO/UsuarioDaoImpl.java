package com.springproyectofct.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.springproyectofct.Email.EnviarEmail;
import com.springproyectofct.modelo.Usuario;
import com.springproyectofct.util.Jpautil;

@Repository
public class UsuarioDaoImpl implements UsuarioDAO {

	EnviarEmail email = new EnviarEmail();
	
	@Override
	public List<Usuario> findAll()  {

		try {
			
		
		List<Usuario> usuarios = new ArrayList<>();

		EntityManager em = Jpautil.getEntityManager();

//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoFCTSpring");

//		EntityManager em = emf.createEntityManager();

		usuarios.addAll(em.createQuery("select c from Usuario c", Usuario.class).getResultList());

		em.close();

		return usuarios;
		
		}catch( IndexOutOfBoundsException ex) {
			
			return new ArrayList<>(); 
		}
	}

	@Override
	public Usuario findOne(int id) {

		Usuario usuario = new Usuario();

		EntityManager em = Jpautil.getEntityManager();

//		usuario = (Usuario) em.createQuery("select c from Usuario c where id = ?", Usuario.class);

		usuario = em.find(Usuario.class, id);

		em.close();

		return usuario;
	}

	@Override
	public boolean create(Usuario usuario) {

		try {

			EntityManager em = Jpautil.getEntityManager();

			em.getTransaction().begin();

			em.persist(usuario);
			
			email.enviarConGMail(
					usuario.getEmail(),
					"Bienvenido a MiRedSocial.com",
					"\nBienvenido a MiRedSocial.com. Usuario:" + usuario.getNombre() + "\n Contraseña: " + usuario.getPassword());
			
			em.getTransaction().commit();

			em.close();

			return true;

		} catch (Exception ex) {
			System.out.println("No se ha podido crear la entidad : " + ex.getMessage());
			System.out.println(ex.getLocalizedMessage());
			return false;
		}

	}

	@Override
	public boolean update(Usuario usuario) {
		
		
		EntityManager em = Jpautil.getEntityManager();
		
		em.getTransaction().begin();
		
		em.merge(usuario);
		
		em.getTransaction().commit();
		
		em.close();
		
		return true;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
