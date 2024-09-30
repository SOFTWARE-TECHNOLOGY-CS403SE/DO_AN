create database advancedRealEstate_db;
drop database advancedRealEstate_db;

create table users(
	id varchar(255) primary key,
    fullname varchar(255),
    email varchar(255),
    password varchar(255),
    gender varchar(255),
    phone varchar(255),
    address varchar(255),
    birthday varchar(255),
    isVerify varchar(255),
    referral_Code varchar(255),
    avatar longblob
);

create table buildings(
	id varchar(255) primary key,
	name varchar(255),
	structure varchar(255),
	level varchar(255),
	area varchar(255),
    type varchar(255),
    description text,
	number_of_basement int,
	image longblob
);

create table maps(
	id varchar(255) primary key,
    map_name varchar(255),
	latitude decimal,
    longitude decimal,
	address varchar(255),
    ward varchar(255),
    district varchar(255),
	direction varchar(255)
);

create table expenses(
	id varchar(255) primary key,
    brokerage_fee decimal,
    service_fee decimal,
    electricity_fee decimal,
    water_fee decimal,
    parking_fee decimal
);

create table rent_buildings(
	id varchar(255) primary key,
    number_month int,
    rent_fee decimal,
	note text
);

create table buy_buildings(
	id varchar(255) primary key,
	price decimal,
	note text
);

create table service(

	id varchar(255) primary key,
	name varchar(255),
    price decimal
);

create table buy_building_details(
	id varchar(255) primary key,
    manager_phone varchar(255),
    manager_name varchar(255),
    service_id varchar(255),
    building_id varchar(255),
    buy_building_id varchar(255),
    expense_id varchar(255),
    map_id varchar(255),
    note text
);

create table rent_building_details(
	id varchar(255) primary key,
    manager_phone varchar(255),
    manager_name varchar(255),
    service_id varchar(255),
    building_id varchar(255),
    rent_building_id varchar(255),
    expense_id varchar(255),
    map_id varchar(255),
    created_by varchar(255),
    modified_date varchar(255),
    note text,
    
	CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES service(id),
    CONSTRAINT fk_building FOREIGN KEY (building_id) REFERENCES buildings(id),
    CONSTRAINT fk_rent_building FOREIGN KEY (rent_building_id) REFERENCES rent_buildings(id),
    CONSTRAINT fk_expense FOREIGN KEY (expense_id) REFERENCES expenses(id),
    CONSTRAINT fk_map FOREIGN KEY (map_id) REFERENCES maps(id),
	CONSTRAINT fk_user FOREIGN KEY (created_by) REFERENCES users(id)
)







