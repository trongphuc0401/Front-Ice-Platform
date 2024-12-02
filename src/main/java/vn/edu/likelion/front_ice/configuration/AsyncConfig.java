package vn.edu.likelion.front_ice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * AsyncConfig -
 *
 * @param
 * @return
 * @throws
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Số thread chính
        executor.setMaxPoolSize(10); // Số thread tối đa
        executor.setQueueCapacity(25); // Số tác vụ có thể đợi
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
}
