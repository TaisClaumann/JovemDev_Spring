insert into pais(id, name) values(1, 'Brasil');
insert into pais(id, name) values(2, 'Argentina');

insert into equipe(id, name) values(1, 'Incriveis');
insert into equipe(id, name) values(2, 'Batman');

insert into campeonato(id, description, ano) values(1, 'Camp1', '2023');
insert into campeonato(id, description, ano) values(2, 'Camp2', '2022');
insert into campeonato(id, description, ano) values(3, 'Camp3', '2021');

insert into pista(id, tamanho, pais_id) values(1, 10, 1);
insert into pista(id, tamanho, pais_id) values(2, 15, 1);
insert into pista(id, tamanho, pais_id) values(3, 16, 1);

insert into piloto(id, nome, pais_id, equipe_id) values(1, 'Paulo', 1, 1);
insert into piloto(id, nome, pais_id, equipe_id) values(2, 'Pedro', 1, 1);
insert into piloto(id, nome, pais_id, equipe_id) values(3, 'Gustavo', 1, 1);
insert into piloto(id, nome, pais_id, equipe_id) values(4, 'Rafael', 1, 1);

insert into corrida(id, data, pista_id, campeonato_id) values(1, '2023-09-14T12:34:00Z', 1, 1);
insert into corrida(id, data, pista_id, campeonato_id) values(2, '2023-10-14T12:34:00Z', 2, 2);
insert into corrida(id, data, pista_id, campeonato_id) values(3, '2023-12-14T12:34:00Z', 2, 2);
insert into corrida(id, data, pista_id, campeonato_id) values(4, '2024-09-14T12:34:00Z', 1, 1);