package com.example.bpi.service;

import java.util.List;

public interface BpiService {
  String getCurrentBpi(String curencyType);
  String getBpiHistory(String curencyType);
  List<String> findHighestAndLowestBpi(String currentBpi, List<String> bpiHistory);
}
