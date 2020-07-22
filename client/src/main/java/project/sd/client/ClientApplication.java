package project.sd.client;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("project.sd.client")
public class ClientApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ClientApplication.class, args);
		Application.launch(JavaFxApplication.class, args);
	}

}
