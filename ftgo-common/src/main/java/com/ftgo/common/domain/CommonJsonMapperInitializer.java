package com.ftgo.common.domain;

import io.eventuate.javaclient.commonimpl.JSonMapper;

import javax.annotation.PostConstruct;

public class CommonJsonMapperInitializer {
	@PostConstruct
	public void initialize() {
		registerMoneyModule();
	}

	public static void registerMoneyModule() {
		JSonMapper.objectMapper.registerModule(new MoneyModule());
	}
}
