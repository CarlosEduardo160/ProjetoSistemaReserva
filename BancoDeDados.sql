Aqui deixo os comandos que utilizei na criação das tabelas

CREATE TABLE cliente (
                         id_cliente bigint generated always as identity primary key,
                         nome varchar(255) not null,
                         sobrenome varchar(255) not null,
)

CREATE TABLE mesa (
                      id_mesa bigint generated always as identity primary key,
                      numero_mesa varchar(50) not null unique,
                      capacidade integer not null
)

CREATE TABLE reserva  (
                          id_reserva bigint generated always as identity primary key,
                          id_cliente bigint not null references cliente(id_cliente),
                          id_mesa bigint not null references mesa(id_mesa),
                          data_reserva TIMESTAMP not null
)