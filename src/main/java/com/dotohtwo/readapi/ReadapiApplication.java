package com.dotohtwo.readapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReadapiApplication {
	// @Autowired
    // private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ReadapiApplication.class, args);
	}
 
    // @Override
    // public void run(String... args) throws Exception {
    //     String sql = "INSERT INTO students (name, email) VALUES ("
    //             + "'Nam Ha Minh', 'nam@codejava.net')";
         
    //     int rows = jdbcTemplate.update(sql);
    //     if (rows > 0) {
    //         System.out.println("A new row has been inserted.");
    //     }
    // }
}
