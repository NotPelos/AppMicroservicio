package com.example.microservicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.starter.CocheService;
import com.starter.models.CocheBuilder;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer


//Mediante las prácticas ya hechas he pensado algo simple y es un microservicio sobre una venta de vehículos. Algo simple donde puedo mostrar al menos lo aprendido.

public class AppSvApplication implements CommandLineRunner{
	
	@Autowired
	CocheBuilder cb;
	
	@Autowired
	CocheService sv;

	public static void main(String[] args) {
		SpringApplication.run(AppSvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Para simplificar código (no sé si se podría hacer) se me ha ocurrido que se podría implementar una tabla de datos mediante JSON, le pasas un arraylist con un
		//for que recorra cada parte del JSON, así para añadir nuevos coches solo tendríamos que tocar el ficher json y el código quedaría mas limpio.
		
		sv.cochebd.add(cb.id(1L).matricula("1234ABC").compania("Seat").marca("Ibiza").ano("25/05/1999").precio(3500.00).build());
		sv.cochebd.add(cb.id(2L).matricula("5678DEF").compania("Renault").marca("Laguna").ano("05/11/2009").precio(4250.00).build());
		sv.cochebd.add(cb.id(3L).matricula("9876GHI").compania("Ford").marca("Mustang").ano("25/05/2020").precio(65000.00).build());
		sv.cochebd.add(cb.id(4L).matricula("5432JKL").compania("Opel").marca("Astra").ano("22/10/2009").precio(4000.00).build());
		sv.cochebd.add(cb.id(5L).matricula("9090MNO").compania("Opel").marca("Corsa").ano("28/04/2021").precio(12500.00).build());

	}

}
