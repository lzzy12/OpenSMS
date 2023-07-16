package com.shivam.sms_sender;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class SmsSenderApplication {

    public static void main(String[] args) throws SecurityException, IOException {
            InputStream fileStream = new ClassPathResource("/json/firebase.json", SmsSenderApplication.class).getInputStream();
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(fileStream)).build();
            if (FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
            }
            SpringApplication.run(SmsSenderApplication.class, args);
    }

}
