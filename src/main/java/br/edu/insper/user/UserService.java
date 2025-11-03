package br.edu.insper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElse(new User());
    }

    public User getUserByAuthId(String authId) {
        return userRepository.findByAuthId(authId);
    }

    public User removeUser(int id) {
        User user = getUser(id);
        userRepository.delete(user);
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
