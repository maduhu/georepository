=============================
 + DB Preparation
=============================

Setup the DB schema by creating a PostGIS DB and creating the base schema using the SQL script:

  georepo-services/distribution/sql/001_setup_db.sql


=============================
 + DataScource Configuration
=============================

Update configuration files accordingly to your DB connection parameters on

  georepo-gui/fullweb/src/main/resources
  

=============================
 + GeoServer Instance Configuration
=============================

Update GeoServer properties accordingly to your configuration by updating the file

  georepo-geoserver/web-app/src/main/resources/georepo-geoserver.properties


=============================
 + Building WARs
=============================

From root directory launch the command

  mvn clean install
  
This will create two WARs:

  georepo-geoserver/web-app/target/geoserver.war
  georepo-gui/fullweb/target/georepo.war

