package com.syphan.practice.demostatemachine.config;

import com.syphan.practice.demostatemachine.service.Events;
import com.syphan.practice.demostatemachine.service.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class OrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.ORDERED)
                .end(States.PAYED)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.ORDERED)
                .target(States.ASSEMBLED)
                .event(Events.assemble)
                .and()
                .withExternal()
                .source(States.ASSEMBLED)
                .target(States.DELIVERED)
                .event(Events.deliver)
                .and()
                .withExternal()
                .source(States.DELIVERED)
                .target(States.INVOICED)
                .event(Events.release_invoice)
                .and()
                .withExternal()
                .source(States.INVOICED)
                .target(States.PAYED)
                .event(Events.payment_received)
                .and()
                .withExternal()
                .source(States.ORDERED)
                .target(States.CANCELLED)
                .event(Events.cancel)
                .and()
                .withExternal()
                .source(States.ASSEMBLED)
                .target(States.CANCELLED)
                .event(Events.cancel)
                .and()
                .withExternal()
                .source(States.DELIVERED)
                .target(States.RETURNED)
                .event(Events.claim)
                .and()
                .withExternal()
                .source(States.INVOICED)
                .target(States.RETURNED)
                .event(Events.claim)
                .and()
                .withExternal()
                .source(States.RETURNED)
                .target(States.CANCELLED)
                .event(Events.cancel)
                .and()
                .withExternal()
                .source(States.RETURNED)
                .target(States.ASSEMBLED)
                .event(Events.reassemble);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener());
    }

    private static final class StateMachineListener extends StateMachineListenerAdapter<States, Events> {
        @Override
        public void stateChanged(State<States, Events> from, State<States, Events> to) {
            System.out.println("Order state changed to " + to.getId());
        }

        @Override
        public void stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
            System.out.println("loi roi a nhe ############");
            super.stateMachineError(stateMachine, exception);
        }

        @Override
        public void eventNotAccepted(Message<Events> event) {
            System.out.println("khong chap nhan nhe -----------");
            super.eventNotAccepted(event);
        }
    }

    @Bean
    public StateMachine<States, Events> buildMachine() throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(States.ORDERED)
                .end(States.PAYED)
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withExternal()
                .source(States.ORDERED)
                .target(States.ASSEMBLED)
                .event(Events.assemble);

        return builder.build();
    }
}
