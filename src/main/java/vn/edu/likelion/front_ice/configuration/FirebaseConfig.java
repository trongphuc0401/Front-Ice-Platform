package vn.edu.likelion.front_ice.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * FirebaseConfig -
 *
 * @param
 * @return
 * @throws
 */
@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void init() throws IOException {

        String firebaseConfig = System.getenv("FIREBASE_CREDENTIALS");
        if (firebaseConfig == null) {
            throw new IllegalStateException("Environment variable FIREBASE_CREDENTIALS not set");
        }

        InputStream serviceAccount = new ByteArrayInputStream(firebaseConfig.getBytes(StandardCharsets.UTF_8));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("front-15551.appspot.com") // Thay YOUR_PROJECT_ID bằng ID của Firebase Project.
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

}
