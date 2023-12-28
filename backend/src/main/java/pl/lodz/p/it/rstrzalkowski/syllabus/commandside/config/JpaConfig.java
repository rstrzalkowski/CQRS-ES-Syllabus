package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@Configuration
@WriteApplicationBean
@EntityScan("pl.lodz.p.it.rstrzalkowski.syllabus.commandside")
public class JpaConfig {
}
