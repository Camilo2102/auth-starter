package co.com.webgen.starter.auth.authstarterv2.security;

import co.com.webgen.starter.auth.authstarterv2.model.Authority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private final Authority authority;
    @Override
    public String getAuthority() {
        return authority.getName().toString();
    }
}
