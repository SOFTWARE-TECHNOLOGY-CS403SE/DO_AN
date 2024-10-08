package org.example.advancedrealestate_be;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Advanced Real Estate API", version = "2.0.2", description = "Advanced Real Estate API"))
public class AdvancedRealEstateBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedRealEstateBeApplication.class, args);
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            keyGen.init(256);
//            SecretKey secretKey = keyGen.generateKey();
//            System.out.println("Generated Secret Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
        System.out.println("running: http://localhost:8080/");
    }

}
