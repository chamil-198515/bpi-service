package com.example.bpi.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.bpi.service.impl.BpiServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class BpiServiceTest {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private BpiServiceImpl bpiService;


  @Test
  void getCurrentBpiEuroTestHappyPathTest() throws JSONException {
    String url = "https://api.coindesk.com/v1/bpi/currentprice/eur.json";
    String responseJson = "{\"time\":{\"updated\":\"Jul 11, 2022 14:03:00 UTC\",\"updatedISO\":\"2022-07-11T14:03:00+00:00\",\"updateduk\":\"Jul 11, 2022 at 15:03 BST\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"rate\":\"20,336.8642\",\"description\":\"United States Dollar\",\"rate_float\":20336.8642},\"EUR\":{\"code\":\"EUR\",\"rate\":\"19,811.0749\",\"description\":\"Euro\",\"rate_float\":19811.0749}}}";
    Mockito.when(restTemplate.getForEntity(url,String.class)).thenReturn(new ResponseEntity<>(responseJson,
        HttpStatus.OK));
    String actualResponseJson = bpiService.getCurrentBpi("eur");
    Assertions.assertEquals(new JSONObject(responseJson).length(),new JSONObject(actualResponseJson).length());
  }

  @Test
  void getCurrentBpiEuroTestNotFoundTest(){
    Throwable exception =  assertThrows(NullPointerException.class, () -> {
      bpiService.getCurrentBpi("123");
    });
    assertEquals("Not Found", exception.getMessage());
  }



  @Test
  void getCurrentBpiUsdTestHappyPathTest() throws JSONException {
    String url = "https://api.coindesk.com/v1/bpi/currentprice/usd.json";
    String responseJson = "{\"time\":{\"updated\":\"Jul 11, 2022 17:22:00 UTC\",\"updatedISO\":\"2022-07-11T17:22:00+00:00\",\"updateduk\":\"Jul 11, 2022 at 18:22 BST\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"rate\":\"20,541.8869\",\"description\":\"United States Dollar\",\"rate_float\":20541.8869}}}";
    Mockito.when(restTemplate.getForEntity(url,String.class)).thenReturn(new ResponseEntity<>(responseJson,
        HttpStatus.OK));
    String actualResponseJson = bpiService.getCurrentBpi("usd");
    Assertions.assertEquals(new JSONObject(responseJson).length(),new JSONObject(actualResponseJson).length());
  }

  @Test
  void getBpiHistoryEuroHappyPathTest() throws JSONException {
    String url = "https://api.coindesk.com/v1/bpi/historical/close.json?start=2022-06-11&end=2022-07-11&currency=eur";
    String responseJson = "{\"bpi\":{\"2022-06-12\":26172.1405,\"2022-06-13\":24166.8164,\"2022-06-30\":18607.5543,\"2022-07-01\":18641.9285,\"2022-07-02\":18399.0292,\"2022-07-03\":18329.031,\"2022-07-04\":18344.3093,\"2022-07-05\":19364.2864,\"2022-07-06\":19415.5529,\"2022-07-10\":20745.4181},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index. BPI value data returned as EUR.\",\"time\":{\"updated\":\"Jul 11, 2022 17:49:54 UTC\",\"updatedISO\":\"2022-07-11T17:49:54+00:00\"}}";
    Mockito.when(restTemplate.getForEntity(url,String.class)).thenReturn(new ResponseEntity<>(responseJson,
        HttpStatus.OK));
    String actualResponseJson = bpiService.getBpiHistory("eur");
    Assertions.assertEquals(new JSONObject(responseJson).length(),new JSONObject(actualResponseJson).length());
  }

  @Test
  void getBpiHistoryEuroTestNotFoundTest(){
    Throwable exception =  assertThrows(NullPointerException.class, () -> {
      bpiService.getCurrentBpi("123");
    });
    assertEquals("Not Found", exception.getMessage());
  }


  @Test
  void findHighestAndLowestBpi() {

  }
}