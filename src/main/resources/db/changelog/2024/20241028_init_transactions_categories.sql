--liquibase formatted sql
--changeset aboldyrev:initTransactionsCategories

INSERT INTO transaction_categories (category_name, is_expense, created_at, modified_at)
VALUES
    ('Продукты', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Транспорт', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Коммунальные услуги', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Арендная плата', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Медицина', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Развлечения', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Одежда', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Электроника', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Путешествия', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Образование', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Спорт', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Доход от работы', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Проценты по вкладам', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Прочее', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Другие доходы', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);