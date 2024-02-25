package co.com.webgen.starter.auth.authstarterv2.service;

import co.com.webgen.starter.auth.authstarterv2.DTO.AuthResponseDTO;
import co.com.webgen.starter.auth.authstarterv2.DTO.LoginRequestDTO;
import co.com.webgen.starter.auth.authstarterv2.DTO.RegisterRequestDTO;
import co.com.webgen.starter.auth.authstarterv2.model.User;
import co.com.webgen.starter.auth.authstarterv2.repository.UserRepository;
import co.com.webgen.starter.auth.authstarterv2.security.SecurityUser;
import co.com.webgen.web.starter.exeptions.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO login(LoginRequestDTO loginData) throws HttpException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUserName(), loginData.getPassword()));

        User user = userRepository.findByUserName(loginData.getUserName())
                                    .orElseThrow(() ->  new HttpException("Not found", HttpStatus.NOT_FOUND));

        String token = jwtService.getToken(new SecurityUser(user));
        return AuthResponseDTO.builder().token(jwtService.getToken(new SecurityUser(user))).build();
    }

    public AuthResponseDTO register(RegisterRequestDTO registerData) {
        User user = User.builder()
                .userName(registerData.getUserName())
                .password(passwordEncoder.encode(registerData.getPassword()))
                .build();

        this.userRepository.save(user);

        return AuthResponseDTO.builder().token(jwtService.getToken(new SecurityUser(user))).build();
    }

}
