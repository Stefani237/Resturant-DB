--  DATABASE CREATION FILE
--  created by: Dvir Twina and Stefani Tamari

-- Tables creation
CREATE TABLE table_ (
    TABLE_ID int not null PRIMARY KEY,
    NUM_OF_SEATS INT,
    IS_OCCUPIED VARCHAR2(15), -- 'true', 'false'
    NUM_OF_OCCUPIED_SEATS INT
);

CREATE TABLE DISH (
    DISH_ID int not null PRIMARY KEY,
    NAME VARCHAR2(50) not null,
    TYPE VARCHAR2(50)not null, -- 'Starters', 'Main', Desserts
    DESCRIPTION VARCHAR2(1024)
);

CREATE TABLE CUSTOMER (
    CUSTOMER_ID int not null PRIMARY KEY,
    FIRST_NAME VARCHAR2(50)not null,
    LAST_NAME VARCHAR2(50),
    TEL_NUM VARCHAR2(15) not null
);

CREATE TABLE RESERVATION (
    RESERVATION_ID int not null PRIMARY KEY,
    CUSTOMER_ID REFERENCES CUSTOMER(CUSTOMER_ID), 
    DISH_ID REFERENCES DISH(DISH_ID)
);

CREATE TABLE EMPLOYEE (
    EMPLOYEE_ID int not null PRIMARY KEY,
    FIRST_NAME VARCHAR2(50) not null,
    LAST_NAME VARCHAR2(50) not null,
    ADDRESS VARCHAR2(50) not null,
    TEL_NUM VARCHAR2(15) not null,    
    ROLE_ VARCHAR2(30) not null,
    HIRE_DATE DATE not null
);

CREATE TABLE TABLE_EMP_CUST ( -- THIS TABLE LINKS THE TABLE FOR THE CUSTOMER, THE WAITER, AND THE DATE-TIME THEY WILL BE DINING AT
    TABLE_ID REFERENCES TABLE_(TABLE_ID),
    EMPLOYEE_ID REFERENCES EMPLOYEE(EMPLOYEE_ID),
    CUSTOMER_ID REFERENCES CUSTOMER(CUSTOMER_ID),
    DATE_OF_RESERVATION DATE not null,
    
    CONSTRAINT TABLE_EMP_CUST_tbl_unique UNIQUE (TABLE_ID, EMPLOYEE_ID, CUSTOMER_ID)
);

-- Sequences
CREATE SEQUENCE table_id_seq
START WITH 1
INCREMENT BY 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE dish_id_seq
START WITH 1
INCREMENT BY 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE customer_id_seq
START WITH 1
INCREMENT BY 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE reservation_id_seq
START WITH 1
INCREMENT BY 1
NOCYCLE
NOCACHE;

CREATE SEQUENCE employee_id_seq
START WITH 1
INCREMENT BY 1
NOCYCLE
NOCACHE;

-- Insertions
insert into TABLE_ values (TABLE_ID_SEQ.nextval, 2, 'false', 0);
insert into TABLE_ values (TABLE_ID_SEQ.nextval, 2, 'false', 0);
insert into TABLE_ values (TABLE_ID_SEQ.nextval, 2, 'false', 0);
insert into TABLE_ values (TABLE_ID_SEQ.nextval, 2, 'false', 0);
insert into TABLE_ values (TABLE_ID_SEQ.nextval, 4, 'false', 0);
insert into TABLE_ values (TABLE_ID_SEQ.nextval, 4, 'false', 0);
insert into TABLE_ values (TABLE_ID_SEQ.nextval, 4, 'false', 0);
insert into TABLE_ values (TABLE_ID_SEQ.nextval, 6, 'false', 0);
insert into TABLE_ values (TABLE_ID_SEQ.nextval, 6, 'false', 0);
insert into TABLE_ values (TABLE_ID_SEQ.nextval, 10, 'false', 0);


insert into DISH values (DISH_ID_SEQ.nextval, 'House Focaccia', 'Starters', 'With a delicate coating of Atlantic salt, thyme and fresh garlic');
insert into DISH values (DISH_ID_SEQ.nextval, 'Sirloin Carpaccio', 'Starters', 'Thin sirloin strips, preserved with lemon, olive and balsamic vinegar, served with a small salad of onion, radish and arugula');
insert into DISH values (DISH_ID_SEQ.nextval, 'Grilled Chicken Livers', 'Starters', 'Served on caramelized fruits');
insert into DISH values (DISH_ID_SEQ.nextval, 'Lamb Kebab', 'Starters', 'Served on a bonfire salad, pickled lemons and green tahini');
insert into DISH values (DISH_ID_SEQ.nextval, 'Aged Prime Entrecote steak', 'Main', 'Served with saut?ed green beans');
insert into DISH values (DISH_ID_SEQ.nextval, 'Beef Fillet Medallions', 'Main', 'Served with root vegetable puree');
insert into DISH values (DISH_ID_SEQ.nextval, 'Lamb Chops', 'Main', 'Served with root vegetable puree');
insert into DISH values (DISH_ID_SEQ.nextval, 'Hamburger', 'Main', 'From prime beef with Foie Gras and truffle aioli, served with papas fritas');
insert into DISH values (DISH_ID_SEQ.nextval, 'Goose Liver', 'Main', 'Green salad on the side');
insert into DISH values (DISH_ID_SEQ.nextval, 'Chocolate Souffl?', 'Desserts', 'A chocolate pastry wrapped in hot chocolate, vanilla ice cream and roasted peanuts');
insert into DISH values (DISH_ID_SEQ.nextval, 'Waffles pleasure', 'Desserts', 'Belgian waffles served with vanilla ice cream, fruit salad and maple');


insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'Steven','King', 'Yarkonim 87, Tel Aviv','0547406865','Cook', TO_DATE('01/01/2016','dd/MM/yyyy'));
insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'Nina','Bissot', 'Dafna 32, Petach Tikva','0507406865','Cook', TO_DATE('28/12/2015','dd/MM/yyyy'));
insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'Lex','Greenberg', 'Nehemiah 3, Bat Yam','0547404865','waiter', TO_DATE('13/11/2017','dd/MM/yyyy'));
insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'Alexander','Chen', 'Bar Kochva 6, Bat Yam','0544406865','waiter', TO_DATE('16/08/2011','dd/MM/yyyy'));
insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'Bruce','David', 'Negba 89, Ramat Gan','0547407865','Manager', TO_DATE('22/02/2017','dd/MM/yyyy'));
insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'David','Kochhar', 'Moria 134, Tel Aviv','0523506865','waiter', TO_DATE('09/06/2013','dd/MM/yyyy'));
insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'Valli','Moshe', 'Yefet 55, Or Yehuda','0547406165','waiter', TO_DATE('04/12/2016','dd/MM/yyyy'));
insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'Diana','Louis','Netzer Sireni 10, Givatayim','0527405845','waitress', TO_DATE('25/07/2015','dd/MM/yyyy'));
insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'Nancy','Mor', 'Geffen 11, Tel Aviv','0507486865','Manager', TO_DATE('30/04/2016','dd/MM/yyyy'));
insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'Daniel','Weiss', 'Katznelson 72, Givatayim','0547404565','Cook', TO_DATE('16/01/2017','dd/MM/yyyy'));
insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, 'Michelle','Miller', 'Uziel 47, Ramat Gan','0507554565','hostess', TO_DATE('27/01/2017','dd/MM/yyyy'));

insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, 'Shalom', 'Michaelshvily',    '050-1234567');
insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, 'Asi',    'Israelof',         '050-1234568');
insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, 'Zion',   'Baruch',           '050-1234569');
insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, 'Yuval',  'Semo',             '050-1111111');
insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, 'Shlomi', 'Koriat',           '050-2222222');
insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, 'Ofir',   'Lobel',            '050-3333333');
insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, 'Adir',   'Miller',           '050-4444444');
insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, 'Israel', 'Katorza',          '050-5555555');
insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, 'Shachar','Hasson',           '050-6666666');
insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, 'Ori',    'Hizkiya',          '050-7777777');

insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, 1, 5);
insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, 1, 5);
insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, 1, 3);
insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, 3, 2);
insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, 6, 2);
insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, 5, 7);
insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, 8, 1);
insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, 9, 9);
insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, 6, 4);
insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, 5, 8);

insert into TABLE_EMP_CUST VALUES(3,4,3,TO_DATE( '13/02/2018 19:45', 'DD/MM/YYYY HH24:MI' ));
insert into TABLE_EMP_CUST VALUES(2,1,4,TO_DATE( '13/02/2018 20:00', 'DD/MM/YYYY HH24:MI' ));
insert into TABLE_EMP_CUST VALUES(1,3,6,TO_DATE( '14/02/2018 20:00', 'DD/MM/YYYY HH24:MI' ));
insert into TABLE_EMP_CUST VALUES(7,6,9,TO_DATE( '17/02/2018 19:30', 'DD/MM/YYYY HH24:MI' ));
insert into TABLE_EMP_CUST VALUES(5,4,7,TO_DATE( '17/02/2018 20:30', 'DD/MM/YYYY HH24:MI' ));
insert into TABLE_EMP_CUST VALUES(6,6,5,TO_DATE( '20/02/2018 19:00', 'DD/MM/YYYY HH24:MI' ));
insert into TABLE_EMP_CUST VALUES(3,4,8,TO_DATE( '20/02/2018 21:30', 'DD/MM/YYYY HH24:MI' ));
insert into TABLE_EMP_CUST VALUES(1,4,1,TO_DATE( '25/02/2018 20:30', 'DD/MM/YYYY HH24:MI' ));
insert into TABLE_EMP_CUST VALUES(1,4,3,TO_DATE( '11/03/2018 19:30', 'DD/MM/YYYY HH24:MI' ));
insert into TABLE_EMP_CUST VALUES(1,3,1,TO_DATE( '18/03/2018 19:45', 'DD/MM/YYYY HH24:MI' ));
insert into TABLE_EMP_CUST VALUES(6,4,3,TO_DATE( '17/03/2018 20:30', 'DD/MM/YYYY HH24:MI' ));

create or replace PROCEDURE TABLE_UPDATE__TABLE_OCCUPATION(pk_val IN int, occupation_state IN VARCHAR2) 
IS 
    rt_table TABLE_%ROWTYPE;
    CURSOR c1 IS SELECT * FROM TABLE_ WHERE TABLE_ID = pk_val FOR UPDATE;
    BEGIN
    OPEN c1;
    LOOP 
        FETCH c1 INTO rt_table;
        EXIT WHEN c1%NOTFOUND;
        UPDATE TABLE_ SET IS_OCCUPIED = occupation_state WHERE CURRENT OF c1;
    END LOOP;
    COMMIT;
END;
    
create or replace FUNCTION GET_CUSTOMER_BY_ORDERED_TABLE(   
    in_table_id IN TABLE_.TABLE_ID%TYPE, 
    in_datetime_of_reservation IN TABLE_EMP_CUST.DATE_OF_RESERVATION%TYPE)
RETURN VARCHAR2
IS
    output VARCHAR2(50);
BEGIN
    SELECT FIRST_NAME || ' ' || LAST_NAME INTO output
    FROM CUSTOMER
    WHERE CUSTOMER_ID IN (  SELECT CUSTOMER_ID 
                            FROM TABLE_EMP_CUST     
                            WHERE TABLE_EMP_CUST.TABLE_ID = in_table_id
                            AND TABLE_EMP_CUST.DATE_OF_RESERVATION = in_datetime_of_reservation);
    RETURN output;
END GET_CUSTOMER_BY_ORDERED_TABLE;

create or replace TRIGGER validate_booleans
BEFORE INSERT OR UPDATE OF IS_OCCUPIED ON TABLE_
FOR EACH ROW 
WHEN ((lower(NEW.IS_OCCUPIED) LIKE 'true') OR (lower(NEW.IS_OCCUPIED) LIKE 'false'))
BEGIN
    IF (lower(:NEW.IS_OCCUPIED) LIKE 'true')
    THEN 
        :NEW.IS_OCCUPIED := 'true';

    ELSIF (lower(:NEW.IS_OCCUPIED) LIKE 'false')
    THEN 
        :NEW.IS_OCCUPIED := 'false';

    END IF;
END;

  CREATE OR REPLACE FORCE VIEW "SYSTEM"."ORDERED_TODAY" ("COUNTER", "DISH") AS 
  SELECT COUNT(D.DISH_ID) AS COUNTER, D.NAME AS DISH
FROM TABLE_EMP_CUST TBC, RESERVATION R, DISH D
WHERE TBC.DATE_OF_RESERVATION LIKE TO_DATE( sysdate, 'DD/MM/YYYY HH24:MI' )
              AND TBC.CUSTOMER_ID = R.CUSTOMER_ID
              AND R.DISH_ID = D.DISH_ID
GROUP BY D.NAME;

create or replace PROCEDURE FIND_TABLE (p_guest_num IN NUMBER, p_table OUT NUMBER) 
IS
    too_many_guests EXCEPTION;
BEGIN    
IF p_guest_num > 12 THEN
    RAISE too_many_guests;
ELSE
    SELECT TABLE_ID INTO p_table
    FROM (select * from TABLE_ order by NUM_OF_SEATS ASC) TABLE_
    WHERE IS_OCCUPIED = 'false' AND NUM_OF_SEATS >= p_guest_num AND ROWNUM <= 1;
END IF;
EXCEPTION 
    WHEN too_many_guests THEN 
    raise_application_error (-20001, 'There is no suitable table. A number of tables must be connected.');
    WHEN OTHERS THEN 
    raise_application_error (-20002, 'An error occurred while entering the number of guests.');
END FIND_TABLE;

COMMIT;