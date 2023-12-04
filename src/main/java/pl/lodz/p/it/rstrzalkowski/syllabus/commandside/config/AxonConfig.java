package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.config.ConfigurerModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor.LogCommandDispatchInterceptor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor.SubjectCommandDispatchInterceptor;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@Configuration
@WriteApplicationBean
public class AxonConfig {

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus,
                                         SubjectCommandDispatchInterceptor subjectCommandDispatchInterceptor,
                                         LogCommandDispatchInterceptor logCommandDispatchInterceptor) {
        return DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .dispatchInterceptors(
                        subjectCommandDispatchInterceptor,
                        logCommandDispatchInterceptor
                )
                .build();
    }

    @Bean
    public ConfigurerModule subscribingProcessorsConfigurerModule() {
        return configurer -> configurer.eventProcessing(
                // To configure a processor to be subscribing ...
                processingConfigurer -> processingConfigurer
                        .registerSubscribingEventProcessor("subject")
                        .registerSubscribingEventProcessor("realisation")
        );
    }
}
