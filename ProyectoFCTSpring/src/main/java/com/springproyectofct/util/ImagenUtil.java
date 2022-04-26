package com.springproyectofct.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class ImagenUtil {
	
	public ImagenUtil() {
		
	}

	public void guardarImagen(String ruta, MultipartFile archivo) throws IllegalStateException, IOException {

		// creo un nuevo archivo
		File file = new File(ruta);

		System.out.println(ruta);
		System.out.println(archivo.getOriginalFilename());
		
		
		// transfiero el archivo multipart al disco.
		archivo.transferTo(file);

	}
	
	
	public static void mostrarImagen(String ruta) throws IllegalStateException, IOException {

		// creo un nuevo archivo
		File file = new File(ruta);

		MultipartFile archivo;
		
		
		// transfiero el archivo multipart al disco.
//		archivo.transferTo(file);

	}

}
