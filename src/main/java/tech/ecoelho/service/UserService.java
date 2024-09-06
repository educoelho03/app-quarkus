package tech.ecoelho.service;

import jakarta.enterprise.context.ApplicationScoped;
import tech.ecoelho.entity.UserEntity;
import tech.ecoelho.exception.UserNotFoundException;
import tech.ecoelho.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped // define a classe como um BEAN, liberando para fazer IoC
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user){
        userRepository.persist(user);
        return user;
    }

    public List<UserEntity> findAll(Integer page, Integer pageSize){
        return userRepository.findAll()
                .page(page, pageSize)
                .list();
    }

    public UserEntity findById(UUID userId){
        return (UserEntity) userRepository.findByIdOptional(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario nao encontrado"));
    }

    public UserEntity updateUserById(UUID userId, UserEntity userEntity){
        var user = findById(userId);

        user.setUsername(userEntity.getUsername());
        userRepository.persist(user); // salva no banco

        return user;

    }

    public void deleteById(UUID userId) {
        var user = findById(userId);

        userRepository.deleteById(user.getId());
    }
}
