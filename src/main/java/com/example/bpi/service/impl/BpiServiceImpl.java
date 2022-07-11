package com.example.bpi.service.impl;

import com.example.bpi.service.BpiService;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;

@Service
public class BpiServiceImpl implements BpiService {

  @Autowired
  RestTemplate restTemplate;

  @Override
  public String getCurrentBpi(String curencyType) {
    String url = String.format("https://api.coindesk.com/v1/bpi/currentprice/%s.json",curencyType);
    ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
    if(response == null)
      throw new NullPointerException("Not Found");
    return response.getBody();
  }

  @Override
  public String getBpiHistory(String curencyType) {
    LocalDate currentDate = LocalDate.now();
    LocalDate previousDate = currentDate.minusDays(30);
    String url = String.format("https://api.coindesk.com/v1/bpi/historical/close.json?start=%s&end=%s&currency=%s", previousDate.toString(), currentDate.toString(), curencyType);
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    return response.getBody();
  }

  @Override
  public List<String> findHighestAndLowestBpi(String currentBpi, List<String> bpiHistory) {
    return null;
  }
}
