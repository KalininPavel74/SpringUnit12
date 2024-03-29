package home.kalinin.web_client1.access;
import home.kalinin.web_client1.access.security.User1;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Data;

@Data
public class RegistrationUser1 {

  private String username;
  private String password;
  private String fullname;
  public User1 toUser(PasswordEncoder passwordEncoder) {
    return new User1(username, passwordEncoder.encode(password), fullname);
  }
  
}
