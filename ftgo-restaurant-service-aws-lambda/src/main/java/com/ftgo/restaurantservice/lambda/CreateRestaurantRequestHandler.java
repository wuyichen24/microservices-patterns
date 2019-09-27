package com.ftgo.restaurantservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ftgo.restaurantservice.api.controller.model.CreateRestaurantRequest;
import com.ftgo.restaurantservice.aws.model.ApiGatewayResponse;
import com.ftgo.restaurantservice.controller.model.CreateRestaurantResponse;
import com.ftgo.restaurantservice.model.Restaurant;
import com.ftgo.restaurantservice.service.RestaurantService;

import io.eventuate.common.json.mapper.JSonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static com.ftgo.restaurantservice.aws.model.ApiGatewayResponse.applicationJsonHeaders;

@Configuration
@Import(RestaurantServiceLambdaConfiguration.class)
public class CreateRestaurantRequestHandler extends AbstractAutowiringHttpRequestHandler {
	@Autowired
	private RestaurantService restaurantService;

	@Override
	protected Class<?> getApplicationContextClass() {
		return CreateRestaurantRequestHandler.class;
	}

	@Override
	protected APIGatewayProxyResponseEvent handleHttpRequest(APIGatewayProxyRequestEvent request, Context context) {
		CreateRestaurantRequest crr = JSonMapper.fromJson(request.getBody(), CreateRestaurantRequest.class);

		Restaurant rest = restaurantService.create(crr);

		return ApiGatewayResponse.builder().setStatusCode(200)
				.setObjectBody(new CreateRestaurantResponse(rest.getId()))
				.setHeaders(applicationJsonHeaders()).build();
	}
}
