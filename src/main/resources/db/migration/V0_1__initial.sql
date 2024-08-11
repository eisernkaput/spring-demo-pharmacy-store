create sequence drug_sequence start with 1000 increment by 1;
create table drug
(
    id                      bigint    default nextval('drug_sequence')   primary key,
    name                    varchar(255)    not null,
    commercial_name         varchar(255)    not null,
    type                    varchar(100)    not null,
    purpose                 varchar(100)    not null,
    manufacturer            varchar(255)    not null,
    require_refrigeration   numeric(1,0)    not null,
    package_size            numeric(19,0)   not null,
    in_stock                numeric(1,0)    not null,

    last_update_date        timestamp       not null
);
create unique index drug_comm_name_idx on drug (commercial_name);


create sequence w_address_sequence start with 1000 increment by 1;
create table warehouse_address
(
    id                          bigint    default nextval('w_address_sequence')   primary key,
    address                     varchar(250)    not null,
    coordinates                 varchar(100)    not null,

    last_update_date            timestamp       not null,
    createDate                  timestamp default now()     not null
);


create sequence warehouse_sequence start with 1000 increment by 1;
create table warehouse
(
    id                          bigint    default nextval('warehouse_sequence')   primary key,
    warehouse_address_id        bigint          not null,

    last_update_date            timestamp       not null,
    createDate                  timestamp default now()     not null
);

alter table warehouse add constraint fk_warehouse_address_id foreign key (warehouse_address_id) references warehouse_address(id);
create index warehouse_address_id_idx on warehouse (warehouse_address_id);


create sequence w_stock_sequence start with 1000 increment by 1;
create table warehouse_stock
(
    id                          bigint    default nextval('w_stock_sequence')   primary key,
    warehouse_id                bigint          not null,
    shelving_num                varchar(100)    not null,
    is_refrigerator             numeric(1,0)    not null,
    total_stock_capacity        numeric(19,0)   not null,
    available_stock_capacity    numeric(19,0)   not null,

    last_update_date            timestamp       not null,
    createDate                  timestamp default now()     not null
);

alter table warehouse_stock add constraint fk_warehouse_id foreign key (warehouse_id) references warehouse(id);
create index w_stock_warehouse_id_idx on warehouse_stock (warehouse_id);


create sequence shelving_sequence start with 1000 increment by 1;
create table shelving
(
    id                      bigint    default nextval('shelving_sequence')   primary key,
    drug_id                 bigint          not null,
    warehouse_stock_id      bigint          not null,
    shipment_num            varchar(255)    not null,
    package_counter         numeric(19,0)   not null,
    shipment_date           date            not null,
    expirationDate          date            not null,
    package_size            numeric(19,0)   not null,
    in_stock                numeric(1,0)    not null,

    last_update_date        timestamp       not null,
    createDate              timestamp default now()     not null
);

alter table shelving add constraint fk_drug_id foreign key (drug_id) references drug(id);
alter table shelving add constraint fk_w_stock_id foreign key (warehouse_stock_id) references warehouse_stock(id);

create index shelving_drug_id_idx on shelving (drug_id);
create index shelving_ws_id_idx on shelving (warehouse_stock_id);