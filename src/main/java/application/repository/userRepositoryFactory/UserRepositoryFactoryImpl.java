package application.repository.userRepositoryFactory;

import application.entity.users.Customer;
import application.entity.users.Expert;
import application.entity.users.Users;
import application.repository.CustomerRepository;
import application.repository.ExpertRepository;
import application.repository.UserRepository;

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
