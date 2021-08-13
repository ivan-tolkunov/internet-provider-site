package ua.ivan.provider.service;

import ua.ivan.provider.model.*;
import ua.ivan.provider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.ivan.provider.security.SecurityUser;

import java.util.List;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return SecurityUser.fromUser(userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists")));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
    }

    public User findById(Long id) {
        return userRepository.getById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User banUser(Long id) {
        findById(id).setStatus(Status.BANNED);
        return saveUser(findById(id));
    }

    public User unbanUser(Long id) {
        findById(id).setStatus(Status.ACTIVE);
        return saveUser(findById(id));
    }

    public User confirmDonate(int sum, Long userId, Donate donate) {
        User user = findById(userId);
        user.setBalance(user.getBalance() + sum);
        return saveUser(user);
    }

    public User addNewUser(User user, String password) {
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        user.setPassword(password);
        user.setBalance(0);
        return saveUser(user);
    }

}
