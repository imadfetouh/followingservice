package com.imadelfetouh.followingservice.dal.configuration;

import com.imadelfetouh.followingservice.dal.ormmodel.Following;
import com.imadelfetouh.followingservice.dal.ormmodel.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class SessionWriteConfiguration {

    private final static SessionWriteConfiguration sessionWriteConfiguration = new SessionWriteConfiguration();
    private final ReadWriteConfiguration readWriteConfiguration;
    private final SessionFactory sessionFactory;

    public SessionWriteConfiguration() {
        readWriteConfiguration = ReadWriteConfiguration.getInstance();
        Configuration configuration = new Configuration();

        Properties properties = readWriteConfiguration.getProperties();
        configuration.addProperties(properties);
        configuration.getProperties().put(Environment.URL, "jdbc:mysql://"+System.getenv("FOLLOWINGSERVICE_MYSQL_MASTER_HOST")+":"+System.getenv("FOLLOWINGSERVICE_MYSQL_MASTER_PORT")+"/followingservice?createDatabaseIfNotExist=true");

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Following.class);

        sessionFactory = configuration.configure().buildSessionFactory();
    }

    public static SessionWriteConfiguration getInstance() {
        return sessionWriteConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
