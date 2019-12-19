package com.syphan.practice.demostatemachine.service;

import org.springframework.messaging.Message;
import org.springframework.statemachine.state.State;

public interface OrderStateChangeListener {
    void onStateChange(State<States, Events> state, Message<Events> message);
}
