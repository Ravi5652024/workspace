//This is starting points of application

package io.springbootTransactionstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



//Step1 To Tell the spring this is the spring application using annotation
@SpringBootApplication
public class TransactionApiApp {

	public static void main(String[] args) {
		
		SpringApplication.run(TransactionApiApp.class, args);
		

	}

}
