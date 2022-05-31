/*
package com.serp;

import com.serp.preparenewfile.AddReferenceSets;
import com.serp.preparenewfile.AddStandardPartRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Primary;

import javax.annotation.Priority;

@SpringBootApplication
public class AddAttributeApplication implements CommandLineRunner {

    @Autowired
    private AddStandardPartRoot addStandardPartRoot;
    @Autowired
    private AddReferenceSets addReferenceSets;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AddAttributeApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        addStandardPartRoot.addAttributes();
        addReferenceSets.addReference();
    }
}
*/
