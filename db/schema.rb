create schema s244707;

alter schema s244707 owner to postgres;

create sequence message_id_seq;

alter sequence message_id_seq owner to s244707;

create domain color_value as integer
	constraint color_value_check check ((VALUE >= 0) AND (VALUE <= 255));

alter domain color_value owner to s244707;

create type skin_color as
(
	r color_value,
	g color_value,
	b color_value
);

alter type skin_color owner to s244707;

create domain size_value as real
	constraint size_value_check check (VALUE >= (0)::double precision);

alter domain size_value owner to s244707;

create type size as
(
	len size_value,
	width size_value,
	height size_value
);

alter type size owner to s244707;

create table if not exists information
(
	type varchar(64)
		constraint information_type_check
			check (((type)::text ~~* 'Сенсор'::text) OR ((type)::text ~~* 'Механическая деталь'::text) OR ((type)::text ~~* 'Мотор'::text) OR ((type)::text ~~* 'Платформа'::text) OR ((type)::text ~~* 'Контроллер'::text) OR ((type)::text ~~* 'Инструмент'::text) OR ((type)::text ~~* 'Прочие ресурсы'::text)),
	model varchar(256),
	name varchar(256) not null,
	price real
		constraint information_price_check
			check (price >= (0)::double precision),
	creation_date timestamp,
	provider varchar(256),
	description text,
	id serial not null
		constraint information_id_pk
			primary key,
	amount integer
);

alter table information owner to s244707;

create table if not exists mechanic_details
(
	material varchar(256),
	id serial not null
		constraint mechanicdetalis_id_pk
			primary key
		constraint mechanicdetalis_information_id_fk
			references information
);

alter table mechanic_details owner to s244707;

create unique index if not exists mechanicdetalis_id_uindex
	on mechanic_details (id);

create unique index if not exists information_id_uindex
	on information (id);

create table if not exists users
(
	id serial not null
		constraint account_pkey
			primary key,
	login varchar(256) not null,
	password varchar(256) not null,
	telegram_id varchar(256)
);

alter table users owner to s244707;

create table if not exists orders
(
	id serial not null
		constraint orders_pkey
			primary key,
	id_user integer
		constraint orders_user_id_fk
			references users,
	date timestamp,
	address varchar(256) not null
);

alter table orders owner to s244707;

create index if not exists orders_id_customer_index
	on orders (id_user);

create table if not exists products_orders
(
	id_product integer
		constraint products_order_information_id_fk
			references information,
	id_order integer
		constraint order_products_orders_id_fk
			references orders,
	amount integer not null
		constraint products_order_amount_check
			check (amount > 0),
	unit_price real not null
		constraint products_order_unit_price_check
			check (unit_price >= (0)::double precision),
	id serial not null
		constraint products_orders_pk
			primary key
);

alter table products_orders owner to s244707;

create table if not exists products_users
(
	id_product integer
		constraint products_users_information_id_fk
			references information,
	amount integer not null
		constraint products_cart_amount_check
			check (amount > 0),
	id_user integer
		constraint products_cart_customers_id_fk
			references users,
	id serial not null
		constraint products_users_pk
			primary key
);

alter table products_users owner to s244707;

create index if not exists products_users_part_number_index
	on products_users (id_product);

create index if not exists products_users_id_user_index
	on products_users (id_user);

create unique index if not exists account_id_uindex
	on users (id);

create unique index if not exists account_login_uindex
	on users (login);

create table if not exists interfaces
(
	id serial not null
		constraint interface_pkey
			primary key,
	name varchar(64) not null
);

alter table interfaces owner to s244707;

create table if not exists motors
(
	power real
		constraint motors_power_check
			check (power >= (0)::double precision),
	min_voltage real,
	max_voltage real
		constraint motors_maxvoltage_check
			check (maxvoltage >= (0)::double precision),
	id serial not null
		constraint motors_id_pk
			primary key
		constraint motors_information_id_fk
			references information,
	interface_id integer
		constraint motors_interface_id_fk
			references interfaces
);

alter table motors owner to s244707;

create unique index if not exists motors_id_uindex
	on motors (id);

create index if not exists motors_interface_id_index
	on motors (interface_id);

create table if not exists controllers
(
	ram integer
		constraint controllers_ram_check
			check (ram >= 0),
	min_voltage real,
	max_voltage real,
	analog_inputs integer,
	id serial not null
		constraint controllers_id_pk
			primary key
		constraint controllers_information_id_fk
			references information,
	interface_id integer
		constraint controllers_interface_id_fk
			references interfaces
);

alter table controllers owner to s244707;

create table if not exists platforms
(
	controller integer
		constraint platforms_controllers_id_fk
			references controllers,
	min_voltage real,
	max_voltage real
		constraint platforms_max_voltage_check
			check (max_voltage >= (0)::double precision),
	freq real
		constraint platforms_freq_check
			check (freq >= (0)::double precision),
	analog_inputs integer,
	flashmemory integer,
	ram integer
		constraint platforms_ram_check
			check (ram >= 0),
	id serial not null
		constraint platforms_id_pk
			primary key
		constraint platforms_information_id_fk
			references information
);

alter table platforms owner to s244707;

create unique index if not exists platforms_id_uindex
	on platforms (id);

create unique index if not exists controllers_id_uindex
	on controllers (id);

create index if not exists controllers_interface_id_index
	on controllers (interface_id);

create table if not exists sensors
(
	min_voltage real,
	max_voltage real
		constraint sensors_max_voltage_check
			check (max_voltage >= (0)::double precision),
	id serial not null
		constraint sensors_id_pk
			primary key
		constraint sensors_information_id_fk
			references information,
	interface_id integer
		constraint sensors_interface_id_fk
			references interfaces,
	constraint sensors_check
		check (max_voltage >= operating_voltage)
);

alter table sensors owner to s244707;

create unique index if not exists sensors_id_uindex
	on sensors (id);

create index if not exists sensors_interface_id_index
	on sensors (interface_id);

create table if not exists details_interfaces
(
	detail_id integer not null
		constraint interfaces_platforms_id_fk
			references platforms,
	interface_id integer
		constraint platform_interfaces_interface_id_fk
			references interfaces
);

alter table details_interfaces owner to s244707;

create index if not exists details_interfaces_interface_id_index
	on details_interfaces (interface_id);

create unique index if not exists interface_id_uindex
	on interfaces (id);

create unique index if not exists interface_name_uindex
	on interfaces (name);

create table if not exists repositories
(
	id serial not null
		constraint repositories_pkey
			primary key,
	id_user integer
		constraint repositories_id_user_fkey
			references users,
	path varchar(512) not null,
	description text,
	name varchar(256) not null
);

alter table repositories owner to s244707;

create table if not exists stars
(
	id serial not null
		constraint stars_pkey
			primary key,
	id_user integer
		constraint stars_id_user_fkey
			references users,
	id_repository integer
		constraint stars_repositories_id_fk
			references repositories
);

alter table stars owner to s244707;

create or replace function check_item_availability() returns trigger
	language plpgsql
as $$
DECLARE
  has_unavailable_items BOOLEAN;
BEGIN
  has_unavailable_items := (with
    items_available as (
      select p.amount, p.part_number from products p, products_users pc, orders o
      where p.part_number = pc.part_number
    ), items_required as (
      select amount, part_number from products_users where id_user = new.id_customer
    )
select exists(
    select from items_required p
    inner join items_available a on (p.part_number = a.part_number)
    where p.amount > a.amount));

  IF has_unavailable_items
  THEN RAISE EXCEPTION 'Not enough items';
  END IF;
	return new;
END;
$$;

alter function check_item_availability() owner to s244707;

create trigger validate_order
	before insert
	on orders
	for each row
	execute procedure check_item_availability();

create or replace function check_info_has_row() returns trigger
	language plpgsql
as $$
declare
	info_has_id boolean;
begin
	if tg_table_name = 'other_resources' then
		info_has_id := (
			select exists(select from information where type = 'Прочие ресурсы' and new.id=information.id)
		);
	elsif tg_table_name = 'mechanic_details' then
		info_has_id := (
			select exists(select from information where type = 'Механическая деталь' and new.id=information.id)
		);
	elsif tg_table_name = 'sensors' then
		info_has_id := (
			select exists(select from information where type = 'Сенсор' and new.id=information.id)
		);
	elsif tg_table_name = 'controllers' then
		info_has_id := (
			select exists(select from information where type = 'Контроллер' and new.id=information.id)
		);
	elsif tg_table_name = 'motors' then
		info_has_id := (
			select exists(select from information where type = 'Мотор' and new.id=information.id)
		);
	elsif tg_table_name = 'platforms' then
		info_has_id := (
			select exists(select from information where type = 'Платформа' and new.id=information.id)
		);
	end if;

  if not info_has_id
  	then raise exception 'Information has no rofl for you';
  end if;
	return new;
end;
$$;

alter function check_info_has_row() owner to s244707;

create trigger validate_mechanic_detail
	before insert or update
	on mechanic_details
	for each row
	execute procedure check_info_has_row();

create trigger validate_motor
	before insert or update
	on motors
	for each row
	execute procedure check_info_has_row();

create trigger validate_platform
	before insert or update
	on platforms
	for each row
	execute procedure check_info_has_row();

create trigger validate_controller
	before insert or update
	on controllers
	for each row
	execute procedure check_info_has_row();

create trigger validate_sensor
	before insert or update
	on sensors
	for each row
	execute procedure check_info_has_row();

create or replace function get_order(number integer) returns TABLE(name character varying, amount integer, unit_price real, price real)
	language plpgsql
as $$
begin return query select information.name, p_o.amount, p_o.unit_price, (p_o.unit_price*p_o.amount)::real from products_order p_o
left join information on (p_o.part_number = information.id)
where p_o.id_order = number
union all select 'Итог:', sum(p_o.amount)::integer, null, sum(p_o.unit_price*p_o.amount)::real from products_order p_o
left join information on (p_o.part_number = information.id)
where p_o.id_order = number;
end;
$$;

alter function get_order(integer) owner to s244707;

create or replace function get_count_order(id_st integer, d integer) returns integer
	language plpgsql
as $$
declare
	result integer;
begin
select count(id) from orders
where orders.id_store = id_st and extract(dow from date) = d into result;
	return result;
end;
$$;

alter function get_count_order(integer, integer) owner to s244707;

create or replace function is_minimum_of_week(id_st integer, d integer) returns boolean
	language plpgsql
as $$
begin
if (get_count_order(id_st, 0) <= get_count_order(id_st, 1)
					 and get_count_order(id_st, 0) <= get_count_order(id_st, 2)
					 and get_count_order(id_st, 0) <= get_count_order(id_st, 3)
					 and get_count_order(id_st, 0) <= get_count_order(id_st, 4)
					 and get_count_order(id_st, 0) <= get_count_order(id_st, 5)
					 and get_count_order(id_st, 0) <= get_count_order(id_st, 6)) 
then return true;
else return false;
end if;
end;
$$;

alter function is_minimum_of_week(integer, integer) owner to s244707;