DROP TABLE IF EXISTS DELIVERIES;

CREATE TABLE DELIVERIES (
                        id   INTEGER      NOT NULL AUTO_INCREMENT,
                        address VARCHAR(128) NOT NULL,
                        order_id INTEGER      NOT NULL,
                        payment_id INTEGER      NOT NULL,
                        status VARCHAR(128) NOT NULL,
                        PRIMARY KEY (id)
);