# spring-jdbc

Модуль с примерами и домашним заданием по работе с базой данных через **Spring JDBC** (`JdbcTemplate` / `NamedParameterJdbcTemplate`) на PostgreSQL.

## Структура

```
src/main/java/com/product/star/
├── account/manager/          — примеры работы с JdbcTemplate (счета/Account)
│   ├── Account.java
│   ├── AccountDao.java                  (интерфейс)
│   ├── JdbcAccountDao.java              (реализация на JdbcTemplate, "?" параметры)
│   ├── NamedJdbcAccountDao.java         (реализация на NamedParameterJdbcTemplate)
│   ├── Main.java                        (точка входа, демонстрация работы DAO)
│   └── config/ApplicationConfiguration.java
│
├── common/                   — общая JDBC-конфигурация
│   ├── JdbcConfig.java                  (DataSource, JdbcTemplate, NamedParameterJdbcTemplate)
│   └── PropertiesConfiguration.java     (подключение jdbc.properties)
│
└── homework/                 — домашнее задание: ContactDao
    ├── Contact.java
    ├── ContactDao.java
    └── ContactConfiguration.java

src/main/resources/jdbc.properties      — параметры подключения к локальной PostgreSQL
src/test/java/.../homework/ContactDaoTests.java
src/test/resources/contact.sql          — схема таблицы CONTACT + тестовые данные
```

## Домашнее задание — ContactDao

`ContactDao` — Spring Bean для взаимодействия с таблицей `CONTACT` в PostgreSQL через `NamedParameterJdbcTemplate`. Реализованы операции:

- получение всех контактов (`getAllContacts`);
- поиск контакта по ID (`getContact`);
- добавление нового контакта с автоматическим присвоением ID (`addContact`, через `GeneratedKeyHolder`);
- обновление номера телефона (`updatePhoneNumber`);
- обновление email-адреса (`updateEmail`);
- удаление контакта по ID (`deleteContact`).

Бин собирается в `ContactConfiguration`, которая импортирует `JdbcConfig` и `PropertiesConfiguration` из пакета `common`.

## Настройка подключения

Параметры подключения к локальной PostgreSQL задаются в `src/main/resources/jdbc.properties`:

```properties
jdbc.url=jdbc:postgresql://localhost:5432/postgres
jdbc.username=<ваш системный пользователь Postgres>
jdbc.password=
jdbc.initialSize=5
jdbc.maxActive=10
jdbc.driverClassName=org.postgresql.Driver
```

> На Mac с PostgreSQL, установленным через Homebrew, суперпользователем по умолчанию является системный логин (например, `danielyaruta`), а не `postgres`.

## Запуск тестов

Требуется запущенный локальный PostgreSQL.

```bash
./gradlew :spring-jdbc:test --tests ContactDaoTests
```

Тесты используют `@Sql("classpath:contact.sql")` — скрипт пересоздаёт таблицу `CONTACT` и наполняет её тестовыми данными перед каждым прогоном.

## Требования

- Java 17
- Gradle (через wrapper `./gradlew`)
- Локальный PostgreSQL
