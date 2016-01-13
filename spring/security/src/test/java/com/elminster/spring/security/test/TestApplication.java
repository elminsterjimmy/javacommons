package com.elminster.spring.security.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan({"com.elminster.spring.security"})
@EnableJpaRepositories({"com.elminster.spring.security"})
@EntityScan({"com.elminster.spring.security"})
@EnableTransactionManagement
public class TestApplication {

  /**
   * The main.
   * 
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class, args);
  }
}
