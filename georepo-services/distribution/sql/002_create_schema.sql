
SET search_path = dgwatch, pg_catalog, public;

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
  AND pg_user.usename = 'dgwatch' 
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

/*==============================================================*/

create sequence DG_AOI_SEQ
increment 1
minvalue 15000
start 15000;

create sequence DG_FEATURE_SEQ
increment 1
minvalue 15000
start 15000;

create sequence DG_WATCH_SEQ
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
/* Table: DG_ACTION hierarchy                                   */
/*==============================================================*/

    create table dg_action (
        id int8 not null,
        primary key (id)
    );

    create table dg_distribution (
        content_type varchar(32) not null,
        id int8 not null,
        primary key (id)
    );

    create table dg_distribution_destinationNodeIds (
        dg_distribution_id int8 not null,
        nodeId int8 not null,
        primary key (dg_distribution_id, nodeId)
    );

    create table dg_notification (
        content_type varchar(32) not null,
        mailContent varchar(255),
        notification_type varchar(32) not null,
        retrieval_type varchar(32) not null,
        timer varchar(32) not null,
        id int8 not null,
        primary key (id)
    );

    create table dg_notification_mailDestinations (
        dg_notification_id int8 not null,
        mailAddress varchar(255) not null,
        primary key (dg_notification_id, mailAddress)
    );


/*==============================================================*/
/* Table: DG_AOI                                                */
/*==============================================================*/

create table DG_AOI (
    id int8 not null,
    area float4,
    creation_time timestamp WITH TIME ZONE not null,
    geometry geometry not null,
    last_update_time timestamp WITH TIME ZONE,
    title varchar(255) not null,
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
where f_table_schema='dgwatch'
and f_table_name='dg_aoi'
and f_geometry_column='geometry';

insert into geometry_columns values('', 'dgwatch', 'dg_aoi', 'geometry', 2, 4326, 'GEOMETRY');

/*==============================================================*/
/* Index: DG_AOI_IDX1                                           */
/*==============================================================*/
create  index DG_AOI_IDX1 on DG_AOI using GIST (
GEOMETRY
);


/*==============================================================*/
/* Table: DG_WATCH                                              */
/*==============================================================*/

    create table dg_watch (
        id int8 not null,
        filter_acquisition_time timestamp,
        filter_acquisition_time_op varchar(255),
        filter_cloud_cover float4,
        filter_cloud_cover_op varchar(255),
        filter_data_layer varchar(255),
        filter_data_layer_op varchar(255),
        filter_legacy_id varchar(255),
        filter_legacy_id_op varchar(255),
        filter_source_id varchar(255),
        filter_source_id_op varchar(255),
        begin_valid_date timestamp,
        end_valid_date timestamp,
        member_connect_id varchar(255) not null,
        member_name varchar(255) not null,
        is_runtime bool,
        title varchar(255),
        action_id int8 not null CONSTRAINT unique_action_id_in_watch UNIQUE,
        aoi_id int8 not null,

   constraint DG_WATCH_PK primary key (ID)

--        primary key (id),
--        unique (action_id)
    );

/*==============================================================*/
/* Table: DG_FEATURE                                            */
/*==============================================================*/
create table DG_FEATURE (

        id int8 not null,
        creation_time timestamp WITH TIME ZONE,
        external_identifier varchar(255),
        geometry geometry not null,
        last_sent_gcdn_time timestamp WITH TIME ZONE,
        last_sent_mail_time timestamp WITH TIME ZONE,
        last_sent_rss_time timestamp WITH TIME ZONE,
        title varchar(255),
        wfs_response varchar(4000),
        dg_watch_id int8 not null,
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
where f_table_schema='dgwatch'
and f_table_name='dg_feature'
and f_geometry_column='geometry';

insert into geometry_columns values('', 'dgwatch', 'dg_feature', 'geometry', 2, 4326, 'GEOMETRY');

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
dg_watch_id
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

/*
    alter table dg_feature              drop constraint fk_dg_feature_watch_id;
    alter table dg_notification         drop constraint fk_dg_notification_action;
    alter table dg_notification_mailDestinations        drop constraint fk_dg_notification_maildest;
    alter table dg_distribution         drop constraint fk_dg_distribution_action;
    alter table dg_distribution_destinationnodeids        drop constraint fk_dg_distribution_nodedest;
    alter table dg_watch                drop constraint fk_watch_action;
    alter table dg_watch                drop constraint FK_DG_WATCH_REFERENCE_DG_AOI;
*/

    alter table dg_feature
        add constraint fk_dg_feature_watch_id
        foreign key (dg_watch_id)
        references dg_watch
        on delete cascade;

    alter table dg_notification
        add constraint fk_dg_notification_action
        foreign key (id)
        references dg_action
        on delete cascade;

    alter table dg_notification_mailDestinations
        add constraint fk_dg_notification_maildest
        foreign key (dg_notification_id)
        references dg_notification
        on delete cascade;

    alter table dg_distribution
        add constraint fk_dg_distribution_action
        foreign key (id)
        references dg_action
        on delete cascade;

    alter table dg_distribution_destinationnodeids
        add constraint fk_dg_distribution_nodedest
        foreign key (dg_distribution_id)
        references dg_distribution
        on delete cascade;

    alter table dg_watch
        add constraint fk_watch_action
        foreign key (action_id)
        references dg_action;
-- TODO: cascading watch -> action, maybe with a trigger

    alter table dg_watch
        add constraint FK_DG_WATCH_REFERENCE_DG_AOI
        foreign key (aoi_id)
        references dg_aoi;



alter table VERSION_CONTENT
   add constraint version_content_dv_id_fk foreign key (DATABASE_VERSION_ID)
      references DATABASE_VERSION (ID)
      on delete restrict on update restrict;



/*==============================================================*/
/* ro_grants                                                    */
/*==============================================================*/


/*==============================================================*/
/* rw_grants                                                    */
/*==============================================================*/

grant insert,update,delete,select on dg_action                          to dgwatch;
grant insert,update,delete,select on dg_distribution                    to dgwatch;
grant insert,update,delete,select on dg_distribution_destinationNodeIds to dgwatch;
grant insert,update,delete,select on dg_notification                    to dgwatch;
grant insert,update,delete,select on dg_notification_mailDestinations   to dgwatch;
grant insert,update,delete,select on dg_aoi                             to dgwatch;
grant insert,update,delete,select on dg_watch                           to dgwatch;
grant insert,update,delete,select on dg_feature                         to dgwatch;

grant select,update on dg_feature_seq to dgwatch;
grant select,update on dg_aoi_seq to dgwatch;
grant select,update on dg_watch_seq to dgwatch;

grant execute on function fixsequences() to dgwatch;
grant execute on function getlookupid(character varying, character varying) to dgwatch;
