package D20PTIT.hoidap.security;

import D20PTIT.hoidap.model.User;
import D20PTIT.hoidap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.
        UserDetailsService;
import org.springframework.security.core.userdetails.
        UsernameNotFoundException;
import org.springframework.stereotype.Service;

// UserDetailsService được dùng để lấy cái thông tin người dùng khi nhận một username ( trong trường hợp này là email)
// và trả lại cho người dùng
@Service
public class UserRepoDetailsService
        implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        System.out.println("HERE");
        User user = userRepo.findByEmail(email);
        System.out.println(user);

        if (user != null) {
            System.out.println(user);
            return user;
        }
        throw new UsernameNotFoundException(
                "User '" + email + "' not found");
    }

}