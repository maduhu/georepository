==================================================================================
======= Installation Overview
==================================================================================

The DGWS Watch application is deployed as several files
into ESB and a web container.  The following instructions
will get the application up and running in a short period of time.
You will want to make sure that the ESB and service assemblies
are running and deployed prior to starting and deploying the web application.


==================================================================================
======= Installing the DGWS Watch Server-Side Applications
==================================================================================

The server-side applications are implemented using the ESB. Below are
instructions for deploying them for use: 

1) Download the FUSE ESB source code v3.4.x from the FuseSource website here: 

    http://fusesource.com/product_download/fuse-esb-apache-servicemix/3-4-0-5/source

2) Compile FUSE project running Maven2 with the following command:
    
    mvn clean install -Pall -Dmaven.test.skip
    
3) Rename the war you find into

    .../apache-servicemix-3.4.0.5-fuse/src/distributions/apache-servicemix-web/target
    
   as "dgwatch-services.war", and deploy it into your Web Container (Tomcat)

4) Update files in $CATILINA_HOME/webapps/dgwatch-services/WEB-INF/lib folder.

  a) Libs in distribution/smix/smix3/lib/replace should replace the existing files
     in webapps/dgwatch-services/WEB-INF/lib:
     e.g.:
        - replace XmlSchema-1.3.2.jar with XmlSchema-1.4.5.jar
        - replace commons-lang-2.1.jar with commons-lang-2.3.jar

  b) Libs in distribution/smix/smix3/lib/add should by copied into
     webapps/dgwatch-services/WEB-INF/lib.


NOTE: Delete previous versions of XmlSchema jar

5) Startup again web container and go to

    http://<your_server_url:port>/dgwatch-services/shared-libraries.jsp
   
   Install and deploy here the file
   	
    .../apache-servicemix-3.4.0.5-fuse/src/distributions/apache-servicemix/target/components/servicemix-shared-2009.01.0.5-fuse-installer.zip
    
6) Go to

    http://<your_server_url:port>/dgwatch-services/components.jsp
    
   Install, deploy and start the components
   
   	servicemix-jsr181
		servicemix-validation
		servicemix-scripting
		servicemix-cxf-se
		servicemix-camel
		servicemix-mail
		servicemix-truezip
		servicemix-xmpp
		servicemix-quartz
		servicemix-script
		servicemix-cxf-bc
		servicemix-drools
		servicemix-jms
		servicemix-bean
		servicemix-file
		servicemix-http
		servicemix-ftp
		servicemix-eip

	You can find modules into 
	
	 .../apache-servicemix-3.4.0.5-fuse/src/distributions/apache-servicemix/target/components/

7) Deploy DGWS Watch JBI SA components using ServiceMIX interface at 

    http://<your_server_url:port>/dgwatch-services/service-assemblies.jsp
    
NOTE: Remember to start endopints once deployed


==================================================================================
======= DB setup
==================================================================================

Test environment
==================================================================================
      
In order to **BUILD** DGWS Watch, you need to create a PostGIS database where destructive tests will be run.
  - db name: dgwatch
  - user/pw: geosolutions_test/geosol_test
  - schema:  dgwatch_test

  You may want to run the tests on a different DB;
  to do so, you will have to change the DB references in these files:
    - dgwatch_esb/trunk/dgwatch-core/dgwatch-dao/src/test/resources/dgw_datasource_fixed.xml
    - dgwatch_esb/trunk/dgwatch-ws/dgwatch-ws-webapp/src/main/resources/dgw_datasource_fixed.xml
  Please also check the "Customizing DB parameters" section


Prod environment
==================================================================================

In order to **RUN** DGWS Watch, you need to create:
  1) a PostGIS database:
      - db name: dgwatch
      - user/pw: geosolutions/geosol
      - schema:  dgwatch

      You may want to set up a different production DB;
      to do so, you will have to change the DB references in file:
        - prod db: dgwatch_esb/trunk/dgwatch-jbi/dgwatch-service/dgwatch-aoi-ws-su/src/main/resources/dgw_datasource_fixed.xml
      Please also check the "Customizing DB parameters" section

  2) a PostgreSQL database:
     - db name: dgwatch
     - user/pw: dgw_quartz/quartz
     - schema:  quartz

SQL files
==================================================================================

You'll find a SQL script to create such DBs in
    trunk/distribution/sql/setupdb.sql

The DDL for the quartz DB is in:
    trunk/distribution/sql/quartz_152_create.sql

The DDL for the dgwatch and the dgwatch_test schemas will be located in
    dgwatch_esb/trunk/dgwatch-core/dgwatch-dao/target/classes/dgwatch.ddl
once the dgwatch-core/dgwatch-dao will have been built.
After the whole project has been built, a copy will be placed in
    trunk/distribution/sql/dgwatch.ddl


Customizing DB parameters
==================================================================================

When editing "dgw_datasource_fixed.xml" files, you'll find the connection params
in lines like these:

    ...
		<property name="driverClassName" value="org.postgresql.Driver" />
		<property name="url" value="jdbc:postgresql://localhost:5432/dgwatch" />
		<property name="username" value="geosolutions" />
		<property name="password" value="geosol" />
    ...
       <entry key="hibernate.default_schema" value="dgwatch" />


If you do not want Hibernate to create/update the schema, you have to look into 
the aforementioned files for a line like this

		<property name="generateDdl" value="true" />

changing the value to "false".


The connection params related to the quartz job store have been externalized and can be configured
in the dgws-config.xml file that should be a part of all installations.

==================================================================================
======= Service Assemblies Configuration and Deployment
==================================================================================
NOTE: before proceed with installation, please make ALWAYS sure the jars contained into
      dgwatch/trunk/distribution/smix/smix3/lib/ext
      are aligned with your $CATALINA_HOME/webapps/dgwatch-services/WEB-INF/lib
      ones



Security Service Configuration:
===============================
1) The file
 
  - dgwatch_esb/trunk/dgwatch-jbi/dgwatch-security/security-service/security-manager-su/src/main/resources/xbean.xml

contains the configuration parameters for the security service. 
In particular you need to change there the "serviceUrl" value of the "memberWebService" bean in order to point to a running instance of DG memberServices

Example:
   	<bean id="memberWebService" class="com.digitalglobe.springframework.remoting.httpinvoker.InstrumentedHttpInvokerProxyFactoryBean"
            lazy-init="true">
        <property name="serviceUrl" value="http://localhost:8181/memberServices/internal/http/Member"/>    <!--------- Check this ---->
        <property name="serviceInterface" value="com.digitalglobe.dgp.member.service.MemberServiceInternal"/>
    </bean>


2) Once compiled and deployed on ServiceMIX web application the JBI security service can be found at

 http://<your_server:port>/dgwatch-services/jbi/securityService/

The URL above must be written into the dgwatch gui spring security application context which can be found at

  - dgwatch-gui/trunk/core/dgwatch-plugin/dgwatch-userui/src/main/resources/applicationContext-Security-Rmi.xml

Example:
	<bean id="remoteAuthenticationManager"
		class=" org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean">
		<property name="serviceUrl" value="http://<your_server:port>/dgwatch-services/jbi/securityService/" />    <!--------- Check this ---->
		<property name="serviceInterface" value="org.springframework.security.authentication.rcp.RemoteAuthenticationManager" />
		<property name="httpInvokerRequestExecutor">
			<bean class="org.springframework.remoting.httpinvoker.CommonsHttpInvokerRequestExecutor" />
		</property>
	</bean>


AOI Management Service Configuration:
=====================================
1) You also have to setup the AOIManament service URI in the file

  - dgwatch_esb/trunk/dgwatch-jbi/dgwatch-service/dgwatch-aoi-consumer-soaphttp-bc/src/main/resources/xbean.xml

editing the line
    locationURI="http://demo.geo-solutions.it:9090/AOIManagement"
and setting your own server URI.

Example:
	<cxfbc:consumer wsdl="classpath:dgwatch.wsdl"
                  targetService="dgwatchservice:AOIManagementService"
                  locationURI="http://demo.geo-solutions.it:9090/AOIManagement"    <!--------- Check this ---->
                  timeout="20000"/>

NOTE: restart tomcat in order to allow ServiceMIX to open sockets correctly everytime you change this parameter.

2) This service is accessed by the GUI service, so the same URI has to be configured
there as well.
Edit the file

  - dgwatch_gui/trunk/core/dgwatch-resources/src/main/resources/applicationContext.xml

Example:
	<bean id="dgwatchProxyFactory" class="org.apache.cxf.jaxws.JaxWsProxyFactoryBean">
		<property name="serviceClass"
			value="it.geosolutions.georepo.services.DGWatchService" />
		<property name="address"
			value="http://demo.geo-solutions.it:9090/AOIManagement" />    <!--------- Check this ---->
	</bean>


User validation configuration
=====================================
When users are created, they will be sent a validation e-mail.
You'll need to configure:
 - The mail template
    trunk/dgwatch-ws/dgwatch-ws-impl/src/main/java/it.geosolutions.georepo/activation.vm
 - The mail server the mail will be sent by.
   Open the file
       trunk/dgwatch-ws/dgwatch-ws-impl/src/main/resources/applicationContext.xml
   and edit the lines
       <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
           <property name="host" value="mail.geo-solutions.it"/>    <!--------- Check this ---->


WFS configuration
=====================================
You need to configure the WFS server URL.
Open the file
    trunk/dgwatch-jbi/dgwatch-service/dgwatch-wfsretriever-se/src/main/resources/xbean.xml
end edit the lines
    <bean id="wfsRetrieverService" class="it.geosolutions.georepo.wfsretriever.WFSRetrieverServiceImpl">
        <!-- these values have to be customized ============================ -->
        <property name="getCapabilitiesUrl" value="http://localhost:8080/geoserver/wfs?service=WFS&amp;request=GetCapabilities" />    <!--------- Check this ---->
        <property name="typeName"           value="com.digitalglobe:finished_feature_vw" />                                            <!--------- Check this ---->
    </bean>


RSS mailing configuration
=====================================
You need to configure the mail server the RSS info will be sent by.
Open the file
    trunk/dgwatch-jbi/dgwatch-service/dgwatch-publishing-se/src/main/resources/xbean.xml

and edit the lines
        <property name="getCapabilitiesUrl" value="http://localhost:8080/geoserver/wfs?service=WFS&amp;request=GetCapabilities" />    <!--------- Check this ---->
        <property name="typeName"           value="com.digitalglobe:finished_feature_vw" />                                            <!--------- Check this ---->

   <bean id="mailHandler" class="it.geosolutions.georepo.rsspublisher.MailHandler">
       ...
       <!-- this property has to be customized ***************************** -->
       <property name="from" value="dgwatch@geo-solutions.it"/>    <!--------- Check this ---->

   <bean id="rssMailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
       <!-- this property has to be customized ***************************** -->
      <property name="host" value="mail.geo-solutions.it"/>    <!--------- Check this ---->


