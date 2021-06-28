alter table entry_category add column updated_by_id bigint not null;


alter table entry_category add constraint fk_entry_category_updated_by_user
foreign key (updated_by_id) references `user` (id);

alter table entry_category add column created_by_id bigint not null;
alter table entry_category add constraint fk_entry_category_created_by_user
foreign key (created_by_id) references `user` (id);

alter table cash_flow_entry add column updated_by_id bigint not null;
alter table cash_flow_entry add constraint fk_cash_flow_entry_updated_by_user
foreign key (updated_by_id) references `user` (id);

alter table payment add column updated_by_id bigint not null;
alter table payment add constraint fk_payment_updated_by_user
foreign key (updated_by_id) references `user` (id);

alter table post add column updated_by_id bigint not null;
alter table post add constraint fk_post_updated_by_user
foreign key (updated_by_id) references `user` (id);

alter table `user` add column updated_by_id bigint null;
alter table `user` add constraint fk_user_updated_by_user
foreign key (updated_by_id) references `user` (id);

alter table `user` add column created_by_id bigint null;
alter table `user` add constraint fk_user_created_by_user
foreign key (created_by_id) references `user` (id);