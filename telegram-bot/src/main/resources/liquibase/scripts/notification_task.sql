-- liquibase formatted sql

-- changeset msalimgareeva:1
CREATE TABLE notification_task
(
    id                   SERIAL Primary key,
    chat_id               INTEGER,
    text_notification     TEXT,
    date_time_notification TIMESTAMP
);