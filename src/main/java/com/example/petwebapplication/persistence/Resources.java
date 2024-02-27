package com.example.petwebapplication.persistence;

import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.bean.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.SynchronizationType;

import javax.enterprise.context.RequestScoped;

@ApplicationScoped
public class Resources {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Produces
    @Default
    @RequestScoped
    private EntityManager createJTAEntityManager()
    {
        return entityManagerFactory.createEntityManager(SynchronizationType.SYNCHRONIZED);
    }

    private void closeDefaultEntityManager(@Disposes @Default EntityManager entityManager)
    {
        entityManager.close();
    }

}
