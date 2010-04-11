
    alter table ss_resource_authority 
        drop 
        foreign key FKD7216891C67601C1;

    alter table ss_resource_authority 
        drop 
        foreign key FKD721689170DB5B33;

    alter table ss_role_authority 
        drop 
        foreign key FKE536BA7993BA26B3;

    alter table ss_role_authority 
        drop 
        foreign key FKE536BA79C67601C1;

    alter table ss_user_role 
        drop 
        foreign key FK1306854B38E4EA93;

    alter table ss_user_role 
        drop 
        foreign key FK1306854B93BA26B3;

    drop table if exists ss_authority;

    drop table if exists ss_resource;

    drop table if exists ss_resource_authority;

    drop table if exists ss_role;

    drop table if exists ss_role_authority;

    drop table if exists ss_user;

    drop table if exists ss_user_role;

    create table ss_authority (
        id bigint not null auto_increment,
        display_name varchar(255),
        name varchar(255) not null unique,
        primary key (id)
    ) ENGINE=InnoDB;

    create table ss_resource (
        id bigint not null auto_increment,
        position double precision not null,
        resource_type varchar(255) not null,
        value varchar(255) not null unique,
        primary key (id)
    ) ENGINE=InnoDB;

    create table ss_resource_authority (
        resource_id bigint not null,
        authority_id bigint not null
    ) ENGINE=InnoDB;

    create table ss_role (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        primary key (id)
    ) ENGINE=InnoDB;

    create table ss_role_authority (
        role_id bigint not null,
        authority_id bigint not null
    ) ENGINE=InnoDB;

    create table ss_user (
        id bigint not null auto_increment,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table ss_user_role (
        user_id bigint not null,
        role_id bigint not null
    ) ENGINE=InnoDB;

    alter table ss_resource_authority 
        add index FKD7216891C67601C1 (authority_id), 
        add constraint FKD7216891C67601C1 
        foreign key (authority_id) 
        references ss_authority (id);

    alter table ss_resource_authority 
        add index FKD721689170DB5B33 (resource_id), 
        add constraint FKD721689170DB5B33 
        foreign key (resource_id) 
        references ss_resource (id);

    alter table ss_role_authority 
        add index FKE536BA7993BA26B3 (role_id), 
        add constraint FKE536BA7993BA26B3 
        foreign key (role_id) 
        references ss_role (id);

    alter table ss_role_authority 
        add index FKE536BA79C67601C1 (authority_id), 
        add constraint FKE536BA79C67601C1 
        foreign key (authority_id) 
        references ss_authority (id);

    alter table ss_user_role 
        add index FK1306854B38E4EA93 (user_id), 
        add constraint FK1306854B38E4EA93 
        foreign key (user_id) 
        references ss_user (id);

    alter table ss_user_role 
        add index FK1306854B93BA26B3 (role_id), 
        add constraint FK1306854B93BA26B3 
        foreign key (role_id) 
        references ss_role (id);
