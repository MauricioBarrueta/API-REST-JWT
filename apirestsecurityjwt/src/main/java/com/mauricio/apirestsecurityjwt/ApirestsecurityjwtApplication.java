package com.mauricio.apirestsecurityjwt;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApirestsecurityjwtApplication {

	//* Se crea el método en la clase principal del proyecto para poder utilizar la dependencia ModelMapper
	@Bean
	/** Beans:
	 *! Los Beans son objetos que maneja el contendor de spring. Pues es un patrón de diseño orientado a objetos, 
	 *! su fin es el de suministrar objetos a una clase sin que la propia clase tenga que crearlos. */
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApirestsecurityjwtApplication.class, args);
	}

}
