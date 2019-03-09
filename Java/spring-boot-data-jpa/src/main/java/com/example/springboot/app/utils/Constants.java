package com.example.springboot.app.utils;

public interface Constants {

  /***** Global *****/
  public static final String BLANK = " ";
  public static final String ANY_FILE = "**";
  
  /***** Controllers *****/
  public static final String ATTRIBUTE_TITLE_KEY = "title";
  public static final String ATTRIBUTE_TITLE_VALUE_LIST_CLIENTS = "List of Clients";
  public static final String ATTRIBUTE_TITLE_VALUE_NEW_CLIENT = "New Client";
  public static final Object ATTRIBUTE_TITLE_VALUE_EDIT_CLIENT = "Edit Client";
  public static final Object ATTRIBUTE_TITLE_VALUE_CLIENT_DETAILS = "Client Details: ";
  public static final String ATTRIBUTE_CLIENTS_KEY = "clients";
  public static final String ATTRIBUTE_CLIENT_KEY = "client";
  public static final String ATTRIBUTE_FLASH_SUCCESS_KEY = "success";
  public static final String ATTRIBUTE_FLASH_ERROR_KEY = "error";
  public static final String ATTRIBUTE_FLASH_WARNING_KEY = "warning";
  public static final String ATTRIBUTE_FLASH_INFO_KEY = "info";
  public static final String VIEW_LIST = "list";  
  public static final String VIEW_CREATE = "create"; 
  public static final String VIEW_DETAILS = "details"; 
  public static final String FIRST_PAGE = "0";
  public static final int RESULTS_PER_PAGE = 4;
  public static final String ATTRIBUTE_PAGE_RENDER_KEY = "page";
  public static final String STATIC_RESOURCES_DIRECTORY_PATH = "src//main//resources//static";
  public static final String UPLOADS_DIRECTORY = "uploads";
  public static final String DEFAULT_DIRECTORY = "default";
  public static final String UPLOADS_IMAGES_DIRECTORY = "images";
  public static final String EXTERNAL_DIRECTORY_PATH = "C://MY-PROJECTS//SpringFramework//Java";
  public static final String EXTERNAL_UPLOADS_DIRECTORY = "//uploads";
  public static final String EXTERNAL_UPLOADS_IMAGES_DIRECTORY = "//images";
  public static final String FILENAME_IMAGE_NOT_FOUND = "image-not-found.png";


  
  /***** Daos *****/
  // Queries words
  public static final String FROM = "FROM";
  
  // Entity
  public static final String ENTITY_CLIENT = "Client";
  
  // Table 'Clients'
  public static final String TABLE_CLIENTS = "Clients";

}
