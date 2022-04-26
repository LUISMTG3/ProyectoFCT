package com.springproyectofct.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Jpautil {

	public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProyectoFCTSpring");

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

}
