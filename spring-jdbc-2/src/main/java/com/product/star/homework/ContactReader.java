package com.product.star.homework;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ContactReader {

    private static final String SEPARATOR = ",";

    public List<Contact> readFromFile(Path filePath) {
        try {
            return Files.lines(filePath)
                    .map(ContactReader::parseContact)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Contact parseContact(String csvLine) {
        var parts = csvLine.split(SEPARATOR);
        return new Contact(parts[0], parts[1], parts[2], parts[3]);
    }
}
