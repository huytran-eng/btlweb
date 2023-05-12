package D20PTIT.hoidap;

import org.springframework.core.SpringVersion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HoidapApplication {
    public static void main(String[] args) {
        System.out.println("version: " + SpringVersion.getVersion());
        SpringApplication.run(HoidapApplication.class, args);
    }
}
