
 drop table tbl_token_time;

       CREATE TABLE tbl_token_time  
    ( id number(10) NOT NULL,  
      token_name varchar2(50) NOT NULL,  
      token_duration number(10),
      CREATED_BY  varchar2(50) NOT NULL,
      CREATED_TS date default sysdate  NOT NULL,
      UPDATED_BY varchar2(50) NOT NULL,
      UPDATED_TS date default sysdate  not null,  
      CONSTRAINT tid_pk PRIMARY KEY (id),
      CONSTRAINT token_name_unique UNIQUE(token_name)
    );  
    
    
    INSERT INTO tbl_token_time (id, token_name, token_duration, CREATED_BY, UPDATED_BY)
   VALUES (1, 'TokenExpiryTime', 10, 'UOB-OCOE','UOB-OCOE' );
   
   INSERT INTO tbl_token_time (id, token_name, token_duration, CREATED_BY, UPDATED_BY)
   VALUES (22, 'TokenRefreshTime', 10, 'UOB-OCOE','UOB-OCOE' );
   
   select * FROM tbl_token_time;
   
  -- update tbl_token_time set token_duration=10;
   
    select tbltokenti0_.id as id1_0_, tbltokenti0_.created_by as created_by2_0_, tbltokenti0_.CREATED_TS as created_date_time3_0_, tbltokenti0_.updated_by as updated_by4_0_, 
    tbltokenti0_.UPDATED_TS as updated_date_time5_0_, tbltokenti0_.token_duration as token_duration6_0_, tbltokenti0_.token_name as token_name7_0_ from tbl_token_time tbltokenti0_
    
    commit;
    
   