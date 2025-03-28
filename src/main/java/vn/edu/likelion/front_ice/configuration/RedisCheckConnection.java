package vn.edu.likelion.front_ice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedisCheckConnection {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @PostConstruct
    public void checkConnection() {
        try {
            redisConnectionFactory.getConnection().ping();

            System.out.println("Redis is connected!");
        } catch (Exception e) {
            System.err.println("Cannot connect to Redis: " + e.getMessage());
        }
    }
}
