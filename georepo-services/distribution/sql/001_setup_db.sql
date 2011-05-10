-- CREATE SCHEMA georepo
CREATE user georepo LOGIN PASSWORD 'georepo' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;

CREATE SCHEMA georepo;

GRANT USAGE ON SCHEMA georepo TO georepo;
GRANT ALL ON SCHEMA georepo TO georepo;

GRANT SELECT ON public.spatial_ref_sys to georepo;
GRANT SELECT,INSERT,DELETE ON public.geometry_columns to georepo;

alter user georepo set search_path to georepo, public;

-- CREATE SCHEMA georepo_test
CREATE user georepo_test LOGIN PASSWORD 'georepo_test' NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;

CREATE SCHEMA georepo_test;

GRANT USAGE ON SCHEMA georepo_test TO georepo_test;
GRANT ALL ON SCHEMA georepo_test TO georepo_test;

GRANT SELECT ON public.spatial_ref_sys to georepo_test;
GRANT SELECT,INSERT,DELETE ON public.geometry_columns to georepo_test;

alter user georepo_test set search_path to georepo_test, public;
