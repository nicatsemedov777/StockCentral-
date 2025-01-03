package az.project.business_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BusinessManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessManagementApplication.class, args);
	}

}
