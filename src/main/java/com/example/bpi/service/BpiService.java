package com.example.bpi.service;

import java.text.ParseException;

/**
 * BpiService Interface contains abstract methods uses for generate max and min bitcoin rates
 * Entry point
 * @version 1.0.0
 * @author Chamil Ananda
 * @Date 7/12/2022
 */
public interface BpiService {

  /**
   * ...Get current BPI jSon string...
   * @param curencyType accept shortened currency type
   */
  String getCurrentBpi(String curencyType);
  /**
   * ...Get current BPI jSon string...
   * @param curencyType accept shortened currency type
   */
  String getBpiHistory(String curencyType);
  /**
   * ...Get current BPI jSon string...
   * @param curencyType accept shortened currency type
   */
  String findHighestAndLowestBpi(String curencyType) throws Exception;
}
