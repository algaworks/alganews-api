create table post (
	id bigint not null auto_increment,
	title varchar(255) not null,
	slug varchar(255) not null,
	image varchar(255) null,
	body text,
	editor_id bigint not null,
	deleted boolean not null,
	created_at datetime not null,
	updated_at datetime not null,
	earnings_words integer not null,
	earnings_price_per_word decimal(10,2) not null,
	earnings_total_amount decimal(10,2) not null,
	primary key (id)
) engine=innodb default charset=utf8;

alter table post add constraint fk_post_editor
foreign key (editor_id) references `user` (id);

create table tag (
	post_id bigint not null,
	tag_name varchar(255) not null
) engine=InnoDB default charset=utf8;

alter table tag add constraint fk_tag_post
foreign key (post_id) references post (id);