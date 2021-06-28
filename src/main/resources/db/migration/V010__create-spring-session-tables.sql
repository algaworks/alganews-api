create table spring_session (
	primary_id char(36) not null,
	session_id char(36) not null,
	creation_time bigint not null,
	last_access_time bigint not null,
	max_inactive_interval int not null,
	expiry_time bigint not null,
	principal_name varchar(100),
	constraint spring_session_pk primary key (primary_id)
) engine=innodb row_format=dynamic;

create unique index spring_session_ix1 on spring_session (session_id);
create index spring_session_ix2 on spring_session (expiry_time);
create index spring_session_ix3 on spring_session (principal_name);

create table spring_session_attributes (
	session_primary_id char(36) not null,
	attribute_name varchar(200) not null,
	attribute_bytes blob not null,
	constraint spring_session_attributes_pk primary key (session_primary_id, attribute_name),
	constraint spring_session_attributes_fk foreign key (session_primary_id) references spring_session(primary_id) on delete cascade
) engine=innodb row_format=dynamic;