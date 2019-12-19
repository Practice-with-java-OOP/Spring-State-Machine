package com.syphan.practice.demostatemachine;

import com.syphan.practice.demostatemachine.service.Events;
import com.syphan.practice.demostatemachine.service.OrdersStateHandler;
import com.syphan.practice.demostatemachine.service.States;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@Log4j2
public class DemoStateMachineApplication implements CommandLineRunner {

    @Autowired
    private OrdersStateHandler ordersStateHandler;

    public static void main(String[] args) {
        SpringApplication.run(DemoStateMachineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        stateMachine.sendEvent(Events.assemble);
//        stateMachine.sendEvent(Events.deliver);
        ordersStateHandler.handleEvent(
                MessageBuilder
                        .withPayload(Events.deliver)
                        .setHeader("order-id", 100L)
                        .build(), States.ASSEMBLED);
    }
}
