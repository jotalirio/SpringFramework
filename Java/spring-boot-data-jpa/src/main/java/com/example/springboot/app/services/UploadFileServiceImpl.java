package com.example.springboot.app.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.app.utils.Constants;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

  // We are going to use a directory (uploads/images) located inside the static directory
  // private static final String UPLOADS_DIRECTORY_FULL_PATH = Constants.STATIC_RESOURCES_DIRECTORY_PATH + "/" + Constants.UPLOADS_DIRECTORY + "/" + Constants.UPLOADS_IMAGES_DIRECTORY;
  
  // We are going to use an external directory to store the Client´s profile photo
  // private static final String EXTERNAL_UPLOADS_DIRECTORY_FULL_PATH = Constants.EXTERNAL_DIRECTORY_PATH + Constants.EXTERNAL_UPLOADS_DIRECTORY + Constants.EXTERNAL_UPLOADS_IMAGES_DIRECTORY;

  // We are going to use a directory (uploads/images) located inside the root project
  private static final String UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH = Constants.UPLOADS_DIRECTORY + "/" + Constants.UPLOADS_IMAGES_DIRECTORY;
  private static final String DEFAULT_IMAGES_DIRECTORY_PROJECT_PATH = Constants.DEFAULT_DIRECTORY + "/" + Constants.UPLOADS_IMAGES_DIRECTORY;

  private final Logger LOGGER = LoggerFactory.getLogger(UploadFileServiceImpl.class);

  @Override
  public Resource loadImage(String fileName) {
    Path pathPhoto = this.getPath(fileName);
    LOGGER.info("pathPhoto: " + pathPhoto);
    Resource resource = null;
    try {
      
      resource = new UrlResource(pathPhoto.toUri());
      if(!resource.exists() || !resource.isReadable()) {
        LOGGER.error("ERROR: There was a problem fetching the client´s photo. The image '" + pathPhoto.toString() + "' does not exist.");
      }
    } 
    catch (MalformedURLException ex) {
      LOGGER.error(ex.getMessage(), ex);
    }
    return resource;
  }

  @Override
  public String createImage(MultipartFile image) {
    // We are going to use a directory (uploads/images) located inside the static directory
    // Path resourcesDirectory = Paths.get(UPLOADS_DIRECTORY_FULL_PATH);
    // String rootPath = resourcesDirectory.toFile().getAbsolutePath();
    
    // We are going to use an external directory to store the Client´s profile photo
    // String rootPath = EXTERNAL_UPLOADS_DIRECTORY_FULL_PATH;
    
    // We are going to use a directory (uploads/images) located inside the root project
    String uniqueFilename =  UUID.randomUUID().toString() + "_" + image.getOriginalFilename();  
    // Path relative to the project: spring-boot-data-jpa/uploads/images: uploads\images\eab67c1a-6efd-4682-91be-b1f4009df795_Youtube.png
    Path rootPath = Paths.get(UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH).resolve(uniqueFilename);
    LOGGER.info("rootPath: " + rootPath);
    // The absolute path to the images directory: C:\MY-PROJECTS\SpringFramework\Java\spring-boot-data-jpa\ouploads\images\eab67c1a-6efd-4682-91be-b1f4009df795_Youtube.png
    Path rootAbsolutePath = rootPath.toAbsolutePath();
    LOGGER.info("rootAbsolutePath: " + rootAbsolutePath);
    try {

      // byte[] bytes = photo.getBytes();
      // Path pathToPhoto = Paths.get(rootPath + "//" + photo.getOriginalFilename());
      // Files.write(pathToPhoto, bytes);
      
      // Files.copy is equivalent to the 3 lines before
      Files.copy(image.getInputStream(), rootAbsolutePath);
      
    } catch (IOException ex) {
      LOGGER.error(ex.getMessage(), ex);
      uniqueFilename = null;
    }
    return uniqueFilename;
  }

  @Override
  public boolean deleteImage(String fileName) {
    boolean isDeleted = false;
    if(fileName != null && !fileName.isEmpty()) {
      Path pathPhoto = this.getPath(fileName);
      File photo = pathPhoto.toFile();
      if(photo.exists() && photo.canRead()) {
        isDeleted = photo.delete();
      }
    }
    return isDeleted;
  }

  /**
   * Delete the 'uploads/images' directory
   */
  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH).toFile());
  }
  
  /**
   * Creates the 'uploads/images' directory
   * @throws IOException
   */
  @Override
  public void init() throws IOException {
    try {
      Files.createDirectories(Paths.get(UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH));
    } catch (IOException ex) {
      LOGGER.error("ERROR: There was a problem creating the '" + UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH + "' directory.");
      throw ex;
    }
  }

  public Resource getImageNotFound() {
    Path pathImageNotFound = Paths.get(DEFAULT_IMAGES_DIRECTORY_PROJECT_PATH).resolve(Constants.FILENAME_IMAGE_NOT_FOUND).toAbsolutePath();
    Resource resource = null;
    try {
      resource = new UrlResource(pathImageNotFound.toUri());
    } catch (MalformedURLException ex) {
      LOGGER.error(ex.getMessage(), ex);
    }
    return resource;
  }
  
  private Path getPath(String fileName) {
    return Paths.get(UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH).resolve(fileName).toAbsolutePath();
  }
}
