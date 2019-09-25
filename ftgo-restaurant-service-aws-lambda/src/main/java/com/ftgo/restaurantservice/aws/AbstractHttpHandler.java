package com.ftgo.restaurantservice.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.ftgo.restaurantservice.aws.model.AwsLambdaError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ftgo.restaurantservice.aws.model.ApiGatewayResponse.buildErrorResponse;

public abstract class AbstractHttpHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		logger.debug("Got request: {}", input);
		try {
			beforeHandling(input, context);
			return handleHttpRequest(input, context);
		} catch (Exception e) {
			logger.error("Error handling request id: {}", context.getAwsRequestId(), e);
			return buildErrorResponse(new AwsLambdaError(
					"Internal Server Error", "500", context.getAwsRequestId(),
					"Error handling request: " + context.getAwsRequestId()
							+ " " + input.toString()));
		}
	}

	protected void beforeHandling(APIGatewayProxyRequestEvent request, Context context) {}

	protected abstract APIGatewayProxyResponseEvent handleHttpRequest(APIGatewayProxyRequestEvent request, Context context);
}
