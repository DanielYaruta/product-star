package com.product.star.homework;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Transactional
    @Modifying
    @Query("update Contact c set c.phone = :phone where c.id = :contactId")
    void updatePhone(@Param("contactId") long contactId, @Param("phone") String phoneNumber);

    @Transactional
    @Modifying
    @Query("update Contact c set c.email = :email where c.id = :contactId")
    void updateEmail(@Param("contactId") long contactId, @Param("email") String email);
}
