package com.product.star.homework;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.List;

public class ContactDao {

    private static final RowMapper<Contact> CONTACT_ROW_MAPPER = (rs, i) -> new Contact(
            rs.getLong("ID"),
            rs.getString("NAME"),
            rs.getString("SURNAME"),
            rs.getString("EMAIL"),
            rs.getString("PHONE_NUMBER")
    );

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public ContactDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public List<Contact> getAllContacts() {
        return namedJdbcTemplate.query(
                "SELECT ID, NAME, SURNAME, EMAIL, PHONE_NUMBER FROM CONTACT",
                CONTACT_ROW_MAPPER
        );
    }

    public Contact getContact(long contactId) {
        return namedJdbcTemplate.queryForObject(
                "SELECT ID, NAME, SURNAME, EMAIL, PHONE_NUMBER FROM CONTACT WHERE ID = :id",
                new MapSqlParameterSource("id", contactId),
                CONTACT_ROW_MAPPER
        );
    }

    public long addContact(Contact contact) {
        var keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(
                "INSERT INTO CONTACT(NAME, SURNAME, EMAIL, PHONE_NUMBER) VALUES(:name, :surname, :email, :phone)",
                new MapSqlParameterSource()
                        .addValue("name", contact.getName())
                        .addValue("surname", contact.getSurname())
                        .addValue("email", contact.getEmail())
                        .addValue("phone", contact.getPhone()),
                keyHolder,
                new String[]{"id"}
        );
        return keyHolder.getKey().longValue();
    }

    public void updatePhoneNumber(long contactId, String phoneNumber) {
        namedJdbcTemplate.update(
                "UPDATE CONTACT SET PHONE_NUMBER = :phone WHERE ID = :id",
                new MapSqlParameterSource()
                        .addValue("phone", phoneNumber)
                        .addValue("id", contactId)
        );
    }

    public void updateEmail(long contactId, String email) {
        namedJdbcTemplate.update(
                "UPDATE CONTACT SET EMAIL = :email WHERE ID = :id",
                new MapSqlParameterSource()
                        .addValue("email", email)
                        .addValue("id", contactId)
        );
    }

    public void deleteContact(long contactId) {
        namedJdbcTemplate.update(
                "DELETE FROM CONTACT WHERE ID = :id",
                new MapSqlParameterSource("id", contactId)
        );
    }
}
