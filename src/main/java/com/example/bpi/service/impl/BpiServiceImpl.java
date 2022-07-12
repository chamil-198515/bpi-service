package com.example.bpi.service.impl;

import com.example.bpi.service.BpiService;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * BpiService Interface implementation
 *
 * @version 1.0.0
 * @author Chamil Ananda
 * @Date 7/12/2022
 */
@Service
public class BpiServiceImpl implements BpiService {

  private static final Logger logger = LoggerFactory.getLogger(BpiServiceImpl.class);
  private static final String NOTFOUND = "Not Found";

  @Autowired
  RestTemplate restTemplate;

  /**
   * ...method returns current BPI rate...
   * @param curencyType accept curency type as String
   * @return  String current BPI rate Json
   */
  @Override
  public String getCurrentBpi(String curencyType) {
    String url = String.format("https://api.coindesk.com/v1/bpi/currentprice/%s.json",curencyType); // External API return current BPI rate
    ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
    if((response.getStatusCode().value() == 404) || response == null) {
      logger.info(String.format("No result found for given curency type %s", curencyType));
      throw new NullPointerException(NOTFOUND);
    }
    return response.getBody();
  }

  /**
   * ...method returns current BPI historical rates...
   * @param curencyType accept curency type as String
   * @return @return  String current BPI historical rates Json
   */
  @Override
  public String getBpiHistory(String curencyType) {
    LocalDate currentDate = LocalDate.now(); //Current date
    LocalDate previousDate = currentDate.minusDays(30); // Date prior to 30 days
    String url = String.format("https://api.coindesk.com/v1/bpi/historical/close.json?start=%s&end=%s&currency=%s", previousDate.toString(), currentDate.toString(), curencyType); //External API that return historical BPI rates
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    if((response.getStatusCode().value() == 404) || response == null){
      logger.info(String.format("No historical result found for given curency type %s", curencyType));
      throw new NullPointerException(NOTFOUND);
    }
    return response.getBody();
  }

  /**
   * ...method to populate Min and Max rates for 30 days...
   * @param curencyType accept curency type as String
   * @return @return  String Min and Max rates json
   */
  @Override
  public String findHighestAndLowestBpi(String curencyType) throws Exception {
    List<Double> bpiHistoryRate = new ArrayList<>();
    String outputRate = null;
    double bpiCurrentRate = 0;
    try {
      String currentBpi = getCurrentBpi(curencyType); //Get current BPI Json
      String bpiHistory = getBpiHistory(curencyType); //Get Historical BPI Json
      String bpiCurrentRateString = new JSONObject(currentBpi).getJSONObject("bpi").getJSONObject(curencyType.toUpperCase()).get("rate").toString(); //Retrieve current rate
      bpiCurrentRate = toDoubleCommaSeperatedNumbers(bpiCurrentRateString); //Convert current rate String to Double
      bpiHistoryRate.add(bpiCurrentRate); //Add current rate to Histroical rate array list
      String bpiHistoryRateString = new JSONObject(bpiHistory).getJSONObject("bpi").toString(); //Get histrocal BPI Json string
      JSONObject bpiHistoryRateJsonObject = new JSONObject(bpiHistoryRateString);
      Iterator<String> bpiHistoryDateIterator = bpiHistoryRateJsonObject.keys(); // Get all the Keys

      while (bpiHistoryDateIterator.hasNext()){ // Loop thourgh keys (Date)
        String key = bpiHistoryDateIterator.next();
        String rateString = bpiHistoryRateJsonObject.get(key).toString(); //Get rate
        Double rate = toDoubleCommaSeperatedNumbers(rateString); // Convert to double
        bpiHistoryRate.add(rate);
      }
      Collections.sort(bpiHistoryRate); //Sort rates to assnding order

      JSONObject obj =  new JSONObject();
      obj.put("Min",bpiHistoryRate.get(0));
      obj.put("Max",bpiHistoryRate.get(bpiHistoryRate.size()-1));
      outputRate = obj.toString(); // Generate new json with Max and Min Rates
    }catch (ParseException pe) {
      logger.info(String.format("Error occured while converting json string to json objecte %s", pe.getMessage()));
      throw new ParseException("Json parser error",0);
    }catch (NullPointerException ne){
      logger.info(String.format("Null pointer exception occured %s", ne.getMessage()));
      throw new NullPointerException(NOTFOUND);
    }catch(Exception ex){
      logger.info(String.format("An error occured while generation min and max rates %s", ex.getMessage()));
      throw new Exception("Internal Error");
    }
    return outputRate;
  }

  private double toDoubleCommaSeperatedNumbers(String number) throws ParseException {
    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    Number formattedNumber = numberFormat.parse(number);
    return formattedNumber.doubleValue();
  }
}
