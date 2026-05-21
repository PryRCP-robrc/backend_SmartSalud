package com.policlinico.smartsalud;

import org.springframework.boot.SpringApplication;

public class TestSmartsaludApplication {

	public static void main(String[] args) {
		SpringApplication.from(SmartsaludApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
