package henrotaym.env.services;

import henrotaym.env.entities.User;
import henrotaym.env.repositories.UserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // 🔍 Récupère l'utilisateur depuis ta base
    Optional<User> optionalUser = this.userRepository.findByMail(username);
    if (optionalUser.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }

    User user = optionalUser.get();

    // ✅ Convertit l'utilisateur de ta base vers un UserDetails
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getMail())
        .password(user.getPassword()) // ⚠️ doit déjà être encodé !
        .roles(user.getRole().toString()) // ou .authorities(...)
        .build();
  }
}
