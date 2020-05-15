package com.ftgo.accountingservice.message;

import io.eventuate.tram.consumer.common.DuplicateMessageDetector;
import io.eventuate.tram.consumer.common.SubscriberIdAndMessage;

/**
 * The detector to detect duplicated message.
 * 
 * @author  Wuyi Chen
 * @date    05/15/2020
 * @version 1.0
 * @since   1.0
 */
public class NoopDuplicateMessageDetector implements DuplicateMessageDetector {
	@Override
	public boolean isDuplicate(String consumerId, String messageId) {
		return false;
	}

	@Override
	public void doWithMessage(SubscriberIdAndMessage subscriberIdAndMessage, Runnable callback) {
		callback.run();
	}
}
