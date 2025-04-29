CREATE TABLE product
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    title           VARCHAR(255) DEFAULT NULL,
    author          VARCHAR(255) DEFAULT NULL,
    publisher       VARCHAR(255) DEFAULT NULL,
    published_date  DATETIME(6) DEFAULT NULL,
    description     TEXT,
    isbn_identifier VARCHAR(255) DEFAULT NULL,
    page_count      INT DEFAULT NULL,
    categories      VARCHAR(255) DEFAULT NULL,
    language        VARCHAR(255) DEFAULT NULL,
    image_links     VARCHAR(255) DEFAULT NULL,
    list_price      DOUBLE DEFAULT NULL,
    retail_price    DOUBLE DEFAULT NULL,
    is_bestseller   TINYINT(1) DEFAULT NULL,
    is_new_arrival  TINYINT(1) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
