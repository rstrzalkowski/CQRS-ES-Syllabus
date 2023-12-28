package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

@Configuration
@ReadApplicationBean
@EntityScan(basePackages = {
        "pl.lodz.p.it.rstrzalkowski.syllabus.queryside",
        "org.axonframework.eventhandling.tokenstore",
        "org.axonframework.modelling.saga.repository.jpa",
        "org.axonframework.eventsourcing.eventstore.jpa"
})
public class JpaConfig {
}
