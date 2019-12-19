package com.syphan.practice.demostatemachine.config;

import com.syphan.practice.demostatemachine.service.BookEvents;
import com.syphan.practice.demostatemachine.service.BookStates;
import com.syphan.practice.demostatemachine.service.LoggingMaListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@Log4j2
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<BookStates, BookEvents> {


    @Override
    public void configure(StateMachineStateConfigurer<BookStates, BookEvents> states) throws Exception {
        states.withStates()
                .initial(BookStates.AVAILABLE)
                .states(EnumSet.allOf(BookStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BookStates, BookEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(BookStates.AVAILABLE)
                .target(BookStates.BORROWED)
                .event(BookEvents.BORROW)
                .and()
                .withExternal()
                .source(BookStates.BORROWED)
                .target(BookStates.AVAILABLE)
                .event(BookEvents.RETURN)
                .and()
                .withExternal()
                .source(BookStates.AVAILABLE)
                .target(BookStates.IN_REPAIR)
                .event(BookEvents.START_REPAIR)
                .and()
                .withExternal()
                .source(BookStates.IN_REPAIR)
                .target(BookStates.AVAILABLE)
                .event(BookEvents.END_REPAIR);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<BookStates, BookEvents> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(new LoggingMaListener());
    }

    @Bean
    public StateMachine<BookStates, BookEvents> buildMachine() throws Exception {
        StateMachineBuilder.Builder<BookStates, BookEvents> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(BookStates.AVAILABLE)
                .states(EnumSet.allOf(BookStates.class));

        builder.configureTransitions()
                .withExternal()
                .source(BookStates.AVAILABLE)
                .target(BookStates.BORROWED)
                .event(BookEvents.BORROW);

        return builder.build();
    }
}
