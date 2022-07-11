package com.example.bpi.service.impl;

import com.example.bpi.service.BpiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BpiServiceImpl implements BpiService {

  @Autowired
  RestTemplate restTemplate;

  @Override
  public String getCurrentBpi(String curencyType) {
    String url = String.format("https://api.coindesk.com/v1/bpi/currentprice/%s.json",curencyType);
    ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
    return response.getBody();
  }

  @Override
  public String getBpiHistory(String curencyType) {
    return null;
  }

  @Override
  public List<String> findHighestAndLowestBpi(String currentBpi, List<String> bpiHistory) {
    return null;
  }
}
