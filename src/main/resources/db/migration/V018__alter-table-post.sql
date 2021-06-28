insert into payment_posts (select payment_id, post.id from post where payment_id is not null);
update post set paid = true where payment_id is not null;
alter table post drop column payment_id;