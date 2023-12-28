package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.RegisterCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user.UserPersonalIdRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.UserAlreadyRegisteredCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
@WriteApplicationBean
public class UserCommandDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final UserPersonalIdRepository userPersonalIdRepository;

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (i, m) -> {
            if (RegisterCommand.class.equals(m.getPayloadType())) {
                final RegisterCommand registerCommand = (RegisterCommand) m.getPayload();

                if (userPersonalIdRepository.findById(registerCommand.getEmail()).isPresent() ||
                    userPersonalIdRepository.findByPersonalId(registerCommand.getPersonalId()).isPresent()) {
                    throw new UserAlreadyRegisteredCommandExecutionException();
                }
            }
            return m;
        };
    }
}
