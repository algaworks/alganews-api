create table entry_category (
	id bigint not null auto_increment,
	name varchar(255) not null,
    created_at datetime not null,
    updated_at datetime not null,
	primary key (id)
) engine=InnoDB default charset=utf8;

create table cash_flow_entry (
	id bigint not null auto_increment,
	transacted_on date not null,
	type varchar(30) not null,
	description varchar(255) not null,
    system_generated boolean not null default false,
    category_id bigint not null,
    amount decimal(10,2) not null,
    created_by_id bigint not null,
    created_at datetime not null,
    updated_at datetime not null,
	primary key (id)
) engine=innodb default charset=utf8;

alter table cash_flow_entry add constraint fk_cash_flow_entry_category
foreign key (category_id) references entry_category (id);

alter table cash_flow_entry add constraint fk_cash_flow_entry_created_by_user
foreign key (created_by_id) references `user` (id);