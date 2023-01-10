create table User
(
    id        bigint              not null primary key,
    firstName varchar(255)        not null,
    lastName  varchar(255)        not null,
    username  varchar(255) unique not null,
    password  varchar(255)        not null -- WHAT!? NOT ENCRYPTED!? ;-)
);

insert into User
    (id, firstName, lastName, username, password)
values (1, 'Phil', 'Ingwell', 'PhilIngwell', 'Password123') ,
    (2, 'Anna', 'Conda', 'AnnaConda', 'Password234');

create table Address
(
    id        bigint              not null primary key,
    address1  varchar(255)        not null,
    address2  varchar(255)                ,
    city      varchar(255)        not null,
    state     varchar(100)        not null,
    postal    varchar(10)         not null
);
insert into Address
    (id, address1, address2, city, state, postal)
 values
     (1, '101 Newyork Lane', 'Block B', 'Brentwood', 'TN', '37027');
 insert into Address
     (id, address1, address2, city, state, postal)
  values
      (2, '102 Newyork Lane', 'Block c', 'Brentwood', 'TN', '37027');
ALTER TABLE user
ADD address_id bigint;
ALTER TABLE user
ADD CONSTRAINT user_address_FK
FOREIGN KEY (address_id) REFERENCES address;
insert into User
    (id, firstName, lastName, username, password, address_id)
values (3, 'Joe', 'Ingwell', 'JoeIngwell', 'Password123', 1) ,
    (4, 'Joanna', 'Conda', 'JoannaConda', 'Password234', 2);