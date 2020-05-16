package com.ftgo.common.exception;

/**
 * The exception to indicate that the state transition is not supported.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class UnsupportedStateTransitionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnsupportedStateTransitionException(Enum state) {
		super("current state: " + state);
	}
}
