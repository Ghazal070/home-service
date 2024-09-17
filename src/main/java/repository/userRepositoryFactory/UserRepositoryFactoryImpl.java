package repository.userRepositoryFactory;

import entity.users.Customer;
import entity.users.Expert;
import entity.users.Users;
import repository.CustomerRepository;
import repository.ExpertRepository;
import repository.UserRepository;

public class UserRepositoryFactoryImpl implements UserRepositoryFactory {
    private final CustomerRepository customerRepository;
    private final ExpertRepository expertRepository;


    public UserRepositoryFactoryImpl(CustomerRepository customerRepository, ExpertRepository expertRepository) {
        this.customerRepository = customerRepository;
        this.expertRepository = expertRepository;
    }

    @Override
    public <T extends Users> UserRepository<T> getRepository(T user) {
        if (user instanceof Customer) {
            return (UserRepository<T>) customerRepository;
        } else if (user instanceof Expert) {
            return (UserRepository<T>) expertRepository;
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }
    }
}
