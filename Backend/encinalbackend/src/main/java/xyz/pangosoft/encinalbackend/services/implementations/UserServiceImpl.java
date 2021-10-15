package xyz.pangosoft.encinalbackend.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pangosoft.encinalbackend.models.User;
import xyz.pangosoft.encinalbackend.repositories.IUserRepository;
import xyz.pangosoft.encinalbackend.services.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            logger.error("Error: no existe el usuario en el sistema.");
            throw new UsernameNotFoundException("Error: no existe el usuario en el sistema.");
        }

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .peek(authority -> logger.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User singleUser(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User singleUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User create(User user) {
        return this.userRepository.save(user);
    }
}
