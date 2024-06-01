package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor.LogCommandDispatchInterceptor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor.RealisationCommandDispatchInterceptor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor.SubjectCommandDispatchInterceptor;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor.UserCommandDispatchInterceptor;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@Configuration
@WriteApplicationBean
public class AxonConfig {

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus,
                                         SubjectCommandDispatchInterceptor subjectCommandDispatchInterceptor,
                                         UserCommandDispatchInterceptor userCommandDispatchInterceptor,
                                         RealisationCommandDispatchInterceptor realisationCommandDispatchInterceptor,
                                         LogCommandDispatchInterceptor logCommandDispatchInterceptor) {
        return DefaultCommandGateway.builder()
                .commandBus(commandBus)
                .dispatchInterceptors(
                        subjectCommandDispatchInterceptor,
                        userCommandDispatchInterceptor,
                        realisationCommandDispatchInterceptor,
                        logCommandDispatchInterceptor
                )
                .build();
    }

    @Bean
    public ConfigurerModule subscribingProcessorsConfigurerModule() {
        return configurer -> configurer.eventProcessing(
                processingConfigurer -> processingConfigurer
                        .registerSubscribingEventProcessor("subject")
                        .registerSubscribingEventProcessor("realisation")
                        .registerSubscribingEventProcessor("user")
        );
    }

    @Bean
    public SnapshotTriggerDefinition syllabusSnapshotTriggerDefinition(Snapshotter snapshotter) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, 500);
    }
}

