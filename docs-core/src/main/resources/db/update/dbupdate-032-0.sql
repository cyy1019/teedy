create memory table T_USER_REQUEST ( 
  URQ_ID_C varchar(36) primary key, 
  URQ_USERNAME varchar(255) not null, 
  URQ_TYPE_C varchar(255), 
  URQ_PASSWORD varchar(255) not null, 
  URQ_EMAIL varchar(255) not null,
  URQ_CREATEDATE_D datetime,
  URQ_DELETEDATE_D datetime
);
update T_CONFIG set CFG_VALUE_C = '32' where CFG_ID_C = 'DB_VERSION';
