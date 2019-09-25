package com.ftgo.restaurantservice.lambda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftgo.restaurantservice.lambda.RestaurantServiceLambdaConfiguration;
import com.ftgo.restaurantservice.service.RestaurantService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantServiceLambdaConfiguration.class)
public class RestaurantServiceLambdaConfigurationTest {

  @Autowired
  private RestaurantService restaurantService;
  @Test
  public void shouldInitialize() {}
}