package com.thrift.thriftsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
@SpringBootApplication
public class ThriftSystemApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                        .ignoreIfMissing()
                        .systemProperties()
                                .load();
        SpringApplication.run(ThriftSystemApplication.class, args);
    }

}
