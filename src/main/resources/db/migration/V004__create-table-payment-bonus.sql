create table payment (
	id bigint not null auto_increment,
	grand_total_amount decimal(10,2) not null,
	earnings_words int not null,
	earnings_total_amount decimal(10,2) not null,
	period_starts_on date not null,
	period_ends_on date not null,
	payee_id bigint not null,
	approved_by_id bigint,
	created_by_id bigint not null,
	scheduled_to date not null,
	bank_account_bank_code varchar (255) not null,
	bank_account_agency varchar (255) not null,
	bank_account_number varchar (255) not null,
	bank_account_digit varchar (255) not null,
	bank_account_type varchar (255) not null,
	created_at datetime not null,
	updated_at datetime not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

alter table post add column payment_id bigint null;

alter table payment add constraint fk_payment_user_payee
foreign key (payee_id) references `user` (id);

alter table payment add constraint fk_payment_aproved_by_user
foreign key (approved_by_id) references `user` (id);

alter table payment add constraint fk_payment_created_by_user
foreign key (created_by_id) references `user` (id);

create table bonus (
	payment_id bigint not null,
	title varchar(255) not null,
	amount decimal(10,2) not null
) engine=InnoDB default charset=utf8;

alter table bonus add constraint fk_bonus_payment
foreign key (payment_id) references payment (id);
