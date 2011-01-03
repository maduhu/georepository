SET search_path = dgwatch_test, pg_catalog, public;

/*==============================================================*/
/* Views                                                        */
/*==============================================================*/

CREATE OR REPLACE VIEW pga_pcolumns AS 
 SELECT pg_class.relname AS tablename, pg_attribute.attname AS columnname, 
        ( SELECT seq_class.relname
           FROM pg_class seq_class
          WHERE seq_class.relname = (pg_class.relname || '_seq')
         LIMIT 1) AS sequencename
   FROM pg_user, pg_class, pg_attribute
  WHERE pg_user.usesysid = pg_class.relowner 
  AND pg_user.usename = 'dgwatch_test' 
  AND pg_class.oid = pg_attribute.attrelid 
  AND pg_attribute.attnum = 1 
  AND pg_class.relkind = 'r'
  AND pg_attribute.attname = 'id'
  ORDER BY pg_class.relname, pg_attribute.attnum;

CREATE OR REPLACE VIEW query_cache_stats AS 
 SELECT pg_stat_database.datname, 
        pg_stat_database.blks_read, 
        pg_stat_database.blks_hit, 
        round((pg_stat_database.blks_hit::double precision / (pg_stat_database.blks_read + pg_stat_database.blks_hit + 1)::double precision * 100::double precision)::numeric, 2) AS cachehitratio
   FROM pg_stat_database
  WHERE pg_stat_database.datname !~ '^(template(0|1)|postgres)$'
  ORDER BY round((pg_stat_database.blks_hit::double precision / (pg_stat_database.blks_read + pg_stat_database.blks_hit + 1)::double precision * 100::double precision)::numeric, 2) DESC;


/*==============================================================*/
/* Functions                                                    */
/*==============================================================*/

-- Function: getlookupid(character varying, character varying)
CREATE OR REPLACE FUNCTION getlookupid(character varying, character varying)
  RETURNS integer AS
$BODY$
DECLARE
    --b_tableName ALIAS FOR $1;
    --b_lookuUpName ALIAS FOR $2;
    v_sql varchar(500);
    v_id INTEGER;

BEGIN

  v_sql := 'select id from ' || $1 ||' where name = '''|| $2 ||'''' ;
  
  EXECUTE v_sql into v_id ;
  
  RETURN v_id;

END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;

-- Function: fixsequences()
CREATE OR REPLACE FUNCTION fixsequences()
  RETURNS integer AS
$BODY$
declare
       table_obj record;
begin
     for table_obj in execute 'select * from pga_pcolumns' loop
         RAISE NOTICE 'Table: %', table_obj.tablename;
         if table_obj.sequencename is not null and table_obj.columnname = 'id' then
                  execute 'SELECT setval('|| quote_literal(table_obj.sequencename) ||', (SELECT max('|| table_obj.columnname || ')+1 FROM '|| table_obj.tablename ||'));';
            RAISE NOTICE 'Resetting: %', table_obj.sequencename;
         end if;
     end loop;
     return 1;
end;
$BODY$
  LANGUAGE 'plpgsql' IMMUTABLE STRICT;
  
/*==============================================================*/
/* Sequences                                                    */
/*==============================================================*/

create sequence DATABASE_VERSION_SEQ
increment 1
minvalue 15000
start 15000;

create sequence VERSION_CONTENT_SEQ
increment 1
minvalue 15000
start 15000;

create sequence DG_AOI_SEQ
increment 1
minvalue 15000
start 15000;

create sequence DG_FEATURE_SEQ
increment 1
minvalue 15000
start 15000;

create sequence DG_USER_SEQ
increment 1
minvalue 15000
start 15000;


/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     8/28/2007 4:09:24 PM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: DATABASE_VERSION                                      */
/*==============================================================*/
create table DATABASE_VERSION (
   ID                   INT4                 not null default nextval('database_version_seq'),
   SYSTEM_VERSION       VARCHAR(10)          not null,
   INSTALLATION_DATE    DATE                 not null,
   END_DATE             DATE                 null,
   IS_SUCCESSFUL        NUMERIC(1)           null,
   constraint DATABASE_VERSION_PK primary key (ID)
);

comment on table DATABASE_VERSION is
'Segment:  MetaData

Meta data maintained by the Database Administration team to track the schema version.';

/*==============================================================*/
/* Index: DATABASE_VERSION_IDX1                                 */
/*==============================================================*/
create  index DATABASE_VERSION_IDX1 on DATABASE_VERSION (
SYSTEM_VERSION
);





/*==============================================================*/
/* Table: VERSION_CONTENT                                       */
/*==============================================================*/
create table VERSION_CONTENT (
   ID                   INT4                 not null default nextval('version_content_seq'),
   OBJECT_NAME          VARCHAR(50)          not null,
   OBJECT_TYPE          VARCHAR(19)          not null,
   DATABASE_VERSION_ID  INT4                 not null,
   constraint PK_VERSION_CONTENT primary key (ID)
);

comment on table VERSION_CONTENT is
'Segment: MetaData

Meta data maintained by the Database Administration team to track version content to support incremental alters.';

/*==============================================================*/
/* Index: VERSION_CONTENT_IDX1                                  */
/*==============================================================*/
create unique index VERSION_CONTENT_IDX1 on VERSION_CONTENT (
OBJECT_NAME,
OBJECT_TYPE,
DATABASE_VERSION_ID
);

/*==============================================================*/
/* Index: VERSION_CONTENT_IDX2                                  */
/*==============================================================*/
create  index VERSION_CONTENT_IDX2 on VERSION_CONTENT (
DATABASE_VERSION_ID
);




/*==============================================================*/
/* Table: DG_AOI                                                */
/*==============================================================*/
create table DG_AOI (
   ID                   INT4                 not null default nextval('dg_aoi_seq'),
   AREA                 NUMERIC              null,
   CREATION_TIME        TIMESTAMP WITH TIME ZONE  not null,
   EXPIRATION_TIME      TIMESTAMP WITH TIME ZONE  null,
   GEOMETRY             GEOMETRY             not null,
   LAST_UPDATE_TIME     TIMESTAMP WITH TIME ZONE  null,
   IS_PUBLISHED         BOOL                 null,
   IS_SHARED            BOOL                 null,
   TITLE                VARCHAR(255)         not null,
   OWNER_ID             INT4                 null,
   constraint DG_AOI_PK primary key (ID)
);

ALTER TABLE dg_aoi ADD CONSTRAINT enforce_dims_geometry
CHECK ((ndims(geometry) = 2));

ALTER TABLE dg_aoi 
ADD CONSTRAINT enforce_geotype_geometry CHECK (((geometrytype(geometry) = 'MULTIPOLYGON'::text) 
OR (geometrytype(geometry) = 'POLYGON'::text) OR (geometry IS NULL)) );

ALTER TABLE dg_aoi ADD CONSTRAINT enforce_srid_geometry
CHECK ((srid(geometry) = 4326));

delete from geometry_columns 
where f_table_schema='dgwatch_test'
and f_table_name='dg_aoi'
and f_geometry_column='geometry';

insert into geometry_columns values('', 'dgwatch_test', 'dg_aoi', 'geometry', 2, 4326, 'GEOMETRY');

/*==============================================================*/
/* Index: DG_AOI_IDX1                                           */
/*==============================================================*/
create  index DG_AOI_IDX1 on DG_AOI using GIST (
GEOMETRY
);

/*==============================================================*/
/* Index: DG_AOI_IDX2                                           */
/*==============================================================*/
create  index DG_AOI_IDX2 on DG_AOI (
OWNER_ID
);






/*==============================================================*/
/* Table: DG_USER                                               */
/*==============================================================*/
create table DG_USER (
   ID                   INT4                 not null default nextval('dg_user_seq'),
   USER_NAME            VARCHAR(255)         not null unique,
   USER_PASSWORD        VARCHAR(255)         null,
   CONNECT_ID           VARCHAR(255)         not null,
   EMAIL_ADDRESS        VARCHAR(255)         not null unique,
   IS_ENABLED           BOOL                 not null,
   IS_REDUCED_CONTENT   BOOL                 not null,
   SEND_EMAIL           BOOL                 not null,
   SEND_RSS             BOOL                 not null,
   TIMER_NAME           VARCHAR(255)         not null,
   constraint DG_USER_PK primary key (ID)
);

/*==============================================================*/
/* Index: DG_USER_IDX1                                          */
/*==============================================================*/
create unique index DG_USER_IDX1 on DG_USER (
USER_NAME
);


/*==============================================================*/
/* Table: DG_AOI_USER_PREFERENCE                                */
/*==============================================================*/
create table DG_AOI_USER_PREFERENCE (
   DG_AOI_ID            INT4                 not null,
   DG_USER_ID           INT4                 not null,
   ACQUISITION_TIME     TIMESTAMP WITH TIME ZONE null,
   ACQUISITION_TIME_OP  VARCHAR(255)             null,
   CLOUD_COVER          FLOAT4                   null,
   CLOUD_COVER_OP       VARCHAR(255)             null,
   DATA_LAYER           TEXT                     null,
   DATA_LAYER_OP        VARCHAR(255)             null,
   LEGACY_ID            TEXT                     null,
   LEGACY_ID_OP         VARCHAR(255)             null,
   RETRIEVE_TYPE        VARCHAR(255)             null,
   constraint PK_DG_AOI_USER_PREFERENCE primary key (DG_AOI_ID, DG_USER_ID)
);


/*==============================================================*/
/* Index: DG_AOI_USER_PREFERENCE_IDX1                           */
/*==============================================================*/
create unique index DG_AOI_USER_PREFERENCE_IDX1 on DG_AOI_USER_PREFERENCE (
DG_AOI_ID,
DG_USER_ID
);


/*==============================================================*/
/* Table: DG_FEATURE                                            */
/*==============================================================*/
create table DG_FEATURE (
   ID                   INT4                 not null default nextval('dg_feature_seq'),
   CREATION_TIME        TIMESTAMP WITH TIME ZONE null,
   EXTERNAL_IDENTIFIER  VARCHAR(255)         null,
   EXTERNAL_SORTING_TIME  TIMESTAMP WITH TIME ZONE null,
   GEOMETRY             GEOMETRY             not null,
   LAST_SENT_MAIL_TIME  TIMESTAMP WITH TIME ZONE null,
   LAST_SENT_RSS_TIME   TIMESTAMP WITH TIME ZONE null,
   TITLE                VARCHAR(255)         null,
   WFS_RESPONSE         TEXT                 null,
   dg_aoi_id             INT4                 not null,
   DG_USER_ID           INT4                 not null,
   constraint DG_FEATURE_PK primary key (ID)
);

ALTER TABLE dg_feature ADD CONSTRAINT enforce_dims_geometry
CHECK ((ndims(geometry) = 2));

ALTER TABLE dg_feature 
ADD CONSTRAINT enforce_geotype_geometry CHECK (((geometrytype(geometry) = 'MULTIPOLYGON'::text) 
OR (geometrytype(geometry) = 'POLYGON'::text) OR (geometry IS NULL)) );

ALTER TABLE dg_feature ADD CONSTRAINT enforce_srid_geometry
CHECK ((srid(geometry) = 4326));

delete from geometry_columns 
where f_table_schema='dgwatch_test'
and f_table_name='dg_feature'
and f_geometry_column='geometry';

insert into geometry_columns values('', 'dgwatch_test', 'dg_feature', 'geometry', 2, 4326, 'GEOMETRY');

/*==============================================================*/
/* Index: DG_FEATURE_IDX1                                       */
/*==============================================================*/
create  index DG_FEATURE_IDX1 on DG_FEATURE (
EXTERNAL_IDENTIFIER
);

/*==============================================================*/
/* Index: DG_FEATURE_IDX2                                       */
/*==============================================================*/
create  index DG_FEATURE_IDX2 on DG_FEATURE (
dg_aoi_id
);

/*==============================================================*/
/* Index: DG_FEATURE_IDX3                                       */
/*==============================================================*/
create  index DG_FEATURE_IDX3 on DG_FEATURE (
DG_USER_ID
);

/*==============================================================*/
/* Index: DG_FEATURE_IDX4                                       */
/*==============================================================*/
create  index DG_FEATURE_IDX4 on DG_FEATURE using GIST (
GEOMETRY
);



/*==============================================================*/
/* Constraints                                                  */
/*==============================================================*/

alter table DG_AOI
   add constraint FK_DG_AOI_REFERENCE_DG_USER foreign key (OWNER_ID)
      references DG_USER (ID)
      on delete restrict on update restrict;


alter table DG_AOI_USER_PREFERENCE
   add constraint DG_AOI_USER_PREFERENCE_AOI_ID_FK foreign key (DG_AOI_ID)
      references DG_AOI (ID)
      on delete restrict on update restrict;

alter table DG_AOI_USER_PREFERENCE
   add constraint DG_AOI_USER_PREFERENCE_USER_ID_FK foreign key (DG_USER_ID)
      references DG_USER (ID)
      on delete restrict on update restrict;


alter table DG_FEATURE
   add constraint DG_FEATURE_AOI_ID_FK foreign key (dg_aoi_id)
      references DG_AOI (ID)
      on delete restrict on update restrict;

alter table DG_FEATURE
   add constraint dg_feature_user_id_fk foreign key (DG_USER_ID)
      references DG_USER (ID)
      on delete restrict on update restrict;


alter table VERSION_CONTENT
   add constraint version_content_dv_id_fk foreign key (DATABASE_VERSION_ID)
      references DATABASE_VERSION (ID)
      on delete restrict on update restrict;



/*==============================================================*/
/* ro_grants                                                    */
/*==============================================================*/

/* grant select on dg_feature to dgwatch_test;
grant select on dg_user to dgwatch_test;
grant select on dg_registration to dgwatch_test;
grant select on dg_aoi to dgwatch_test;
grant select on dg_aoi_user_preference to dgwatch_test;

grant execute on function fixsequences() to dgwatch_test;
grant execute on function getlookupid(character varying, character varying) to dgwatch_test; */



/*==============================================================*/
/* rw_grants                                                    */
/*==============================================================*/

grant insert,update,delete,select on dg_feature to geosolutions_test;
grant insert,update,delete,select on dg_user to geosolutions_test;
grant insert,update,delete,select on dg_aoi to geosolutions_test;
grant insert,update,delete,select on dg_aoi_user_preference to geosolutions_test;

grant select,update on dg_feature_seq to geosolutions_test;
grant select,update on dg_user_seq to geosolutions_test;
grant select,update on dg_aoi_seq to geosolutions_test;

grant execute on function fixsequences() to geosolutions_test;
grant execute on function getlookupid(character varying, character varying) to geosolutions_test;