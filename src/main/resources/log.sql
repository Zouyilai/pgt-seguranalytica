create table log(
    id_log varchar(64) not null,
    id_token varchar(64) not null,
    data_acesso timestamp not null,
    endpoint_acesso varchar(256) not null,
    status_resposta varchar(64)  not null,
    primary key (id_log),
    foreign key (id_token) references token(`id_token`)
);
