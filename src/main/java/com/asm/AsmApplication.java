package com.asm;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsmApplication.class, args);
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dyq6z1a91",
                "api_key", "299125189913532",
                "api_secret", "WZiZozEYRtrAg9Ew0mk7sIJww3Q",
                "secure",true
        ));
        return cloudinary;
    }
}
