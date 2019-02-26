package com.example.springboot.app.utils;

public interface Constants {

  /***** Global *****/
  public static final String BLANK = " ";
  
  /***** Controllers *****/
  public static final String ATTRIBUTE_TITLE_KEY = "title";
  public static final String ATTRIBUTE_TITLE_VALUE_LIST_CLIENTS = "List of Clients";
  public static final String ATTRIBUTE_TITLE_VALUE_NEW_CLIENT = "New Client";
  public static final Object ATTRIBUTE_TITLE_VALUE_EDIT_CLIENT = "Edit Client";
  public static final String ATTRIBUTE_CLIENTS_KEY = "clients";
  public static final String ATTRIBUTE_CLIENT_KEY = "client";
  public static final String ATTRIBUTE_FLASH_SUCCESS_KEY = "success";
  public static final String ATTRIBUTE_FLASH_ERROR_KEY = "error";
  public static final String ATTRIBUTE_FLASH_WARNING_KEY = "warning";
  public static final String ATTRIBUTE_FLASH_INFO_KEY = "info";
  public static final String VIEW_LIST = "list";  
  public static final String VIEW_CREATE = "create"; 
  public static final String FIRST_PAGE = "0";
  public static final int RESULTS_PER_PAGE = 4;
  public static final String ATTRIBUTE_PAGE_RENDER_KEY = "page";
  
  /***** Daos *****/
  // Queries words
  public static final String FROM = "FROM";
  
  // Entity
  public static final String ENTITY_CLIENT = "Client";
  
  // Table 'Clients'
  public static final String TABLE_CLIENTS = "Clients";

}
