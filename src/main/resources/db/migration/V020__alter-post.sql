update post set slug = concat(slug,'-',left(md5(rand()), 8));
alter table post add constraint un_post_slug unique key (slug);