package repository.userRepositoryFactory;

import entity.users.Users;
import repository.UserRepository;

public interface UserRepositoryFactory {
    <T extends Users> UserRepository<T> getRepository(T user);
}
