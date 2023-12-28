package pl.lodz.p.it.rstrzalkowski.syllabus.shared.util;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Profile("write")
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteApplicationBean {
}
