package co.com.webgen.starter.auth.authstarterv2.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String ownerId;
    private String mail;
    private String userName;
    private String password;
    private String role;
}
