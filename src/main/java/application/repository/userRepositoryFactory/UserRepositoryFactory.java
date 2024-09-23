package application.repository.userRepositoryFactory;

import application.entity.users.Users;
import application.repository.UserRepository;

public interface UserRepositoryFactory {
    <T extends Users> UserRepository<T> getRepository(T user);
}
