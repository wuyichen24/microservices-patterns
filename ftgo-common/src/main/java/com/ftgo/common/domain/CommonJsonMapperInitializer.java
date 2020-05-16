package com.ftgo.common.domain;

import javax.annotation.PostConstruct;

import io.eventuate.common.json.mapper.JSonMapper;

/**
 * The initializer for the JSON mapper.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class CommonJsonMapperInitializer {
	@PostConstruct
	public void initialize() {
		registerMoneyModule();
	}

	public static void registerMoneyModule() {
		JSonMapper.objectMapper.registerModule(new MoneyModule());
	}
}
