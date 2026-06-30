package com.product.star.homework;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collection;
import java.util.List;

public class ContactDao {

    private static final String GET_ALL_CONTACTS_SQL =
            "SELECT ID, NAME, SURNAME, PHONE_NUMBER, EMAIL FROM CONTACT";

    private static final String SAVE_CONTACT_SQL =
            "INSERT INTO CONTACT(NAME, SURNAME, PHONE_NUMBER, EMAIL) VALUES (:name, :surname, :phoneNumber, :email)";

    private static final RowMapper<Contact> CONTACT_ROW_MAPPER =
            (rs, i) -> new Contact(
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
        return namedJdbcTemplate.query(GET_ALL_CONTACTS_SQL, CONTACT_ROW_MAPPER);
    }

    public void saveAll(Collection<Contact> contacts) {
        var args = contacts.stream()
                .map(ContactDao::contactToArgs)
                .toArray(MapSqlParameterSource[]::new);
        namedJdbcTemplate.batchUpdate(SAVE_CONTACT_SQL, args);
    }

    private static MapSqlParameterSource contactToArgs(Contact contact) {
        return new MapSqlParameterSource()
                .addValue("name", contact.getName())
                .addValue("surname", contact.getSurname())
                .addValue("email", contact.getEmail())
                .addValue("phoneNumber", contact.getPhone());
    }
}
