package com.example.springboot.app.utils.paginator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;

import com.example.springboot.app.utils.Constants;

public class PageRender<T> {
  
  private String url;
  private Page<T> page;
  private int totalPages;
  private int resultsPerPage;
  private int currentPage;
  private List<PageItem> pages;
  
  public PageRender(String url, Page<T> page) {
    this.url = url;
    this.page = page;
    this.resultsPerPage = page.getSize();
    this.totalPages = page.getTotalPages();
    // The first page passed to the Controller will be "0" so we have to add 1 to show the first page like 1 in the View
    this.currentPage = page.getNumber() + 1;
    this.pages = new ArrayList<PageItem>();
    
    int from, to;
    if(this.totalPages <= this.resultsPerPage) {
      // The first page passed to the Controller will be "0" so we have to add 1 to represent the first page like 1
      from = Integer.valueOf(Constants.FIRST_PAGE) + 1;
      to = this.totalPages;
    }
    else if(this.currentPage <= (this.resultsPerPage / 2)) {
      from = Integer.valueOf(Constants.FIRST_PAGE) + 1;
      to = this.resultsPerPage;
    }
    else if(this.currentPage >= (this.totalPages - this.resultsPerPage)) {
      from = (this.totalPages - this.resultsPerPage) + 1;
      to = this.resultsPerPage;
    }
    else {
      from = this.currentPage - (this.resultsPerPage / 2);
      to = this.resultsPerPage;
    }
    
    // Pages to be showed inside the View
    IntStream.range(0, to).forEach(i -> {
      pages.add(new PageItem((from + i), (this.currentPage == (from + i))));
    });
    
  }

  public String getUrl() {
    return url;
  }

  public Page<T> getPage() {
    return page;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public List<PageItem> getPages() {
    return pages;
  }
  
  public boolean isFirst() {
    return this.page.isFirst();
  }
  
  public boolean isLast() {
    return this.page.isLast();
  }
  
  public boolean isHasNext() {
    return this.page.hasNext();
  }
  
  public boolean isHasPrevious() {
    return this.page.hasPrevious();
  }
}
