package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProfileImageCommand {

    private MultipartFile image;
}
