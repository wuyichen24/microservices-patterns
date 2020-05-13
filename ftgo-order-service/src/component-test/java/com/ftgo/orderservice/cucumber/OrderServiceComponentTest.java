package com.ftgo.orderservice.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * The base class for the setup phase of testing the command consumer in the kitchen service.
 * 
 * @author  Wuyi Chen
 * @date    05/12/2020
 * @version 1.0
 * @since   1.0
 */
@RunWith(Cucumber.class)                                                // This annotation tells JUnit to use the Cucumber test runner.
@CucumberOptions(features = "src/component-test/resources/features")    // This annotation specifies where to find the Gherkin feature files.
public class OrderServiceComponentTest {
}