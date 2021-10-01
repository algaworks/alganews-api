set foreign_key_checks = 0;

delete from `user`;
delete from skill;
delete from post;
delete from tag;
delete from payment_posts;
delete from payment;
delete from payment_bonus;
delete from entry_category;
delete from cash_flow_entry;
delete from SPRING_SESSION;
delete from SPRING_SESSION_ATTRIBUTES;

set foreign_key_checks = 1;

alter table `user` auto_increment = 1;
alter table skill auto_increment = 1;
alter table post auto_increment = 1;
alter table tag auto_increment = 1;
alter table payment auto_increment = 1;
alter table entry_category auto_increment = 1;
alter table cash_flow_entry auto_increment = 1;

--Senha: alganews

insert into `user` ( id, name, email, password, `role`, created_at, updated_at, active, avatar, bio, birthdate, phone, taxpayer_id, price_per_word, location_country, location_state, location_city, bank_account_bank_code, bank_account_agency, bank_account_number, bank_account_digit, bank_account_type ) values
( 1, 'João da Silva', 'joao.ger@alganews.com.br', '$2a$04$o1/wxlkdhHbUPjZXSBqftOGOEhhP0tehr7n/7.1xWazpIvcC21Bba', 'MANAGER', DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), true, 'avatar-joao.jpeg', 'Donec consequat varius velit. Cras eros erat, vivamus vel scelerisque nunc.', '1993-10-28', '27912344321', '42919326007', 5.0, 'Brasil', 'São Paulo', 'São Paulo', '001', '231', '21241', '5', 'SAVING' ),
( 2, 'Maria Joaquina', 'maria.vnd@alganews.com.br', '$2a$04$o1/wxlkdhHbUPjZXSBqftOGOEhhP0tehr7n/7.1xWazpIvcC21Bba', 'MANAGER', DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), true, 'avatar-maria.jpeg', 'Proin ac erat maximus ipsum dictum dignissim dignissim et urna. Donec laoreet magna sit amet magna auctor, facilisis faucibus dui porttitor. In vitae sagittis dolor, et interdum est.', '1991-01-18', '11911342321', '83883231053', 1.5, 'Brasil', 'Minas Gerais', 'Belo Horizonte', '002', '211', '10341', '2', 'CHECKING' ),
( 3, 'José Souza', 'jose.aux@alganews.com.br', '$2a$04$o1/wxlkdhHbUPjZXSBqftOGOEhhP0tehr7n/7.1xWazpIvcC21Bba', 'ASSISTANT', DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), true, 'avatar-jose.jpeg', 'In accumsan ligula et commodo sodales. Nam dignissim consectetur metus, id mattis ex elementum sed. Proin sit amet pretium erat.', '1991-11-24', '62981222767', '31674069502', 5.15, 'Brasil', 'Pernambuco', 'Paulista', '002', '131', '11241', '3', 'CHECKING' ),
( 4, 'Sebastião Martins', 'sebastiao.cad@alganews.com.br', '$2a$04$o1/wxlkdhHbUPjZXSBqftOGOEhhP0tehr7n/7.1xWazpIvcC21Bba', 'ASSISTANT', DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), true, 'avatar-sebastiao.jpeg', 'Donec laoreet magna sit amet magna auctor, facilisis. Et interdum est.', '1989-07-01', '27982843280', '81420515403', 4.1, 'Brasil', 'Espírito Santo', 'Serra', '001', '5190', '1118729', '8', 'CHECKING' ),
( 5, 'Manoel Lima', 'manoel.loja@gmail.com', '$2a$04$o1/wxlkdhHbUPjZXSBqftOGOEhhP0tehr7n/7.1xWazpIvcC21Bba', 'EDITOR', DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), true, 'avatar-manoel.jpeg', 'Ut eu iaculis ipsum. Morbi et justo at ante blandit tristique. Etiam accumsan eleifend consectetur. Suspendisse quis feugiat massa, nec sodales justo.', '2001-03-05', '(71) 99748-0457', '90882025406', 13.15, 'Brasil', 'Bahia', 'Salvador', '001', '0151', '0515579', '7', 'SAVING' ),
( 6, 'Débora Mendonça', 'email.teste.debora@gmail.com', '$2a$04$o1/wxlkdhHbUPjZXSBqftOGOEhhP0tehr7n/7.1xWazpIvcC21Bba', 'EDITOR', DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), UTC_TIMESTAMP, true, 'avatar-debora.jpeg', 'Praesent tincidunt lectus vitae nibh sodales, nec porta neque ullamcorper. Phasellus bibendum, tortor eget porttitor bibendum.', '1972-08-05', '61995931558', '05038691200', 5.50, 'Brasil', 'Roraima', 'Ji-Paraná', '341', '8907', '09121', '2', 'SAVING' ),
( 7, 'Carlos Lima', 'email.teste.carlos@gmail.com', '$2a$04$o1/wxlkdhHbUPjZXSBqftOGOEhhP0tehr7n/7.1xWazpIvcC21Bba', 'EDITOR', DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), DATE_SUB(UTC_TIMESTAMP, INTERVAL 6 MONTH), true, 'avatar-carlos.jpeg', 'Sed in fermentum lacus, eu viverra metus. Cras sit amet justo a purus pellentesque ultrices. Donec euismod arcu est, nec laoreet mi mattis non. In accumsan ligula et commodo sodales.', '1975-04-12', '41987712159', '69278332429', 2.0, 'Brasil', 'Paraná', 'Curitiba', '341', '2347', '1065160', '8', 'SAVING' );



insert into skill(name, percentage, user_id) values
('Javascript', 90, 1),
('React', 50, 1),
('Node', 75, 1),
('Java', 40, 2),
('React', 30, 2),
('Angular', 25, 2),
('C++', 40, 3),
('C', 50, 3),
('C#', 35, 3),
('Pascal', 60, 4),
('VB', 10, 4),
('Python', 35, 4),
('C++', 40, 5),
('C', 50, 5),
('C#', 35, 5),
('C++', 30, 6),
('TypeScript', 20, 6),
('C#', 35, 6),
('C++', 45, 7),
('Node', 20, 7),
('C#', 85, 7);



INSERT INTO entry_category
(name, created_at, updated_at, system_generated, updated_by_id, created_by_id, type)
VALUES
('Pagamento', UTC_TIMESTAMP, UTC_TIMESTAMP, true, 1, 1, 'EXPENSE'),
('Infra', UTC_TIMESTAMP, UTC_TIMESTAMP, false, 2, 1, 'EXPENSE'),
('Anúncios', UTC_TIMESTAMP, UTC_TIMESTAMP, false, 2, 1, 'REVENUE');


INSERT INTO cash_flow_entry ( transacted_on, `type`, description, system_generated,
category_id, amount, created_by_id, created_at, updated_at, updated_by_id)
VALUES
(DATE_SUB(CURRENT_DATE(), INTERVAL 1  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 3210.10, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 1  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 1  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 5220.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 4220.32, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 3210.40, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 5  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 3330.63, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 5  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 5  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 2345.20, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 1510.34, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 1610.50, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 9  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 1740.40, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 9  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 9  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 10 MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 2810.30, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 10 MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 10 MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 11 MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 4910.14, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 11 MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 11 MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 2340.10, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 1  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 3310.15, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 1  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 1  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 4410.40, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 5130.15, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 6410.30, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 5  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 3710.10, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 5  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 5  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 2210.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 2510.10, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 1410.99, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 9  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 3530.80, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 9  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 9  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 10 MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 5310.70, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 10 MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 10 MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 11 MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 1210.40, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 11 MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 11 MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 3210.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 1210.50, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 1530.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 2  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 5  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 2310.40, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 5  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 5  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 2261.30, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 2710.20, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 2870.10, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 4150.99, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 5810.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 8310.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 3510.99, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 9  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 3310.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 9  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 9  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 4000.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 3  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 2710.20, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 4  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 1870.10, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 6  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 4150.99, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 7  MONTH), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 5810.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 'REVENUE', 'Entrada avulsa', false, 3, 8310.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 8  MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), 'EXPENSE', 'Despesa avulsa', false, 2, 1510.99, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 3  DAY),   'EXPENSE', 'Despesa avulsa', false, 2, 2261.30, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 3  DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 3  DAY), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 14 DAY),   'REVENUE', 'Entrada avulsa', false, 3, 2710.20, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 14 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 14 DAY), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 16 DAY),   'EXPENSE', 'Despesa avulsa', false, 2, 2870.10, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 16 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 16 DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 17 DAY),   'REVENUE', 'Entrada avulsa', false, 3, 4150.99, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 17 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 17 DAY), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 18 DAY),   'EXPENSE', 'Despesa avulsa', false, 2, 5810.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 18 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 18 DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 18 DAY),   'REVENUE', 'Entrada avulsa', false, 3, 8310.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 18 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 18 DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 12 DAY),   'EXPENSE', 'Despesa avulsa', false, 2, 3510.99, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 12 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 12 DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 9  DAY),   'REVENUE', 'Entrada avulsa', false, 3, 3310.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 9  DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 9  DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 3  DAY),   'EXPENSE', 'Despesa avulsa', false, 2, 4000.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 3  DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 3  DAY), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 4  DAY),   'REVENUE', 'Entrada avulsa', false, 3, 2710.20, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 4  DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 4  DAY), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 6  DAY),   'EXPENSE', 'Despesa avulsa', false, 2, 1870.10, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 6  DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 6  DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 7  DAY),   'REVENUE', 'Entrada avulsa', false, 3, 4150.99, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 7  DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 7  DAY), 2),
(DATE_SUB(CURRENT_DATE(), INTERVAL 8  DAY),   'EXPENSE', 'Despesa avulsa', false, 2, 5810.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 8  DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 8  DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 18 DAY),   'REVENUE', 'Entrada avulsa', false, 3, 8310.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 18 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 18 DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 12 DAY),   'EXPENSE', 'Despesa avulsa', false, 2, 1510.99, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 12 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 12 DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 19 DAY),   'REVENUE', 'Entrada avulsa', false, 3, 3530.80, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 19 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 19 DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY),   'REVENUE', 'Entrada avulsa', false, 3, 5310.70, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 11 DAY),   'REVENUE', 'Entrada avulsa', false, 3, 1210.40, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 11 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 11 DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 12 DAY),   'EXPENSE', 'Despesa avulsa', false, 2, 3210.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 12 DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 12 DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 2  DAY),   'REVENUE', 'Entrada avulsa', false, 3, 1210.50, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 2  DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 2  DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 2  DAY),   'EXPENSE', 'Despesa avulsa', false, 2, 1530.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 2  DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 2  DAY), 1),
(DATE_SUB(CURRENT_DATE(), INTERVAL 9  DAY),   'REVENUE', 'Entrada avulsa', false, 3, 2310.00, 1, DATE_SUB(CURRENT_DATE(), INTERVAL 9  DAY),   DATE_SUB(CURRENT_DATE(), INTERVAL 9  DAY), 1);

INSERT
	INTO
	post (updated_by_id, image, title, slug, body, editor_id, deleted, created_at, updated_at, earnings_words, earnings_price_per_word, earnings_total_amount)
VALUES
(
1,
'voce-sabe-como-e-trabalhar-em-uma-startup.jpg',
'Você sabe como é trabalhar em uma startup?',
'voce-sabe-como-e-trabalhar-em-uma-startup',
'# Peto nec praerupit hic

## Consistere erat transitque cives petita numine nubes

Lorem markdownum si modo stipata! Corpus natos, una ille ira Dianae: Lyrcea
candentia, [alii](http://modocur.net/increvit-vitium) orat in vincar crimina
feritate. Nobis *audire* nomen Troiaeque subeunt! Fortunata
[moras](http://tenentem.org/).

> Achille [ab](http://iamque-prosiliunt.io/aptato) animavit, potentia et refers
> satum clamore: Eumenidum dura cui annum. Paludes omnis via solacia ait
> [collige ferens](http://neveet.org/opifer)!

Cetera ministro dat [securus putet](http://illa.io/suoque.html): cui *me* nec
mea. Quia forcipe pariter exanimi adversaque omnes **in grandia mensam** unusque
Latinas: ille? Et avidam subiectis dotalem. O *dubium cucurri caelo* nunc, nec:
preces remorata fluctus ira **tanges**, nitidum est haerentes cupidine
auctoremque.

## Cum odium templis pectore isdem per dat

Dextra illud si totam fatemur subiecit, suae insuperabile navigii potest pruinas
latarumque. Cito stricto et natura fumantia terras querellae facit incaluit et
nolet, puniceo. Nat putria vates litore apte arcanis vinoque altera et iungitur?

    mediaPixelMinisite(uatRtf(wrap, duplexPpgaDonationware, markup), 2);
    tutorial_megabyte *= -1;
    bluetoothExternal = bezelSystemTransfer;
    tft_wildcard_handle *= of.mca.character_processor_microphone(page_node -
            frame(copy_base, vpi_shift), ide_day_bus(160605, surfaceRaster,
            tableJava) + -5, rfid_xp(dll_torrent(koffice, jsfKeySoft)));

## Est minus

Paesti [praemia](http://bracchia-effugiunt.com/videtur) temperiemque iubet
contrahitur de Ennomon senserunt natos, concitat facta posse. Dixit contrahitur
et mihi ferebam, satis quod **natas omnia** indigenae patulis.

1. Nubibus rigorem
2. Unco flumina interea factum praebuerat se iram
3. Sive tum
4. Mos armenti sitae exspiravit domino
5. Est saepe carinae cunctos mala Antaeo perque

Subdita occuluere, **fluctus**. Paras in [alto](http://www.et.net/aevi-velle)
mirabere *sedet* cumque est Euboicas quin: quae. Angue proelia exstant surgere
adhuc velut quoniam et dicta piacula dabitur manus ignibus plausit. In tellus
talibus semesarumque actis mi reddita nostra paulatimque reclusa diversa; sacra
tibi, mea.

Nos clara me at vulnus in falsi censu boves innixusque nodum. Neve gravi, arcus
tecti pronepos nimia siquid vindicat meorum ambae.',
6, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 1 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 1 DAY), 266, 5.50, 1463.00),
(
1,
'como-contribuir-para-um-repositorio-no-github.jpg',
'Como contribuir para um repostório no GitHub',
'como-contribuir-para-um-repositorio-no-github',
'# Septemplice Coroneus prosiliunt eruerit nostras petatur maneant

## Tuta Ophionides odere taceam

Lorem markdownum tuens spatiantur primo animos sinistrae et videndo troia hoc.
Excipit illo suo scintilla feras illae quondam, mihi fonti, metues. Vidit nec
animo imagine semper.

    oem_markup_ipad = google_access_vector;
    file_computer_optical = icmpLogic + ospf_dns(29);
    smart.bar = dbms;
    var memory_keyboard = data;

Semper in quam, Peliden et solum in armenti petiit, sepulcris defecerat
tenetque, aut duae texit aera. Stare **pacisci non** dent umquam *dicta est*,
iam. Mea super sibi suppressa agmen poposcerat coniunx regna confusa metuit
patriaeque et modo imagine usque. Bello plura molliter dubitare *et haut aratri*
vincetur deus auras commoda iacent Limyren.

## Humo mox modo quos adflata aram

Cyllene quaecumque [adflabat](http://lupum-ad.org/casa.html) heros. Illam
meritoque quoque inmeritae nudo [stupet](http://moveri.net/) talia natus mersa,
invidiosa utrumque dedecus praestant posita nimium Cereris vulnera et!

Europa vaticinor. Illa Euagrus, erat luctor vicerat dictu admovique signa
soleant aut tantum quod lustro huic: qualem.

Tanta de possit! Trado cognoscit Lucinam bucera erat quamquam prementem modo
conplexibus etiam cauda errabat. Qua sed aethera, iterum?

Rediret per minus cepisse contenta Ulixes et tum natos, dotalem in epulae, ut
sude, cyclopum. Adfectu [hasta amor vidi](http://www.sine.org/) ora digna
progenitore magno tu dum pellis. Fecit alite carina relevare. Altera figuras,
hinc manu Ceyx ter afflatibus exire fuerat; sine arma sol, formidine, est
pariter. Ille invaserat.',
7, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 1 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 1 DAY), 210, 5.50, 1155.00),
(
1,
'desktop-ou-notebook.jpg',
'Desktop ou Notebook?',
'desktop-ou-notebook',
'# Cava amor fuit armis et quoque

## Praeterque favet Ausoniis capillis colla

Lorem markdownum oculi. Letum hac has cera Calliroe, manu liceat, amans nata
quibus? Gravis occasus sorores *refundit Ascalaphus* in cunctis fugientem velles
colla novem Leucosiamque vagus texerat Troianae! Seriphi telum Erymantho nec
Iuppiter Iove capulo Venerem.

> [Tegit](http://sumite.org/gaudiasilvis.html) Hypanis et digna nec dicite
> iaculum, materno, brevissima quam. Prius ululare colloquium hinc ille vulnere
> quaerit sanguine te pontus passim roganda; dum tamen gloria,
> [indicio](http://www.pinu.io/)! Nulla ipse erit orbem non vidit est per passa
> virtute telo *ubi*.

## Omne quod satiatur simillimus serae

Illa pecudesque corripe animam at metu, fidumque diffugiunt hunc moderatius
vitae, effetus quis. Gratia suspicor busta.

    drive_multithreading = snow(url / 1 * disk);
    thumbnail(drive_twain_ip - linux_swipe_vpi, dhcp_worm_link);
    if (client(refresh + extensionAd)) {
        vga.lan_socket_page = biometricsParityOdbc(server_wimax);
        botnetDataUri = lossless;
        socialMemory(acl, installer_zip_engine + 4, driveReality);
    } else {
        dv_inkjet.hfs_ugc(gate);
        cyberspacePeoplewareAsp.url_bitmap = 4;
    }

## Tibi saucia

Omni mora tunc vetitorum aut haec sceleratus etiam incurrite nam, damus Adoni
domum, obnoxius. Multiplicique iungit suspirat deflevit etiam deiecto fecisse
frugis Titania [aere](http://mora.org/haerentia.aspx), lancea ipse! Volant cum
iustis, feres expers inhibere in [verbis natura in](http://videritcristis.org/)
pectus descendunt silices. Posita semine orabam prius omnia Cnosiacaeque parat
vultumque habenas dignissima adeunt cum suis? Inducere nova et tricuspide hac
flexerat adit sollemni Charybdis revelli fuso cedere visa vena amanti aliter.

1. Ingentes margine sanguine Argum
2. Qua inimica corpus formasque habebat
3. Nomen subito

Fletu rostro. Fudi [trium](http://dixit.io/denique-nato.html). Quoque putabant
gelidoque solidumve te corporis, tot non quisquam rapitur cursus reclinis
altaque, repulsus abactas crescere: detque! Duo vires deus media te tibi fratrem
sparserat flagrantem flecti messes, in tectus lenta Ericthonio illo reverentia
Athin.',
6, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 15 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 15 DAY), 252, 5.50, 1386.00),
(
1,
'dicas-para-desviciar-do-cafe.jpg',
'Dicas para desviciar do café',
'dicas-para-desviciar-do-cafe',
'# Peto nec praerupit hic

## Consistere erat transitque cives petita numine nubes

Lorem markdownum si modo stipata! Corpus natos, una ille ira Dianae: Lyrcea
candentia, [alii](http://modocur.net/increvit-vitium) orat in vincar crimina
feritate. Nobis *audire* nomen Troiaeque subeunt! Fortunata
[moras](http://tenentem.org/).

> Achille [ab](http://iamque-prosiliunt.io/aptato) animavit, potentia et refers
> satum clamore: Eumenidum dura cui annum. Paludes omnis via solacia ait
> [collige ferens](http://neveet.org/opifer)!

Cetera ministro dat [securus putet](http://illa.io/suoque.html): cui *me* nec
mea. Quia forcipe pariter exanimi adversaque omnes **in grandia mensam** unusque
Latinas: ille? Et avidam subiectis dotalem. O *dubium cucurri caelo* nunc, nec:
preces remorata fluctus ira **tanges**, nitidum est haerentes cupidine
auctoremque.

## Cum odium templis pectore isdem per dat

Dextra illud si totam fatemur subiecit, suae insuperabile navigii potest pruinas
latarumque. Cito stricto et natura fumantia terras querellae facit incaluit et
nolet, puniceo. Nat putria vates litore apte arcanis vinoque altera et iungitur?

    mediaPixelMinisite(uatRtf(wrap, duplexPpgaDonationware, markup), 2);
    tutorial_megabyte *= -1;
    bluetoothExternal = bezelSystemTransfer;
    tft_wildcard_handle *= of.mca.character_processor_microphone(page_node -
            frame(copy_base, vpi_shift), ide_day_bus(160605, surfaceRaster,
            tableJava) + -5, rfid_xp(dll_torrent(koffice, jsfKeySoft)));

## Est minus

Paesti [praemia](http://bracchia-effugiunt.com/videtur) temperiemque iubet
contrahitur de Ennomon senserunt natos, concitat facta posse. Dixit contrahitur
et mihi ferebam, satis quod **natas omnia** indigenae patulis.

1. Nubibus rigorem
2. Unco flumina interea factum praebuerat se iram
3. Sive tum
4. Mos armenti sitae exspiravit domino
5. Est saepe carinae cunctos mala Antaeo perque

Subdita occuluere, **fluctus**. Paras in [alto](http://www.et.net/aevi-velle)
mirabere *sedet* cumque est Euboicas quin: quae. Angue proelia exstant surgere
adhuc velut quoniam et dicta piacula dabitur manus ignibus plausit. In tellus
talibus semesarumque actis mi reddita nostra paulatimque reclusa diversa; sacra
tibi, mea.

Nos clara me at vulnus in falsi censu boves innixusque nodum. Neve gravi, arcus
tecti pronepos nimia siquid vindicat meorum ambae.',
7, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 14 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 14 DAY), 266, 2.00, 532.00),
(
1,
'dicas-para-organizar-seu-trabalho.jpg',
'Dicas para organizar seu trabalho no dia a dia',
'dicas-para-organizar-seu-trabalho',
'# Septemplice Coroneus prosiliunt eruerit nostras petatur maneant

## Tuta Ophionides odere taceam

Lorem markdownum tuens spatiantur primo animos sinistrae et videndo troia hoc.
Excipit illo suo scintilla feras illae quondam, mihi fonti, metues. Vidit nec
animo imagine semper.

    oem_markup_ipad = google_access_vector;
    file_computer_optical = icmpLogic + ospf_dns(29);
    smart.bar = dbms;
    var memory_keyboard = data;

Semper in quam, Peliden et solum in armenti petiit, sepulcris defecerat
tenetque, aut duae texit aera. Stare **pacisci non** dent umquam *dicta est*,
iam. Mea super sibi suppressa agmen poposcerat coniunx regna confusa metuit
patriaeque et modo imagine usque. Bello plura molliter dubitare *et haut aratri*
vincetur deus auras commoda iacent Limyren.

## Humo mox modo quos adflata aram

Cyllene quaecumque [adflabat](http://lupum-ad.org/casa.html) heros. Illam
meritoque quoque inmeritae nudo [stupet](http://moveri.net/) talia natus mersa,
invidiosa utrumque dedecus praestant posita nimium Cereris vulnera et!

Europa vaticinor. Illa Euagrus, erat luctor vicerat dictu admovique signa
soleant aut tantum quod lustro huic: qualem.

Tanta de possit! Trado cognoscit Lucinam bucera erat quamquam prementem modo
conplexibus etiam cauda errabat. Qua sed aethera, iterum?

Rediret per minus cepisse contenta Ulixes et tum natos, dotalem in epulae, ut
sude, cyclopum. Adfectu [hasta amor vidi](http://www.sine.org/) ora digna
progenitore magno tu dum pellis. Fecit alite carina relevare. Altera figuras,
hinc manu Ceyx ter afflatibus exire fuerat; sine arma sol, formidine, est
pariter. Ille invaserat.',
7, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 15 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 15 DAY), 210, 2.00, 420.00),
(
1,
'home-office-como-se-organizar-melhor.jpg',
'Home-office: como se organizar melhor',
'home-office-como-se-organizar-melhor',
'# Cava amor fuit armis et quoque

## Praeterque favet Ausoniis capillis colla

Lorem markdownum oculi. Letum hac has cera Calliroe, manu liceat, amans nata
quibus? Gravis occasus sorores *refundit Ascalaphus* in cunctis fugientem velles
colla novem Leucosiamque vagus texerat Troianae! Seriphi telum Erymantho nec
Iuppiter Iove capulo Venerem.

> [Tegit](http://sumite.org/gaudiasilvis.html) Hypanis et digna nec dicite
> iaculum, materno, brevissima quam. Prius ululare colloquium hinc ille vulnere
> quaerit sanguine te pontus passim roganda; dum tamen gloria,
> [indicio](http://www.pinu.io/)! Nulla ipse erit orbem non vidit est per passa
> virtute telo *ubi*.

## Omne quod satiatur simillimus serae

Illa pecudesque corripe animam at metu, fidumque diffugiunt hunc moderatius
vitae, effetus quis. Gratia suspicor busta.

    drive_multithreading = snow(url / 1 * disk);
    thumbnail(drive_twain_ip - linux_swipe_vpi, dhcp_worm_link);
    if (client(refresh + extensionAd)) {
        vga.lan_socket_page = biometricsParityOdbc(server_wimax);
        botnetDataUri = lossless;
        socialMemory(acl, installer_zip_engine + 4, driveReality);
    } else {
        dv_inkjet.hfs_ugc(gate);
        cyberspacePeoplewareAsp.url_bitmap = 4;
    }

## Tibi saucia

Omni mora tunc vetitorum aut haec sceleratus etiam incurrite nam, damus Adoni
domum, obnoxius. Multiplicique iungit suspirat deflevit etiam deiecto fecisse
frugis Titania [aere](http://mora.org/haerentia.aspx), lancea ipse! Volant cum
iustis, feres expers inhibere in [verbis natura in](http://videritcristis.org/)
pectus descendunt silices. Posita semine orabam prius omnia Cnosiacaeque parat
vultumque habenas dignissima adeunt cum suis? Inducere nova et tricuspide hac
flexerat adit sollemni Charybdis revelli fuso cedere visa vena amanti aliter.

1. Ingentes margine sanguine Argum
2. Qua inimica corpus formasque habebat
3. Nomen subito

Fletu rostro. Fudi [trium](http://dixit.io/denique-nato.html). Quoque putabant
gelidoque solidumve te corporis, tot non quisquam rapitur cursus reclinis
altaque, repulsus abactas crescere: detque! Duo vires deus media te tibi fratrem
sparserat flagrantem flecti messes, in tectus lenta Ericthonio illo reverentia
Athin.',
7, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 40 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 40 DAY), 252, 2.00, 504.00),
(
1,
'integrando-api-do-google-maps-react.jpg',
'Integrando a API do Google Maps ao React',
'integrando-api-do-google-maps-react',
'# Peto nec praerupit hic

## Consistere erat transitque cives petita numine nubes

Lorem markdownum si modo stipata! Corpus natos, una ille ira Dianae: Lyrcea
candentia, [alii](http://modocur.net/increvit-vitium) orat in vincar crimina
feritate. Nobis *audire* nomen Troiaeque subeunt! Fortunata
[moras](http://tenentem.org/).

> Achille [ab](http://iamque-prosiliunt.io/aptato) animavit, potentia et refers
> satum clamore: Eumenidum dura cui annum. Paludes omnis via solacia ait
> [collige ferens](http://neveet.org/opifer)!

Cetera ministro dat [securus putet](http://illa.io/suoque.html): cui *me* nec
mea. Quia forcipe pariter exanimi adversaque omnes **in grandia mensam** unusque
Latinas: ille? Et avidam subiectis dotalem. O *dubium cucurri caelo* nunc, nec:
preces remorata fluctus ira **tanges**, nitidum est haerentes cupidine
auctoremque.

## Cum odium templis pectore isdem per dat

Dextra illud si totam fatemur subiecit, suae insuperabile navigii potest pruinas
latarumque. Cito stricto et natura fumantia terras querellae facit incaluit et
nolet, puniceo. Nat putria vates litore apte arcanis vinoque altera et iungitur?

    mediaPixelMinisite(uatRtf(wrap, duplexPpgaDonationware, markup), 2);
    tutorial_megabyte *= -1;
    bluetoothExternal = bezelSystemTransfer;
    tft_wildcard_handle *= of.mca.character_processor_microphone(page_node -
            frame(copy_base, vpi_shift), ide_day_bus(160605, surfaceRaster,
            tableJava) + -5, rfid_xp(dll_torrent(koffice, jsfKeySoft)));

## Est minus

Paesti [praemia](http://bracchia-effugiunt.com/videtur) temperiemque iubet
contrahitur de Ennomon senserunt natos, concitat facta posse. Dixit contrahitur
et mihi ferebam, satis quod **natas omnia** indigenae patulis.

1. Nubibus rigorem
2. Unco flumina interea factum praebuerat se iram
3. Sive tum
4. Mos armenti sitae exspiravit domino
5. Est saepe carinae cunctos mala Antaeo perque

Subdita occuluere, **fluctus**. Paras in [alto](http://www.et.net/aevi-velle)
mirabere *sedet* cumque est Euboicas quin: quae. Angue proelia exstant surgere
adhuc velut quoniam et dicta piacula dabitur manus ignibus plausit. In tellus
talibus semesarumque actis mi reddita nostra paulatimque reclusa diversa; sacra
tibi, mea.

Nos clara me at vulnus in falsi censu boves innixusque nodum. Neve gravi, arcus
tecti pronepos nimia siquid vindicat meorum ambae.',
6, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 31 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 31 DAY), 266, 5.50, 1463.00),
(
1,
'javascript-e-muito-mais-do-que-voce-pensa.jpg',
'O JavaScript é muito mais do que você pensa',
'javascript-e-muito-mais-do-que-voce-pensa',
'# Septemplice Coroneus prosiliunt eruerit nostras petatur maneant

## Tuta Ophionides odere taceam

Lorem markdownum tuens spatiantur primo animos sinistrae et videndo troia hoc.
Excipit illo suo scintilla feras illae quondam, mihi fonti, metues. Vidit nec
animo imagine semper.

    oem_markup_ipad = google_access_vector;
    file_computer_optical = icmpLogic + ospf_dns(29);
    smart.bar = dbms;
    var memory_keyboard = data;

Semper in quam, Peliden et solum in armenti petiit, sepulcris defecerat
tenetque, aut duae texit aera. Stare **pacisci non** dent umquam *dicta est*,
iam. Mea super sibi suppressa agmen poposcerat coniunx regna confusa metuit
patriaeque et modo imagine usque. Bello plura molliter dubitare *et haut aratri*
vincetur deus auras commoda iacent Limyren.

## Humo mox modo quos adflata aram

Cyllene quaecumque [adflabat](http://lupum-ad.org/casa.html) heros. Illam
meritoque quoque inmeritae nudo [stupet](http://moveri.net/) talia natus mersa,
invidiosa utrumque dedecus praestant posita nimium Cereris vulnera et!

Europa vaticinor. Illa Euagrus, erat luctor vicerat dictu admovique signa
soleant aut tantum quod lustro huic: qualem.

Tanta de possit! Trado cognoscit Lucinam bucera erat quamquam prementem modo
conplexibus etiam cauda errabat. Qua sed aethera, iterum?

Rediret per minus cepisse contenta Ulixes et tum natos, dotalem in epulae, ut
sude, cyclopum. Adfectu [hasta amor vidi](http://www.sine.org/) ora digna
progenitore magno tu dum pellis. Fecit alite carina relevare. Altera figuras,
hinc manu Ceyx ter afflatibus exire fuerat; sine arma sol, formidine, est
pariter. Ille invaserat.',
6, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 61 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 61 DAY), 0.50, 500, 250.50),
(
1,
'java-vscode-e-produtivo.jpg',
'Java + VSCode = produtividade?',
'java-vscode-e-produtivo',
'# Cava amor fuit armis et quoque

## Praeterque favet Ausoniis capillis colla

Lorem markdownum oculi. Letum hac has cera Calliroe, manu liceat, amans nata
quibus? Gravis occasus sorores *refundit Ascalaphus* in cunctis fugientem velles
colla novem Leucosiamque vagus texerat Troianae! Seriphi telum Erymantho nec
Iuppiter Iove capulo Venerem.

> [Tegit](http://sumite.org/gaudiasilvis.html) Hypanis et digna nec dicite
> iaculum, materno, brevissima quam. Prius ululare colloquium hinc ille vulnere
> quaerit sanguine te pontus passim roganda; dum tamen gloria,
> [indicio](http://www.pinu.io/)! Nulla ipse erit orbem non vidit est per passa
> virtute telo *ubi*.

## Omne quod satiatur simillimus serae

Illa pecudesque corripe animam at metu, fidumque diffugiunt hunc moderatius
vitae, effetus quis. Gratia suspicor busta.

    drive_multithreading = snow(url / 1 * disk);
    thumbnail(drive_twain_ip - linux_swipe_vpi, dhcp_worm_link);
    if (client(refresh + extensionAd)) {
        vga.lan_socket_page = biometricsParityOdbc(server_wimax);
        botnetDataUri = lossless;
        socialMemory(acl, installer_zip_engine + 4, driveReality);
    } else {
        dv_inkjet.hfs_ugc(gate);
        cyberspacePeoplewareAsp.url_bitmap = 4;
    }

## Tibi saucia

Omni mora tunc vetitorum aut haec sceleratus etiam incurrite nam, damus Adoni
domum, obnoxius. Multiplicique iungit suspirat deflevit etiam deiecto fecisse
frugis Titania [aere](http://mora.org/haerentia.aspx), lancea ipse! Volant cum
iustis, feres expers inhibere in [verbis natura in](http://videritcristis.org/)
pectus descendunt silices. Posita semine orabam prius omnia Cnosiacaeque parat
vultumque habenas dignissima adeunt cum suis? Inducere nova et tricuspide hac
flexerat adit sollemni Charybdis revelli fuso cedere visa vena amanti aliter.

1. Ingentes margine sanguine Argum
2. Qua inimica corpus formasque habebat
3. Nomen subito

Fletu rostro. Fudi [trium](http://dixit.io/denique-nato.html). Quoque putabant
gelidoque solidumve te corporis, tot non quisquam rapitur cursus reclinis
altaque, repulsus abactas crescere: detque! Duo vires deus media te tibi fratrem
sparserat flagrantem flecti messes, in tectus lenta Ericthonio illo reverentia
Athin.',
6, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 71 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 71 DAY), 0.50, 500, 250.50),
(
1,
'monitorando-clusters-node-js-com-pm2.jpg',
'Monitorando Clusters Node.js com PM2',
'monitorando-clusters-node-js-com-pm2',
'# Peto nec praerupit hic

## Consistere erat transitque cives petita numine nubes

Lorem markdownum si modo stipata! Corpus natos, una ille ira Dianae: Lyrcea
candentia, [alii](http://modocur.net/increvit-vitium) orat in vincar crimina
feritate. Nobis *audire* nomen Troiaeque subeunt! Fortunata
[moras](http://tenentem.org/).

> Achille [ab](http://iamque-prosiliunt.io/aptato) animavit, potentia et refers
> satum clamore: Eumenidum dura cui annum. Paludes omnis via solacia ait
> [collige ferens](http://neveet.org/opifer)!

Cetera ministro dat [securus putet](http://illa.io/suoque.html): cui *me* nec
mea. Quia forcipe pariter exanimi adversaque omnes **in grandia mensam** unusque
Latinas: ille? Et avidam subiectis dotalem. O *dubium cucurri caelo* nunc, nec:
preces remorata fluctus ira **tanges**, nitidum est haerentes cupidine
auctoremque.

## Cum odium templis pectore isdem per dat

Dextra illud si totam fatemur subiecit, suae insuperabile navigii potest pruinas
latarumque. Cito stricto et natura fumantia terras querellae facit incaluit et
nolet, puniceo. Nat putria vates litore apte arcanis vinoque altera et iungitur?

    mediaPixelMinisite(uatRtf(wrap, duplexPpgaDonationware, markup), 2);
    tutorial_megabyte *= -1;
    bluetoothExternal = bezelSystemTransfer;
    tft_wildcard_handle *= of.mca.character_processor_microphone(page_node -
            frame(copy_base, vpi_shift), ide_day_bus(160605, surfaceRaster,
            tableJava) + -5, rfid_xp(dll_torrent(koffice, jsfKeySoft)));

## Est minus

Paesti [praemia](http://bracchia-effugiunt.com/videtur) temperiemque iubet
contrahitur de Ennomon senserunt natos, concitat facta posse. Dixit contrahitur
et mihi ferebam, satis quod **natas omnia** indigenae patulis.

1. Nubibus rigorem
2. Unco flumina interea factum praebuerat se iram
3. Sive tum
4. Mos armenti sitae exspiravit domino
5. Est saepe carinae cunctos mala Antaeo perque

Subdita occuluere, **fluctus**. Paras in [alto](http://www.et.net/aevi-velle)
mirabere *sedet* cumque est Euboicas quin: quae. Angue proelia exstant surgere
adhuc velut quoniam et dicta piacula dabitur manus ignibus plausit. In tellus
talibus semesarumque actis mi reddita nostra paulatimque reclusa diversa; sacra
tibi, mea.

Nos clara me at vulnus in falsi censu boves innixusque nodum. Neve gravi, arcus
tecti pronepos nimia siquid vindicat meorum ambae.',
7, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 121 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 121 DAY), 1.50, 10000, 550.50),
(
1,
'por-detras-do-codigo.jpg',
'Por detrás do código',
'por-detras-do-codigo',
'# Septemplice Coroneus prosiliunt eruerit nostras petatur maneant

## Tuta Ophionides odere taceam

Lorem markdownum tuens spatiantur primo animos sinistrae et videndo troia hoc.
Excipit illo suo scintilla feras illae quondam, mihi fonti, metues. Vidit nec
animo imagine semper.

    oem_markup_ipad = google_access_vector;
    file_computer_optical = icmpLogic + ospf_dns(29);
    smart.bar = dbms;
    var memory_keyboard = data;

Semper in quam, Peliden et solum in armenti petiit, sepulcris defecerat
tenetque, aut duae texit aera. Stare **pacisci non** dent umquam *dicta est*,
iam. Mea super sibi suppressa agmen poposcerat coniunx regna confusa metuit
patriaeque et modo imagine usque. Bello plura molliter dubitare *et haut aratri*
vincetur deus auras commoda iacent Limyren.

## Humo mox modo quos adflata aram

Cyllene quaecumque [adflabat](http://lupum-ad.org/casa.html) heros. Illam
meritoque quoque inmeritae nudo [stupet](http://moveri.net/) talia natus mersa,
invidiosa utrumque dedecus praestant posita nimium Cereris vulnera et!

Europa vaticinor. Illa Euagrus, erat luctor vicerat dictu admovique signa
soleant aut tantum quod lustro huic: qualem.

Tanta de possit! Trado cognoscit Lucinam bucera erat quamquam prementem modo
conplexibus etiam cauda errabat. Qua sed aethera, iterum?

Rediret per minus cepisse contenta Ulixes et tum natos, dotalem in epulae, ut
sude, cyclopum. Adfectu [hasta amor vidi](http://www.sine.org/) ora digna
progenitore magno tu dum pellis. Fecit alite carina relevare. Altera figuras,
hinc manu Ceyx ter afflatibus exire fuerat; sine arma sol, formidine, est
pariter. Ille invaserat.',
7, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 156 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 156 DAY), 0.50, 500, 250.50),
(
1,
'teclado-faz-diferenca-para-programador.jpg',
'Teclado faz diferença para programador?',
'teclado-faz-diferenca-para-programador',
'# Cava amor fuit armis et quoque

## Praeterque favet Ausoniis capillis colla

Lorem markdownum oculi. Letum hac has cera Calliroe, manu liceat, amans nata
quibus? Gravis occasus sorores *refundit Ascalaphus* in cunctis fugientem velles
colla novem Leucosiamque vagus texerat Troianae! Seriphi telum Erymantho nec
Iuppiter Iove capulo Venerem.

> [Tegit](http://sumite.org/gaudiasilvis.html) Hypanis et digna nec dicite
> iaculum, materno, brevissima quam. Prius ululare colloquium hinc ille vulnere
> quaerit sanguine te pontus passim roganda; dum tamen gloria,
> [indicio](http://www.pinu.io/)! Nulla ipse erit orbem non vidit est per passa
> virtute telo *ubi*.

## Omne quod satiatur simillimus serae

Illa pecudesque corripe animam at metu, fidumque diffugiunt hunc moderatius
vitae, effetus quis. Gratia suspicor busta.

    drive_multithreading = snow(url / 1 * disk);
    thumbnail(drive_twain_ip - linux_swipe_vpi, dhcp_worm_link);
    if (client(refresh + extensionAd)) {
        vga.lan_socket_page = biometricsParityOdbc(server_wimax);
        botnetDataUri = lossless;
        socialMemory(acl, installer_zip_engine + 4, driveReality);
    } else {
        dv_inkjet.hfs_ugc(gate);
        cyberspacePeoplewareAsp.url_bitmap = 4;
    }

## Tibi saucia

Omni mora tunc vetitorum aut haec sceleratus etiam incurrite nam, damus Adoni
domum, obnoxius. Multiplicique iungit suspirat deflevit etiam deiecto fecisse
frugis Titania [aere](http://mora.org/haerentia.aspx), lancea ipse! Volant cum
iustis, feres expers inhibere in [verbis natura in](http://videritcristis.org/)
pectus descendunt silices. Posita semine orabam prius omnia Cnosiacaeque parat
vultumque habenas dignissima adeunt cum suis? Inducere nova et tricuspide hac
flexerat adit sollemni Charybdis revelli fuso cedere visa vena amanti aliter.

1. Ingentes margine sanguine Argum
2. Qua inimica corpus formasque habebat
3. Nomen subito

Fletu rostro. Fudi [trium](http://dixit.io/denique-nato.html). Quoque putabant
gelidoque solidumve te corporis, tot non quisquam rapitur cursus reclinis
altaque, repulsus abactas crescere: detque! Duo vires deus media te tibi fratrem
sparserat flagrantem flecti messes, in tectus lenta Ericthonio illo reverentia
Athin.',
6, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 123 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 123 DAY), 0.50, 500, 250.50),
(
1,
'testes-calcule-se-vale-a-pena-ou-nao.jpg',
'Testes: calcule se vale a pena ou não',
'testes-calcule-se-vale-a-pena-ou-nao',
'# Peto nec praerupit hic

## Consistere erat transitque cives petita numine nubes

Lorem markdownum si modo stipata! Corpus natos, una ille ira Dianae: Lyrcea
candentia, [alii](http://modocur.net/increvit-vitium) orat in vincar crimina
feritate. Nobis *audire* nomen Troiaeque subeunt! Fortunata
[moras](http://tenentem.org/).

> Achille [ab](http://iamque-prosiliunt.io/aptato) animavit, potentia et refers
> satum clamore: Eumenidum dura cui annum. Paludes omnis via solacia ait
> [collige ferens](http://neveet.org/opifer)!

Cetera ministro dat [securus putet](http://illa.io/suoque.html): cui *me* nec
mea. Quia forcipe pariter exanimi adversaque omnes **in grandia mensam** unusque
Latinas: ille? Et avidam subiectis dotalem. O *dubium cucurri caelo* nunc, nec:
preces remorata fluctus ira **tanges**, nitidum est haerentes cupidine
auctoremque.

## Cum odium templis pectore isdem per dat

Dextra illud si totam fatemur subiecit, suae insuperabile navigii potest pruinas
latarumque. Cito stricto et natura fumantia terras querellae facit incaluit et
nolet, puniceo. Nat putria vates litore apte arcanis vinoque altera et iungitur?

    mediaPixelMinisite(uatRtf(wrap, duplexPpgaDonationware, markup), 2);
    tutorial_megabyte *= -1;
    bluetoothExternal = bezelSystemTransfer;
    tft_wildcard_handle *= of.mca.character_processor_microphone(page_node -
            frame(copy_base, vpi_shift), ide_day_bus(160605, surfaceRaster,
            tableJava) + -5, rfid_xp(dll_torrent(koffice, jsfKeySoft)));

## Est minus

Paesti [praemia](http://bracchia-effugiunt.com/videtur) temperiemque iubet
contrahitur de Ennomon senserunt natos, concitat facta posse. Dixit contrahitur
et mihi ferebam, satis quod **natas omnia** indigenae patulis.

1. Nubibus rigorem
2. Unco flumina interea factum praebuerat se iram
3. Sive tum
4. Mos armenti sitae exspiravit domino
5. Est saepe carinae cunctos mala Antaeo perque

Subdita occuluere, **fluctus**. Paras in [alto](http://www.et.net/aevi-velle)
mirabere *sedet* cumque est Euboicas quin: quae. Angue proelia exstant surgere
adhuc velut quoniam et dicta piacula dabitur manus ignibus plausit. In tellus
talibus semesarumque actis mi reddita nostra paulatimque reclusa diversa; sacra
tibi, mea.

Nos clara me at vulnus in falsi censu boves innixusque nodum. Neve gravi, arcus
tecti pronepos nimia siquid vindicat meorum ambae.',
6, false, DATE_SUB(UTC_TIMESTAMP, INTERVAL 126 DAY), DATE_SUB(UTC_TIMESTAMP, INTERVAL 121 DAY), 1.50, 10000, 550.50);




UPDATE post
SET earnings_words=266, earnings_price_per_word=5.50, earnings_total_amount=1463.00
WHERE id=13;
UPDATE post
SET earnings_words=252, earnings_price_per_word=5.50, earnings_total_amount=1386.00
WHERE id=9;
UPDATE post
SET earnings_words=210, earnings_price_per_word=5.50, earnings_total_amount=1155.00
WHERE id=8;
UPDATE post
SET earnings_words=266, earnings_price_per_word=5.50, earnings_total_amount=1463.00
WHERE id=7;
UPDATE post
SET earnings_words=252, earnings_price_per_word=2.00, earnings_total_amount=504.00
WHERE id=6;
UPDATE post
SET earnings_words=252, earnings_price_per_word=5.50, earnings_total_amount=1386.00
WHERE id=15;
UPDATE post
SET earnings_words=210, earnings_price_per_word=2.00, earnings_total_amount=420.00
WHERE id=5;
UPDATE post
SET earnings_words=266, earnings_price_per_word=2.00, earnings_total_amount=532.00
WHERE id=4;
UPDATE post
SET earnings_words=252, earnings_price_per_word=5.50, earnings_total_amount=1386.00
WHERE id=3;
UPDATE post
SET earnings_words=210, earnings_price_per_word=5.50, earnings_total_amount=1155.00
WHERE id=14;
UPDATE post
SET earnings_words=210, earnings_price_per_word=5.50, earnings_total_amount=1155.00
WHERE id=2;
UPDATE post
SET earnings_words=266, earnings_price_per_word=5.50, earnings_total_amount=1463.00
WHERE id=1;
UPDATE post
SET earnings_words=252, earnings_price_per_word=2.00, earnings_total_amount=504.00
WHERE id=12;
UPDATE post
SET earnings_words=266, earnings_price_per_word=2.00, earnings_total_amount=532.00
WHERE id=10;
UPDATE post
SET earnings_words=210, earnings_price_per_word=2.00, earnings_total_amount=420.00
WHERE id=11;
UPDATE post
SET earnings_words=266, earnings_price_per_word=2.00, earnings_total_amount=532.00
WHERE id=16;
UPDATE post
SET earnings_words=210, earnings_price_per_word=2.00, earnings_total_amount=420.00
WHERE id=17;
UPDATE post
SET earnings_words=252, earnings_price_per_word=2.00, earnings_total_amount=504.00
WHERE id=18;


INSERT INTO tag (post_id, name) VALUES
(1, 'Java'),
(1, 'Javascript'),
(1, 'ReactJS'),
(2, 'Java'),
(3, 'Java'),
(3, 'PHP'),
(4, 'Java'),
(5, 'Java'),
(5, 'Node'),
(5, 'Django'),
(6, 'Java'),
(6, 'Spring'),
(7, 'Java'),
(7, 'Javascript'),
(8, 'Java'),
(8, 'Node'),
(9, 'Java'),
(9, 'Node'),
(10, 'Java'),
(11, 'Java'),
(11, 'Python'),
(11, 'Django'),
(11, 'Flask'),
(12, 'Java'),
(13, 'Java'),
(13, 'Javascript');

INSERT INTO payment (id, grand_total_amount, earnings_words, earnings_total_amount, period_starts_on, period_ends_on, payee_id, approved_by_id, created_by_id, scheduled_to, bank_account_bank_code, bank_account_agency, bank_account_number, bank_account_digit, bank_account_type, created_at, updated_at, approved_at, updated_by_id) VALUES
(1, 604.00, 252, 504.00,   DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), INTERVAL 1 DAY), DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), 7, NULL, 1, DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), INTERVAL 5 DAY), '341', '2347', '1065160', '8', 'SAVING', DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), INTERVAL 1 DAY), NULL, 1),
(2, 1563.00, 266, 1463.00, DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), INTERVAL 1 DAY), DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), 6, 1, 1,    DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), INTERVAL 5 DAY), '341', '8907', '09121', '2', 'SAVING',   DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), INTERVAL 1 DAY), 1),
(3, 2641.00, 462, 2541.00, DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 3 MONTH), INTERVAL 1 DAY), DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), 6, 1, 1,    DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), INTERVAL 5 DAY), '341', '8907', '09121', '2', 'SAVING',   DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), INTERVAL 1 DAY), 1),
(4, 632.00, 266, 532.00,   DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 1 DAY), DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), 7, 1, 1,    DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 5 DAY), '341', '2347', '1065160', '8', 'SAVING', DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 1 DAY), 1),
(5, 2067.00, 518, 1967.00, DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 1 DAY), DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), 6, 1, 1,    DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 5 DAY), '341', '8907', '09121', '2', 'SAVING',   DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 1 DAY), 1),
(6, 520.00, 210, 420.00,   DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 6 MONTH), INTERVAL 1 DAY), DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), 7, 1, 1,    DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 5 DAY), '341', '2347', '1065160', '8', 'SAVING', DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 1 DAY), DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 1 DAY), 1);

INSERT INTO payment_posts (payment_id,post_id) VALUES 
(1,6),
(2,7),
(3,8),
(3,9),
(4,10),
(6,11),
(5,12),
(5,13);

INSERT INTO payment_bonus (payment_id,title,amount) VALUES 
(1,'Qualidade',100.00),
(2,'Metas Atingidas',100.00),
(3,'Conteúdo',100.00),
(4,'Metas Atingidas',100.00),
(5,'Metas Atingidas',100.00),
(6,'Metas Atingidas',100.00);


UPDATE post SET paid = TRUE WHERE id IN (6, 7, 8, 9, 10, 11, 12, 13);

INSERT INTO alganews.cash_flow_entry (transacted_on,`type`,description,system_generated,category_id,amount,created_by_id,created_at,updated_at,updated_by_id) VALUES 
(DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), INTERVAL 5 DAY),'EXPENSE', CONCAT('Posts de Débora Mendonça de ', DATE_FORMAT(DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), INTERVAL 1 DAY), '%d/%m/%Y'), ' a ', DATE_FORMAT(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), '%d/%m/%Y') ),      1,1,1563.00,1,DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), INTERVAL 1 DAY),DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 1 MONTH), INTERVAL 1 DAY), 1),
(DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), INTERVAL 5 DAY),'EXPENSE', CONCAT('Posts de Débora Mendonça de ', DATE_FORMAT(DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 3 MONTH), INTERVAL 1 DAY), '%d/%m/%Y'), ' a ', DATE_FORMAT(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), '%d/%m/%Y') ),      1,1,2641.00,1,DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), INTERVAL 1 DAY),DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 2 MONTH), INTERVAL 1 DAY), 1),
(DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 5 DAY),'EXPENSE', CONCAT('Posts de Carlos Lima de ',     DATE_FORMAT(DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 1 DAY), '%d/%m/%Y'), ' a ', DATE_FORMAT(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), '%d/%m/%Y') ),      1,1,632.00,1, DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 1 DAY),DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 1 DAY), 1),
(DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 5 DAY),'EXPENSE', CONCAT('Posts de Débora Mendonça de ', DATE_FORMAT(DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 1 DAY), '%d/%m/%Y'), ' a ', DATE_FORMAT(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), '%d/%m/%Y') ),      1,1,2067.00,1,DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 1 DAY),DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 4 MONTH), INTERVAL 1 DAY), 1),
(DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 5 DAY),'EXPENSE', CONCAT('Posts de Carlos Lima de ',     DATE_FORMAT(DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 6 MONTH), INTERVAL 1 DAY), '%d/%m/%Y'), ' a ', DATE_FORMAT(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), '%d/%m/%Y') ),      1,1,520.00,1, DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 1 DAY),DATE_ADD(DATE_SUB(LAST_DAY(CURRENT_DATE()), INTERVAL 5 MONTH), INTERVAL 1 DAY), 1);