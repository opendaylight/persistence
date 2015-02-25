/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.opendaylight.persistence.DataStore;
import org.opendaylight.persistence.PersistenceException;
import org.opendaylight.persistence.Query;
import org.opendaylight.persistence.common.query.QueryLoggerDecorator;

import com.google.common.base.Preconditions;

public class JpaDataStore implements DataStore<JpaContext> {
    /*
     * Text taken from "Java Persistence with Hibernate (Christian Bauer & Gavin King)": Hibernate EntityManager is a
     * wrapper around Hibernate Core that provides the JPA programming interfaces, supports the JPA entity instance
     * lifecycle, and allows you to write queries with the standardized Java Persistence query language. Because JPA
     * functionality is a subset of Hibernate's native capabilities, you may wonder why you should use the EntityManager
     * package on top of Hibernate. There are several advantages, one of them is a configuration simplification when you
     * configure your project for Hibernate EntityManager: You no longer have to list all annotated classes (or XML
     * mapping files) in your configuration file.
     */

    /*
     * A SessionFactory represents a particular logical data-store configuration in a Hibernate application. The
     * EntityManagerFactory has the same role in a JPA application, and you configure an EntityManagerFactory (EMF)
     * either with configuration files or in application code just as you would configure a SessionFactory. The
     * configuration of an EMF, together with a set of mapping metadata (usually annotated classes), is called the
     * persistence unit.
     */

    /*
     * These are your primary programming interfaces in Java Persistence: - javax.persistence.Persistence: A startup
     * class that provides a static method for the creation of an EntityManagerFactory. -
     * javax.persistence.EntityManagerFactory: The equivalent to a Hibernate SessionFactory. This runtime object
     * represents a particular persistence unit. It's thread-safe, is usually handled as a singleton, and provides
     * methods for the creation of EntityManager instances. - javax.persistence.EntityManager: The equivalent to a
     * Hibernate Session. This single-threaded, nonshared object represents a particular unit of work for data access.
     * It provides methods to manage the lifecycle of entity instances and to create Query instances. -
     * javax.persistence.Query: This is the equivalent to a Hibernate Query. An object is a particular JPA query
     * language or native SQL query representation, and it allows safe binding of parameters and provides various
     * methods for the execution of the query. - javax.persistence.EntityTransaction: This is the equivalent to a
     * Hibernate Transaction, used in Java SE environments for the demarcation of RESOURCE_LOCAL transactions. In Java
     * EE, you rely on the standardized javax.transaction.UserTransaction interface of JTA for programmatic transaction
     * demarcation.
     */

    /*
     * You can use native Hibernate mappings or APIs if needed. Obviously, importing a Hibernate API into your code
     * makes porting the code to a different JPA provider more difficult. Hence, it becomes critically important to
     * isolate these parts of your code properly, or at least to document why and when you used a native Hibernate
     * feature. The SessionFactory interface is useful if you need programmatic control over the second-level cache
     * regions. You can get a SessionFactory by casting the EntityManagerFactory first: HibernateEntityManagerFactory
     * hibernateEntityManagerFactory = (HibernateEntityManagerFactory) entityManagerFactory; SessionFactory
     * sessionFactory = hibernateEntityManagerFactory.getSessionFactory(); The same technique can be applied to get a
     * Session from an EntityManager: HibernateEntityManager hibernateEntityManager = (HibernateEntityManager)
     * entityManager; Session session = entityManager.getSession(); This isn't the only way to get a native API from the
     * standardized EntityManager. The JPA specification supports a getDelegate() method that returns the underlying
     * implementation: Session session = (Session)entityManager.getDelegate();
     */

    private EntityManagerFactory entityManagerFactory;

    /**
     * Creates a new data store implementation based on JPA.
     *
     * @param persistenceUnitName
     *            persistence unit name.
     * @param loggerProvider
     *            logger provider
     */
    public JpaDataStore(String persistenceUnitName) {
        this.entityManagerFactory = Persistence
                .createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public <T> T execute(final Query<T, JpaContext> query)
            throws PersistenceException {
        Preconditions.checkNotNull(query, "query");

        final Query<T, JpaContext> queryDecorator = new QueryLoggerDecorator<T, JpaContext>(
                query);

        EntityTransaction entityTransaction = null;

        try {
            EntityManager entityManager = this.entityManagerFactory
                    .createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            T result = queryDecorator.execute(new JpaContext(entityManager));
            entityTransaction.commit();
            entityManager.close();
            return result;
        } catch (Exception e) {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }
}