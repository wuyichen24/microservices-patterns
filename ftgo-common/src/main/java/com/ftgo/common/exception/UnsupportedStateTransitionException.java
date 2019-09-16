package com.ftgo.common.exception;

public class UnsupportedStateTransitionException extends RuntimeException {
	public UnsupportedStateTransitionException(Enum state) {
		super("current state: " + state);
	}
}
