package com.imadelfetouh.followingservice.dal.configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class SessionReadConfiguration {

    private final SessionFactory sessionFactory;

    public SessionReadConfiguration() {
        Configuration configuration = ReadWriteConfiguration.getInstance().getConfiguration();
        configuration.getProperties().put(Environment.URL, "jdbc:mysql://"+System.getenv("FOLLOWINGSERVICE_MYSQL_REPLICA_HOST")+":"+System.getenv("FOLLOWINGSERVICE_MYSQL_REPLICA_PORT")+"/followingservice");

        sessionFactory = configuration.configure().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
