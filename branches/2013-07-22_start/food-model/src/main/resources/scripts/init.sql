CREATE SEQUENCE "S_DOC_NUM" MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER NOCYCLE ;
\

CREATE TABLE "P_DOC_NUM"
  (
    "ID"         NUMBER(19,0) NOT NULL ENABLE,
    "CA_CODE"    VARCHAR2(10 CHAR) NOT NULL ENABLE,
    "DOC_TYPE"   VARCHAR2(100 CHAR) NOT NULL ENABLE,
    "DOC_YEAR"   NUMBER(4,0) NOT NULL ENABLE,
    "DOC_NUMBER" NUMBER(19,0) NOT NULL ENABLE,
    CONSTRAINT "P_DOC_NUM_PK" PRIMARY KEY ("ID") USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645 PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT) TABLESPACE "SYSTEM" ENABLE
  )
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING STORAGE
  (
    INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645 PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT
  )
  TABLESPACE "SYSTEM" ;
\

CREATE OR REPLACE PROCEDURE PR_GEN_DOC_NUM (c_code in varchar2, d_type in varchar2,doc_check_type in varchar2, doc_rtn in varchar2, doc_number out varchar2)  AS   
  c_number number(20);
  m_number varchar2(40);
  c_date varchar2(40);
  
  cursor c1 is 
    select t.DOC_NUMBER 
    from P_DOC_NUM t 
    where t.CA_CODE = c_code and t.DOC_TYPE=d_type and t.DOC_YEAR = extract(year from sysdate) 
    for update of t.DOC_NUMBER;
    
BEGIN  
  open c1;
  fetch c1 into c_number;
  if c1%notfound then
    c_number := 1;
    insert into P_DOC_NUM(ID, CA_CODE, DOC_YEAR, DOC_TYPE, DOC_NUMBER) values(S_DOC_NUM.nextval, c_code, extract(year from sysdate), d_type, c_number);
  else
    c_number := c_number + 1;
    update P_DOC_NUM set DOC_NUMBER=c_number where current of c1;
  end if;  
  close c1;
  
  select to_char(sysdate, 'DDMMYY') into c_date from dual; 
  select lpad(c_number, 5, '0') into m_number from dual;
  
  if d_type = 'RISK_ASSESSMENT_TASK' then
    doc_number := 'ОР/' || c_code || '/' || c_date || '/' || m_number;
  elsif d_type = 'PLAN' then
    doc_number := 'ПП/' || c_code || '/' || c_date || '/' || m_number;
  elsif d_type = 'CASE' then    
    doc_number := c_code || '/' || doc_check_type || '/' || doc_rtn || '/' || m_number;
  else
     doc_number := c_code || '/' || c_date || '/' || m_number;
  end if;
  
  DBMS_OUTPUT.PUT_LINE('DOC_NUMBER = ' || doc_number);
  --commit;

  exception
    when others then begin
      --rollback;
      raise;
    end;  
END PR_GEN_DOC_NUM;
\

create or replace
TRIGGER TR_P_DOCUMENT_I
BEFORE INSERT ON P_DOCUMENT 
REFERENCING OLD AS OLD NEW AS NEW FOR EACH ROW

DECLARE
GEN_DOC_NUMBER VARCHAR2(40 CHAR);
CASE_CHECK_TYPE VARCHAR2(40 CHAR);
CASE_RTN VARCHAR2(12 CHAR);
BEGIN
    --TODO add other docs
    
    --generate document number for RISK_ASSESSMENT_TASK 
  if :NEW.TYPE = 'RISK_ASSESSMENT_TASK' AND :NEW.STATUS = 'DRAFT' AND :NEW.DOCUMENTNUMBER IS NULL THEN
    PR_GEN_DOC_NUM(
      C_CODE => :NEW.CUSTOMSAUTHORITY,
      D_TYPE => :NEW.TYPE,
      DOC_CHECK_TYPE => CASE_CHECK_TYPE,
      DOC_RTN => CASE_RTN,    
      DOC_NUMBER => GEN_DOC_NUMBER
    );
    :NEW.DOCUMENTNUMBER := GEN_DOC_NUMBER;
  END IF;
  
  --generate document number for PLAN
  if :NEW.TYPE = 'PLAN' AND :NEW.STATUS = 'DRAFT' AND :NEW.DOCUMENTNUMBER IS NULL THEN
    PR_GEN_DOC_NUM(
      C_CODE => :NEW.CUSTOMSAUTHORITY,
      D_TYPE => :NEW.TYPE,
      DOC_CHECK_TYPE => CASE_CHECK_TYPE,
      DOC_RTN => CASE_RTN,    
      DOC_NUMBER => GEN_DOC_NUMBER
    );
    :NEW.DOCUMENTNUMBER := GEN_DOC_NUMBER;
  END IF;
  
END;
\
create or replace
TRIGGER TR_P_CASE_BI 
BEFORE INSERT ON P_CASE 
REFERENCING NEW AS NEW
FOR EACH ROW 

DECLARE
GEN_DOC_NUMBER VARCHAR2(40 CHAR);
CASE_CHECK_TYPE VARCHAR2(40 CHAR);
CASE_RTN VARCHAR2(12 CHAR);
CUSTAUTH VARCHAR2(255 BYTE);
DTYPE VARCHAR2(255 BYTE);
BEGIN
  select decode(:NEW.CHECKTYPECODE, '1', 'ПП', '2', 'ВП', '3', 'КП') into CASE_CHECK_TYPE from dual;
  select rtn into CASE_RTN from p_pfea where id=:NEW.CHECKPFEA_ID;
  select d.customsauthority into CUSTAUTH from p_document d where d.id=:NEW.id;
  select d.type into DTYPE from p_document d where d.id=:NEW.id;
  
    PR_GEN_DOC_NUM(
      C_CODE => CUSTAUTH,
      D_TYPE => DTYPE,
      DOC_CHECK_TYPE => CASE_CHECK_TYPE,
      DOC_RTN => CASE_RTN,    
      DOC_NUMBER => GEN_DOC_NUMBER
    );
    update p_document set documentnumber = GEN_DOC_NUMBER where id=:NEW.id;

END;
\
create or replace
TRIGGER TR_P_DOCUMENT_BU 
BEFORE UPDATE ON P_DOCUMENT 
REFERENCING OLD AS OLD NEW AS NEW 
FOR EACH ROW 

DECLARE
GEN_DOC_NUMBER VARCHAR2(40 CHAR);
CASE_CHECK_TYPE VARCHAR2(40 CHAR);
CASE_RTN VARCHAR2(12 CHAR);
ACT_TYPE VARCHAR2(255 BYTE);

BEGIN
 if :NEW.TYPE = 'PRESCRIPT' AND :NEW.STATUS = 'REGISTERED_IN_CA' AND :NEW.DOCUMENTNUMBER IS NULL THEN
  PR_GEN_DOC_NUM(
      C_CODE => :NEW.CUSTOMSAUTHORITY,
      D_TYPE => :NEW.TYPE,
      DOC_CHECK_TYPE => CASE_CHECK_TYPE,
      DOC_RTN => CASE_RTN,    
      DOC_NUMBER => GEN_DOC_NUMBER
    );
    :NEW.DOCUMENTNUMBER := GEN_DOC_NUMBER;
  END IF;  
  
  if :NEW.TYPE = 'ACT' AND :NEW.STATUS = 'COMPLETED' AND :NEW.DOCUMENTNUMBER IS NULL THEN
    select decode(A.CHECK_TYPE_CODE, '1', 'ACT', '2', 'ACT', '3', 'ACT_CAMERAL') into ACT_TYPE from P_ACT A WHERE A.id=:NEW.id;
    PR_GEN_DOC_NUM(
        C_CODE => :NEW.CUSTOMSAUTHORITY,
        D_TYPE => ACT_TYPE,
        DOC_CHECK_TYPE => CASE_CHECK_TYPE,
        DOC_RTN => CASE_RTN,    
        DOC_NUMBER => GEN_DOC_NUMBER
      );
      :NEW.DOCUMENTNUMBER := GEN_DOC_NUMBER;
  END IF;
  
  if :NEW.TYPE = 'NOTIFICATION' AND :NEW.STATUS = 'COMPLETED' AND :NEW.DOCUMENTNUMBER IS NULL THEN
  PR_GEN_DOC_NUM(
      C_CODE => :NEW.CUSTOMSAUTHORITY,
      D_TYPE => :NEW.TYPE,
      DOC_CHECK_TYPE => CASE_CHECK_TYPE,
      DOC_RTN => CASE_RTN,    
      DOC_NUMBER => GEN_DOC_NUMBER
    );
    :NEW.DOCUMENTNUMBER := GEN_DOC_NUMBER;
  END IF;
  
  if :NEW.TYPE = 'REQUIREMENT' AND :NEW.STATUS = 'COMPLETED' AND :NEW.DOCUMENTNUMBER IS NULL THEN
  PR_GEN_DOC_NUM(
      C_CODE => :NEW.CUSTOMSAUTHORITY,
      D_TYPE => :NEW.TYPE,
      DOC_CHECK_TYPE => CASE_CHECK_TYPE,
      DOC_RTN => CASE_RTN,    
      DOC_NUMBER => GEN_DOC_NUMBER
    );
    :NEW.DOCUMENTNUMBER := GEN_DOC_NUMBER;
  END IF;
END;
\
CREATE SEQUENCE P_RATRES_REQUEST_SEQ INCREMENT BY 1 START WITH 1 NOCACHE ORDER;
\
CREATE OR REPLACE TRIGGER TR_P_RA_TASK_BI 
BEFORE INSERT ON P_RA_TASK 
REFERENCING NEW AS NEW 
FOR EACH ROW 
WHEN (NEW.REQUESTID IS NULL)

DECLARE 
REQID NUMBER(19,0);
BEGIN
  select p_ratres_request_seq.nextval into REQID from dual;
  :NEW.REQUESTID := REQID;
END;
\
insert into p_auth_role (id, description, name) values (1, 'Утверждающее лицо', 'ROLE_PAR_STATING_PERSON');
insert into p_auth_role (id, description, name) values (2, 'Согласующее лицо 1 уровня', 'ROLE_PAR_1_LEVEL_COORDINATING');
insert into p_auth_role (id, description, name) values (3, 'Согласующее лицо 2 уровня', 'ROLE_PAR_2_LEVEL_COORDINATING');
insert into p_auth_role (id, description, name) values (4, 'Аналитик 1 уровня', 'ROLE_PAR_1_LEVEL_ANALYST');
insert into p_auth_role (id, description, name) values (5, 'Аналитик 2 уровня', 'ROLE_PAR_2_LEVEL_ANALYST');
insert into p_auth_role (id, description, name) values (6, 'Проверяющее лицо', 'ROLE_PAR_CHECKING_PERSON');
insert into p_auth_role (id, description, name) values (7, 'Администратор', 'ROLE_PAR_MANAGER');

REM INSERTING into P_AUTH_PERMISSION
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (1,'UC-PM-005','Просмотреть список задании на оценку риска');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (2,'UC-PM-010','Создать Задание на оценку риска');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (3,'UC-PM-015','Выполнить задание на оценку риска');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (4,'UC-PM-025','Просмотреть задание на оценку риска');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (5,'UC-PM-050','Просмотреть список Планов');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (6,'UC-PM-055','Создать План');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (7,'UC-PM-060','Формировать перечень для Плана');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (8,'UC-PM-065','Редактировать План проверок');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (9,'UC-PM-070','Удалить План проверок');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (10,'UC-PM-075','Добавить проверяемое лицо');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (11,'UC-PM-080','Отправить План на согласование в ДТК');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (12,'UC-PM-095','Принять исправления в План');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (13,'UC-PM-100','Отправить План на согласование в КТК');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (14,'UC-PM-115','Внести изменения в утвержденный План проверок');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (15,'UC-PM-116','Удалить проверяемое лицо');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (16,'UC-PM-118','Сохранить План');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (17,'UC-PM-051','Просмотреть План');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (18,'UC-PM-120','Просмотреть список Дел пост-таможенного контроля');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (19,'UC-PM-125','Просмотреть Дело пост-таможенного контроля');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (20,'UC-PM-145','Просмотреть Предписание');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (21,'UC-PM-185','Просмотреть Акт');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (22,'UC-PM-210','Просмотреть Уведомление');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (23,'UC-PM-240','Просмотреть Требование');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (24,'UC-PM-132','Создать Дело по камеральной проверке');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (25,'UC-PM-133','Создать документы в деле');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (26,'UC-PM-135','Закрыть Дело пост-таможенного контроля');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (27,'UC-PM-140','Создать Предписание');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (28,'UC-PM-150','Сохранить Предписание');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (29,'UC-PM-151','Удалить Предписание');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (30,'UC-PM-155','Регистрировать Предписание в ТО');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (31,'UC-PM-160','Регистрировать Предписание в ОП');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (32,'UC-PM-165','Вручить Предписание');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (33,'UC-PM-170','Приостановить Предписание');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (34,'UC-PM-175','Возобновить Предписание');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (35,'UC-PM-180','Продлить Предписание');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (36,'UC-PM-190','Создать Акт');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (37,'UC-PM-195','Сохранить Акт');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (38,'UC-PM-196','Удалить Акт');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (39,'UC-PM-197','Рассчитать пеню');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (40,'UC-PM-200','Завершить Акт');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (41,'UC-PM-205','Вручить Акт');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (42,'UC-PM-215','Создать Уведомление');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (43,'UC-PM-220','Сохранить Уведомление');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (44,'UC-PM-221','Удалить Уведомление');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (45,'UC-PM-222','Рассчитать пеню');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (46,'UC-PM-225','Завершить Уведомление');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (47,'UC-PM-230','Вручить Уведомление');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (48,'UC-PM-235','Отозвать Уведомление');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (49,'UC-PM-245','Создать Требование');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (50,'UC-PM-250','Сохранить Требование');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (51,'UC-PM-251','Удалить Требование');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (52,'UC-PM-255','Завершить Требование');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (53,'UC-PM-260','Вручить Требование');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (54,'UC-PM-265','Отозвать Требование');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (55,'UC-PM-020','Оценка риска');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (56,'UC-PM-105','Cогласовать План в КТК');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (57,'UC-PM-131','Создать Дело по внеплановой проверке');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (58,'UC-PM-085','Согласовать План проверок');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (59,'UC-PM-090','Отправить План проверок с учетом замечаний');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (60,'UC-PM-130','Создать Дело по плановой проверке');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (61,'UC-PM-134','Назначить лицо');
Insert into P_AUTH_PERMISSION (ID,CODE,DESCRIPTION) values (62,'UC-PM-110','Утвердить План');


REM INSERTING into P_AUTH_ROLE_PERM
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (1,5);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (1,17);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (1,18);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (1,19);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (1,20);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (1,21);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (1,22);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (1,23);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (1,62);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (2,5);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (2,17);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (2,18);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (2,19);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (2,20);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (2,21);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (2,22);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (2,23);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (2,56);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (2,57);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,5);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,17);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,18);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,19);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,20);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,21);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,22);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,23);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,57);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,58);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,59);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,60);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (3,61);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,1);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,2);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,3);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,4);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,5);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,6);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,7);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,8);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,9);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,10);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,11);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,12);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,13);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,14);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,15);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,16);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,17);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,18);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,19);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,20);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,21);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,22);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (4,23);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (5,5);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (5,8);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (5,10);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (5,17);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (5,18);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (5,19);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (5,20);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (5,21);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (5,22);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (5,23);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,5);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,17);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,18);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,19);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,20);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,21);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,22);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,23);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,24);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,25);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,26);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,27);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,28);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,29);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,30);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,31);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,32);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,33);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,34);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,35);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,36);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,37);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,38);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,39);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,40);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,41);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,42);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,43);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,44);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,45);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,46);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,47);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,48);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,49);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,50);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,51);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,52);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,53);
Insert into P_AUTH_ROLE_PERM (P_AUTH_ROLE_ID,PERMISSIONS_ID) values (6,54);
\

CREATE OR REPLACE FORCE VIEW "P_VIEW_FINECFG" ("DATE_FROM", "DATE_TO", "FACTOR_MULT", "REF_RATE", "DAYS")
AS
  SELECT t.date_from,
    t.date_to,
    t.factor_mult,
    t.ref_rate,
    floor(t.date_to - t.date_from + 1) days
  FROM
    (SELECT f.id,
      f.date_from,
      NVL(
      (SELECT MIN(t.date_from)-1
      FROM p_fine_config t
      WHERE t.date_from > f.date_from
      ), sysdate) date_to,
      f.factor_mult,
      f.ref_rate
    FROM p_fine_config f
    ORDER BY date_from ASC
    ) t;
\
CREATE OR REPLACE FUNCTION FN_CALCULATE_FINE 
(
  IN_DATE_FROM IN DATE  
, IN_DATE_TO IN DATE  
, IN_ARREARS IN NUMBER  
) RETURN NUMBER AS 
total_fine NUMBER;
BEGIN
  SELECT NVL(ROUND(SUM((IN_ARREARS*ref_rate*factor_mult*days/100)/365),2),0)
  INTO total_fine
  FROM (
    (SELECT IN_DATE_FROM date_from,
      t.date_to,
      t.factor_mult,
      t.ref_rate,
      floor(t.date_to-IN_DATE_FROM+1) days
    FROM p_view_finecfg t
    WHERE date_from =
      (SELECT MAX(date_from) FROM p_fine_config WHERE date_from <= IN_DATE_FROM
      )
    AND date_to < IN_DATE_TO
    )
  UNION
    (SELECT f.date_from,
      f.date_to,
      f.factor_mult,
      f.ref_rate,
      f.days
    FROM p_view_finecfg f
    WHERE f.date_from >= IN_DATE_FROM
    AND f.date_to     <= IN_DATE_TO
    )
  UNION
    (SELECT t.date_from,
      IN_DATE_TO date_to,
      t.factor_mult,
      t.ref_rate,
      floor(IN_DATE_TO-t.date_from+1) days
    FROM p_view_finecfg t
    WHERE date_from =
      (SELECT MAX(date_from) FROM p_fine_config WHERE date_from <= IN_DATE_TO
      )
    AND date_from >IN_DATE_FROM
    )
  UNION
    (SELECT IN_DATE_FROM date_from,
      IN_DATE_TO date_to,
      factor_mult,
      ref_rate,
      floor(IN_DATE_TO - IN_DATE_FROM + 1) days
    FROM p_view_finecfg
    WHERE date_from <= IN_DATE_FROM
    AND IN_DATE_TO  <= date_to
    ));
  RETURN total_fine;
END FN_CALCULATE_FINE;
\
-- CREATE TRADERS_VIEW in  risk_analysis_dm_kz_unf
CREATE MATERIALIZED VIEW TRADERS_VIEW 
NOCACHE 
NOPARALLEL 
BUILD IMMEDIATE 
USING INDEX 
REFRESH START WITH SYSDATE NEXT trunc(SYSDATE + 1) COMPLETE
WITH ROWID 
DISABLE QUERY REWRITE AS 
SELECT 
    *
FROM traders_view@dblink_risk_analysis_dm_kz;