create table usuario (
    `id_usuario` varchar(64) not null,
    `nome` varchar(64) not null,
    `senha` varchar(64) not null,
    `data_cadastro` date not null,
    `email` varchar(64) not null,
    primary key (`id_usuario`)
);
