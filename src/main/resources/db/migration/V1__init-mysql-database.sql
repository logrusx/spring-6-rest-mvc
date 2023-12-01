drop table if exists beer;
drop table if exists customer;
create table beer
(
    beer_style       tinyint check (beer_style between 0 and 9),
    price            decimal(38, 2),
    quantity_on_hand integer,
    version          integer,
    created_date     datetime(6),
    update_date      datetime(6),
    beer_name        varchar(20),
    id               varchar(36) not null,
    upc              varchar(255),
    primary key (id)
) engine=InnoDB;
create table customer
(
    version       integer,
    creation_date datetime(6),
    id            varchar(36) not null,
    customer_name varchar(255),
    primary key (id)
) engine=InnoDB;
