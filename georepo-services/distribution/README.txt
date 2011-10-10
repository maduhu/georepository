==================================================================================
======= Installation Overview
==================================================================================

Create DB as user postgres

  createdb georepo
  createlang plpgsql georepo
  psql -d georepo -f /usr/share/postgresql/9.1/contrib/postgis-1.5/postgis.sql
  psql -d georepo -f /usr/share/postgresql/9.1/contrib/postgis-1.5/spatial_ref_sys.sql
  psql -d georepo -f /usr/share/postgresql/9.1/contrib/postgis_comments.sql

Run as user postgres the script 001_setup_db.sql
  psql -d georepo -f 001_setup_db.sql
