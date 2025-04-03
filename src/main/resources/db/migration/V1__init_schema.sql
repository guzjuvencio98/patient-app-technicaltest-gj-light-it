CREATE TABLE patient
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100)        NOT NULL,
    email         VARCHAR(150) UNIQUE NOT NULL,
    phone         VARCHAR(20),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    document_path VARCHAR(255)
);
