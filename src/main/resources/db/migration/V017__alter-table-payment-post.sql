alter table post add paid bool default false not null;

create table payment_posts (
	payment_id bigint not null,
	post_id bigint not null,
	primary key (payment_id, post_id)
) engine=innodb default charset=utf8;

alter table payment_posts add constraint fk_payment_posts_payment
foreign key (payment_id) references payment (id);

alter table payment_posts add constraint fk_payment_posts_post
foreign key (post_id) references post (id);

alter table payment_posts add constraint un_payment_posts_post unique key (post_id);