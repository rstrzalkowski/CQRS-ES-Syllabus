package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CreateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.subject.SubjectNameRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.subject.SubjectAlreadyExistsException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
@WriteApplicationBean
public class SubjectCommandDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final SubjectNameRepository subjectNameRepository;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (i, m) -> {
            if (CreateSubjectCommand.class.equals(m.getPayloadType())) {
                final CreateSubjectCommand createSubjectCommand = (CreateSubjectCommand) m.getPayload();
                if (subjectNameRepository.existsById(createSubjectCommand.getName())) {
                    throw new SubjectAlreadyExistsException();
                }
            }
            return m;
        };
    }
}
