package com.serp;

import com.serp.printTiff.OpenPartsTiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrintTiffApplication implements CommandLineRunner {

	@Autowired
	private OpenPartsTiff openParts;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PrintTiffApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		openParts.execute();
	}
}