package util;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.CustomerRepository;
import repository.ExpertRepository;
import repository.impl.CustomerRepositoryImpl;
import repository.impl.ExpertRepositoryImpl;
import service.CustomerService;
import service.ExpertService;
import service.PasswordEncode;
import service.SignupService;
import service.impl.CustomerServiceImpl;
import service.impl.ExpertServiceImpl;
import service.impl.PasswordEncodeImpl;
import service.impl.SignupServiceImpl;


public class ApplicationContext {
    private static Logger logger;
    private static ApplicationContext INSTANCE;
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;


    private ApplicationContext() {
        getEntityManager();
        getLogger();
        logger.info("ApplicationContext initialized");
        CustomerRepository customerRepository = new CustomerRepositoryImpl(entityManager);
        ExpertRepository expertRepository = new ExpertRepositoryImpl(entityManager);
        AuthHolder authHolder = new AuthHolder();
        CustomerService customerService =new CustomerServiceImpl(customerRepository,authHolder);
        ExpertService expertService =new ExpertServiceImpl(expertRepository,authHolder);
        PasswordEncode passwordEncode =new PasswordEncodeImpl();
        SignupService signupService= new SignupServiceImpl(expertService,customerService, passwordEncode, authHolder);



    }


    public static ApplicationContext getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationContext();
        }
        return INSTANCE;
    }

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
        }
        return entityManagerFactory;
    }

    public static Logger getLogger() {
        if(logger==null){
            logger = LoggerFactory.getLogger(ApplicationContext.class);
        }
        return logger;
    }
}
