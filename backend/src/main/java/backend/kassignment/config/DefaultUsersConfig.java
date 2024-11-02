package backend.kassignment.config;

import backend.kassignment.domain.Role;
import backend.kassignment.domain.User;
import backend.kassignment.domain.repository.RoleRepository;
import backend.kassignment.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DefaultUsersConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public DefaultUsersConfig(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Transactional
    public CommandLineRunner init() {
        return args -> {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            Role clientRole = roleRepository.findByName("ROLE_CLIENT");
            if (userRepository.findUserByEmail("admin@example.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("password"));
                admin.setRoles(List.of(adminRole)); // assign admin role
                userRepository.save(admin);
            }
        };
    }
}
