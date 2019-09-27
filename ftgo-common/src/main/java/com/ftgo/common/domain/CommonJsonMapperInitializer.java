package com.ftgo.common.domain;

import javax.annotation.PostConstruct;

import io.eventuate.common.json.mapper.JSonMapper;

public class CommonJsonMapperInitializer {
	@PostConstruct
	public void initialize() {
		registerMoneyModule();
	}

	public static void registerMoneyModule() {
		JSonMapper.objectMapper.registerModule(new MoneyModule());
	}
}
