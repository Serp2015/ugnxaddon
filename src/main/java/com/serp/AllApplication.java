package com.serp;

import com.serp.createassemble.CreateAssemble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AllApplication implements CommandLineRunner {

    //	@Autowired
//	private OpenPartsPdf openPartsPdf;
//    @Autowired
//    private OpenPartsTiff openPartsTiff;
//    @Autowired
//    private AddStandardPartRoot addStandardPartRoot;
//    @Autowired
//    private AddReferenceSets addReferenceSets;
    @Autowired
    private CreateAssemble createAssemble;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AllApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
//		openPartsPdf.execute();
//		openPartsTiff.execute();
//      addStandardPartRoot.addAttributes();
//      addReferenceSets.addReference();
        createAssemble.create();
    }
}