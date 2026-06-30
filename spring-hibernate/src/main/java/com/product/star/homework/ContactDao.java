package com.product.star.homework;

import org.hibernate.SessionFactory;

import java.util.List;

public class ContactDao {

    private final SessionFactory sessionFactory;

    public ContactDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Contact> getAllContacts() {
        try (var session = sessionFactory.openSession()) {
            return session.createQuery("from Contact", Contact.class).getResultList();
        }
    }

    public Contact getContact(long contactId) {
        try (var session = sessionFactory.openSession()) {
            return session.get(Contact.class, contactId);
        }
    }

    public long addContact(Contact contact) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contactId = (Long) session.save(contact);
            transaction.commit();
            return contactId;
        }
    }

    public void updatePhone(long contactId, String phoneNumber) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contact = session.get(Contact.class, contactId);
            if (contact != null) {
                contact.setPhone(phoneNumber);
                session.update(contact);
            }
            transaction.commit();
        }
    }

    public void updateEmail(long contactId, String email) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contact = session.get(Contact.class, contactId);
            if (contact != null) {
                contact.setEmail(email);
                session.update(contact);
            }
            transaction.commit();
        }
    }

    public void deleteContact(long contactId) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            var contact = session.get(Contact.class, contactId);
            if (contact != null) {
                session.delete(contact);
            }
            transaction.commit();
        }
    }
}
