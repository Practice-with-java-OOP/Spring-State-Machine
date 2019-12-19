package com.syphan.practice.demostatemachine;

import com.syphan.practice.demostatemachine.service.Book;
import com.syphan.practice.demostatemachine.service.BookEvents;
import com.syphan.practice.demostatemachine.service.BookStates;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
@Log4j2
public class DemoStateMachineApplication implements CommandLineRunner {

    private final StateMachine<BookStates, BookEvents> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(DemoStateMachineApplication.class, args);
    }

    @Autowired
    public DemoStateMachineApplication(StateMachine<BookStates, BookEvents> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void run(String... args) throws Exception {
        stateMachine.start();
        Message<BookEvents> message = MessageBuilder
                .withPayload(BookEvents.BORROW)
                .setHeader("test", Book.builder().id(100L).name("sy phan test").states(BookStates.IN_REPAIR).build())
                .build();
        boolean borrowAccepted1 = stateMachine.sendEvent(message);
        log.info("borrow accepted: " + borrowAccepted1);
//        stateMachine.sendEvent(BookEvents.RETURN);
//        stateMachine.sendEvent(BookEvents.BORROW);
        boolean returnAccepted = stateMachine.sendEvent(BookEvents.RETURN);
        log.info("return accepted: " + returnAccepted);
        boolean borrowAccepted = stateMachine.sendEvent(BookEvents.BORROW);
        log.info("borrow accepted: " + borrowAccepted);
//        stateMachine.stop();
    }
}
