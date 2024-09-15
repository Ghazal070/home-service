package util;


import entity.Duty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ApplicationContext {
    private static Logger logger;
    private static ApplicationContext INSTANCE;
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;


    private ApplicationContext() {
        getEntityManager();
        getLogger();
        logger.info("ApplicationContext initialized");


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
