package com.syphan.practice.demostatemachine.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Log4j2
public class LoggingMaListener implements StateMachineListener<BookStates, BookEvents> {

    @Override
    public void stateChanged(State<BookStates, BookEvents> from, State<BookStates, BookEvents> to) {
        System.out.println("==============###################----------------- " + to.getId());
        log.info("State changed from {} to {}", "test", to.getId());
    }

    @Override
    public void stateEntered(State<BookStates, BookEvents> state) {
        System.out.println("how to send object");
        log.info("Entered state {}", state);
    }

    @Override
    public void stateExited(State<BookStates, BookEvents> state) {
        System.out.println();
        log.info("Exited state {}", state);
    }

    @Override
    public void eventNotAccepted(Message<BookEvents> event) {
        log.error("Event not accepted: {}", event.getPayload());
    }

    @Override
    public void transition(Transition<BookStates, BookEvents> transition) {
        // Too much logging spoils the code =)
    }

    @Override
    public void transitionStarted(Transition<BookStates, BookEvents> transition) {
        // Too much logging spoils the code =)
    }

    @Override
    public void transitionEnded(Transition<BookStates, BookEvents> transition) {
        // Too much logging spoils the code =)
    }

    @Override
    public void stateMachineStarted(StateMachine<BookStates, BookEvents> stateMachine) {
        log.info("Machine started: {}", stateMachine);
    }

    @Override
    public void stateMachineStopped(StateMachine<BookStates, BookEvents> stateMachine) {
        log.info("Machine stopped: {}", stateMachine);
    }

    @Override
    public void stateMachineError(StateMachine<BookStates, BookEvents> stateMachine, Exception exception) {
        log.error("Machine error: {}", stateMachine);
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {
        log.info("Extended state changed: [{}: {}]", key, value);
    }

    @Override
    public void stateContext(StateContext<BookStates, BookEvents> stateContext) {
        // Too much logging spoils the code =)
    }
}
