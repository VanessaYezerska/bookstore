CREATE TABLE cart_item
(
    id        bigint NOT NULL auto_increment,
    price     float(53),
    quantity  integer,
    orderid   bigint,
    productid bigint NOT NULL,
    userid    bigint NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB;

CREATE TABLE orders
(
    id         bigint NOT NULL auto_increment,
    orderdate  date,
    totalprice float(53),
    userid     bigint NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB;


CREATE TABLE user
(
    id           bigint NOT NULL auto_increment,
    address      varchar(255),
    card_details varchar(255),
    email        varchar(255),
    fullname     varchar(255),
    password     varchar(255),
    username     varchar(255),
    PRIMARY KEY (id)
) engine=InnoDB;

-- ALTER TABLE cart_item
--     ADD CONSTRAINT fk70xmbp7pqjfwh3ja3mnhqu146
--         FOREIGN KEY (orderid)
--             REFERENCES orders (id):
--
-- ALTER TABLE cart_item
--     ADD CONSTRAINT fk9pw5b35944xvbr3h8avlakscl FOREIGN KEY (productid) REFERENCES product (id):
--
-- ALTER TABLE cart_item
--     ADD CONSTRAINT fk52rnx78ha4q08yw2d1rsxyvjh
--         FOREIGN KEY (userid)
--             REFERENCES user (id):
--
-- ALTER TABLE orders
--     ADD CONSTRAINT fkdxew8n76x1bnoxjas0qxrlbq6
--         FOREIGN KEY (userid)
--             REFERENCES user (id);
