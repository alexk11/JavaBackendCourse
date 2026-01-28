package com.example.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class TransactionService {

    private final SessionFactory sessionFactory;

    public TransactionService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void executeInTransaction(Consumer<Session> action) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            action.accept(session);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public<T> T executeInTransaction(Supplier<T> action) {
        var session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        // если транзакция уже не в статусе NOT_ACTIVE
        // просто продолжаем выполнение в этой транзакции
        if (!transaction.getStatus().equals(TransactionStatus.NOT_ACTIVE)) {
            return action.get();
        }
        try {
            session.beginTransaction();
            T returnValue = action.get();
            transaction.commit();
            return returnValue;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

//    public<T> T executeInTransaction(Function<Session, T> action) {
//        Transaction transaction = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.getTransaction();
//            transaction.begin();
//
//            var result = action.apply(session);
//
//            session.getTransaction().commit();
//            return result;
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw e;
//        }
//    }

}
