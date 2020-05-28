package contracts;

org.springframework.cloud.contract.spec.Contract.make {
    label 'confirmCreateTicket'
    input {
        messageFrom('kitchenService')
        messageBody('''{"ticketId":1}''')
        messageHeaders {
            header('command_type','com.ftgo.kitchenservice.api.command.ConfirmCreateTicketCommand')
            header('command_saga_type','com.ftgo.orderservice.saga.createorder.CreateOrderSaga')
            header('command_saga_id',$(consumer(regex('[0-9a-f]{16}-[0-9a-f]{16}'))))
            header('command_reply_to', 'com.ftgo.orderservice.saga.createorder.CreateOrderSaga-reply')
        }
    }
    outputMessage {
        sentTo('com.ftgo.orderservice.sagas.createorder.CreateOrderSaga-reply')
        headers {
            header('reply_type', 'io.eventuate.tram.commands.common.Success')
            header('reply_outcome-type', 'SUCCESS')
        }
    }
}