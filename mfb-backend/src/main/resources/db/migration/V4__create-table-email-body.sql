CREATE TABLE emails_body (
    id UUID NOT NULL,
    email_body_type VARCHAR(100) NOT NULL,
    body TEXT NOT NULL,
    PRIMARY KEY (id)
);