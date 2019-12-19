package com.syphan.practice.demostatemachine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.LifecycleObjectSupport;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
public class OrdersStateHandler extends LifecycleObjectSupport {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Override
    protected void onInit() throws Exception {
        stateMachine
                .getStateMachineAccessor()
                .doWithAllRegions(function -> function.addStateMachineInterceptor(new StateMachineInterceptorAdapter<States, Events>() {
                    @Override
                    public void preStateChange(State<States, Events> state, Message<Events> message, Transition<States, Events> transition, StateMachine<States, Events> stateMachine) {
                        if (message.getPayload().equals(Events.deliver)) {
                            System.out.println("oke bay be");
                            onStateChange(state, message);
                        }
                    }
                }));
    }

    public void handleEvent(Message<Events> event, States sourceState) {
        stateMachine.stop();
        stateMachine
                .getStateMachineAccessor()
                .doWithAllRegions(access -> access.resetStateMachine(new DefaultStateMachineContext<States, Events>(sourceState, null, null, null)));
        stateMachine.start();
        stateMachine.sendEvent(event);

        System.out.println();
    }

    //    @Override
    private void onStateChange(State<States, Events> state, Message<Events> message) {
        Long orderId = message.getHeaders().get("order-id", Long.class);
        System.out.println("#############---------------- " + orderId + ", status: " + state.getId());
    }
}
