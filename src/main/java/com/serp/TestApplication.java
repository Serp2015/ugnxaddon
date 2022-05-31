/*
package com.serp;

import com.serp.message.UGConsolePrint;
import com.serp.printpdf.OpenPartsPdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication implements CommandLineRunner {

	@Autowired
	private UGConsolePrint consolePrint;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TestApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	@Override
	public void run(String... args) {
		consolePrint.println("Test");
	}
}
*/
