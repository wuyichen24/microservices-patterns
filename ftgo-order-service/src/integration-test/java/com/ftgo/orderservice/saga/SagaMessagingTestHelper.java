/*
 * Copyright 2020 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ftgo.orderservice.saga;

import io.eventuate.common.id.IdGenerator;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.commands.common.Command;
import io.eventuate.tram.sagas.orchestration.SagaCommandProducer;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;

import javax.inject.Inject;

/**
 * The helper class for testing sending and receiving commands.
 *
 * @author  Wuyi Chen
 * @date    05/11/2020
 * @version 1.0
 * @since   1.0
 */
public class SagaMessagingTestHelper {
	@Inject
	ContractVerifierMessaging<?> contractVerifierMessaging;

	@Autowired
	private SagaCommandProducer sagaCommandProducer;

	@Autowired
	private IdGenerator idGenerator;

	public <C extends Command, R> R sendAndReceiveCommand(CommandEndpoint<C> commandEndpoint, C command, Class<R> replyClass, String sagaType) {
		// TODO verify that replyClass is allowed

		String sagaId = idGenerator.genId().asString();

		String replyTo = sagaType + "-reply";
		sagaCommandProducer.sendCommand(sagaType, sagaId, commandEndpoint.getCommandChannel(), null, command, replyTo);

		ContractVerifierMessage response = contractVerifierMessaging.receive(replyTo);

		String payload = (String) response.getPayload();
		return (R) JSonMapper.fromJson(payload, replyClass);
	}
}
