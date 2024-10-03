create table token (
    `id_token` varchar(64) not null,
    `valor_token` varchar(256) not null,
    `id_usuario` varchar(64) not null,
    `data_criacao` timestamp not null,
    `data_expiracao` timestamp not null,
    primary key (`id_token`),
    foreign key (`id_usuario`) references usuario(`id_usuario`)
);
