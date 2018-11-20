package demo;

import javax.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JsonDbStreamingDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(JsonDbStreamingDemoApplication.class, args);
  }

  @Bean
  public CommandLineRunner carInit(PersonRepository heroRepository) {
    return new CommandLineRunner() {
      @Override
      @Transactional
      public void run(String... args) throws Exception {
        for (int i = 0; i < 10_000; i++) {
          heroRepository.save(new Person("Person " + i, i));
        }
      }
    };
  }

}
