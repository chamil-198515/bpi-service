package com.example.bpi.controller;

import com.example.bpi.service.impl.BpiServiceImpl;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller contains apis to access BPI rate services
 *
 * @version 1.0.0
 * @author Chamil Ananda
 * @Date 7/12/2022
 */
@RestController
public class TestController {

  /**
   * BpiServiceImpl instance variable
   */
  @Autowired
  BpiServiceImpl bpiService;

  /**
   * ...API method to test currency rate implementation...
   *
   * @param curency accept curency type as String
   */
  @GetMapping(value = "/bpi_rates/{curency}")
  public ResponseEntity<String> getBpiRatesByCurency(@PathVariable String curency) {
    String bpiRates = null;
    try {
      bpiRates = bpiService.findHighestAndLowestBpi(curency);
    } catch (NullPointerException e) {
      return new ResponseEntity<>("No result found for given curency type", HttpStatus.NOT_FOUND);
    } catch (ParseException e) {
      return new ResponseEntity<>("Error occured parsing json", HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      return new ResponseEntity<>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(bpiRates, HttpStatus.OK);
  }
}
