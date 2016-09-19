create table member(
	m_no INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	m_id varchar(50),
    m_pw varchar(20),
    m_flag varchar(5) default 'N'
);

create table product(
	p_no int not null auto_increment primary key,
    p_price int,
    p_name varchar(50),
    p_detail varchar(2000),
    p_reg_date date,
    p_ori varchar(500),
    p_real varchar(500),
    p_path varchar(500)
);

create table image_file(
	if_no int not null auto_increment primary key,
    p_no int,
    if_ori varchar(500),
    if_real varchar(500),
    if_path varchar(500)
);

create table bag(
	b_no int not null auto_increment primary key,
    m_no int,
    p_no int,
    b_cnt int
);

create table ph_list(
	ph_no int not null auto_increment primary key,
    m_no int,
    ph_status int,
    ph_addr varchar(100),
    ph_date date,
    ph_receiver varchar(50),
    ph_tel varchar(50),
    ph_msg varchar(500),
    ph_total_price int,
    ph_method int
);

create table item_list(
	il_no int not null auto_increment primary key,
    ph_no int,
    p_no int,
    il_cnt int
);

