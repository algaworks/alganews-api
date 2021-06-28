create table `user` (
	id bigint not null auto_increment,
	name varchar(80) not null,
	email varchar(255) not null,
	avatar varchar(255),
	bio varchar(255) not null,
	birthdate date not null,
	phone varchar(30),
	`password` varchar(255) not null,
	taxpayer_id varchar(80) null,
	price_per_word decimal(10,2) not null,
	`role` varchar(20) not null,
	location_country varchar(255) not null,
	location_state varchar(255) not null,
	location_city varchar(255) not null,
	bank_account_bank_code varchar (255) not null,
	bank_account_agency varchar (255) not null,
	bank_account_number varchar (255) not null,
	bank_account_digit varchar (255) not null,
	bank_account_type varchar (255) not null,
	active boolean not null,
	created_at datetime not null,
	updated_at datetime not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table skill (
	id bigint not null auto_increment,
	name varchar(80) not null,
	percentage int not null,
	user_id bigint not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

alter table skill add constraint fk_skill_user
foreign key (user_id) references `user` (id);


