package Shingu.JobCompletion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class JobCompletionApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobCompletionApplication.class, args);
	}

}
