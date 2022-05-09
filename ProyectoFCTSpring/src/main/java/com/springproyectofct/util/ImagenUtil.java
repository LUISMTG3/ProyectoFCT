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
		
		
		// transfiero el archivo multipart al disco.
		archivo.transferTo(file);

	}
	


}
