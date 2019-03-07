package com.example.springboot.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.FileSystemUtils;

import com.example.springboot.app.utils.Constants;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {

  // We are going to use a directory (uploads/images) located inside the root project
  private static final String UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH = Constants.UPLOADS_DIRECTORY + "/" + Constants.UPLOADS_IMAGES_DIRECTORY;

  private final Logger LOGGER = LoggerFactory.getLogger(SpringBootDataJpaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	/**
	 * When the project starts, the 'uploads/images' directory is automatically deleted and created in order to clear the images
	 */
  @Override
  public void run(String... args) throws Exception {
    this.deleteAll();
    this.init();
  }

  /**
   * Delete the 'uploads/images' directory
   */
  private void deleteAll() {
    FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH).toFile());
  }
  
  /**
   * Creates the 'uploads/images' directory
   * @throws IOException
   */
  private void init() throws IOException {
    try {
      Files.createDirectories(Paths.get(UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH));
    } catch (IOException ex) {
      LOGGER.error("ERROR: There was a problem creating the '" + UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH + "' directory.");
      throw ex;
    }
  }
}
