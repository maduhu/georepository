--
-- PostgreSQL database dump
--

-- Started on 2012-03-03 17:36:12

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 7 (class 2615 OID 50644)
-- Name: georepo; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA georepo;


SET search_path = georepo, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 2300 (class 1259 OID 62331)
-- Dependencies: 7
-- Name: gr_gruser; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_gruser (
    id bigint NOT NULL,
    datecreation timestamp without time zone,
    emailaddress character varying(255),
    enabled boolean NOT NULL,
    extid character varying(255),
    fullname character varying(255),
    name character varying(255) NOT NULL,
    password character varying(255)
);


--
-- TOC entry 2301 (class 1259 OID 62343)
-- Dependencies: 7
-- Name: gr_gsinstance; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_gsinstance (
    id bigint NOT NULL,
    baseurl character varying(255) NOT NULL,
    datecreation timestamp without time zone,
    description character varying(255),
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


--
-- TOC entry 2302 (class 1259 OID 62351)
-- Dependencies: 7 992
-- Name: gr_gsuser; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_gsuser (
    id bigint NOT NULL,
    admin boolean NOT NULL,
    allowedarea public.geometry,
    datecreation timestamp without time zone,
    emailaddress character varying(255),
    enabled boolean NOT NULL,
    extid character varying(255),
    fullname character varying(255),
    name character varying(255) NOT NULL,
    password character varying(255),
    profile_id bigint NOT NULL
);


--
-- TOC entry 2303 (class 1259 OID 62363)
-- Dependencies: 7
-- Name: gr_layer_attributes; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_layer_attributes (
    details_id bigint NOT NULL,
    access_type character varying(255),
    data_type character varying(255),
    name character varying(255) NOT NULL
);


--
-- TOC entry 2304 (class 1259 OID 62371)
-- Dependencies: 7
-- Name: gr_layer_custom_props; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_layer_custom_props (
    details_id bigint NOT NULL,
    propvalue character varying(255),
    propkey character varying(255) NOT NULL
);


--
-- TOC entry 2305 (class 1259 OID 62379)
-- Dependencies: 992 7
-- Name: gr_layer_details; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_layer_details (
    id bigint NOT NULL,
    area public.geometry,
    cqlfilterread character varying(4000),
    cqlfilterwrite character varying(4000),
    defaultstyle character varying(255),
    type character varying(255),
    rule_id bigint NOT NULL
);


--
-- TOC entry 2306 (class 1259 OID 62389)
-- Dependencies: 7
-- Name: gr_layer_styles; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_layer_styles (
    details_id bigint NOT NULL,
    stylename character varying(255)
);


--
-- TOC entry 2307 (class 1259 OID 62392)
-- Dependencies: 7
-- Name: gr_profile; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_profile (
    id bigint NOT NULL,
    datecreation timestamp without time zone,
    enabled boolean NOT NULL,
    extid character varying(255),
    name character varying(255) NOT NULL
);


--
-- TOC entry 2308 (class 1259 OID 62404)
-- Dependencies: 7
-- Name: gr_profile_custom_props; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_profile_custom_props (
    profile_id bigint NOT NULL,
    propvalue character varying(255),
    propkey character varying(255) NOT NULL
);


--
-- TOC entry 2309 (class 1259 OID 62412)
-- Dependencies: 7
-- Name: gr_rule; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_rule (
    id bigint NOT NULL,
    grant_type character varying(255) NOT NULL,
    layer character varying(255),
    priority bigint NOT NULL,
    request character varying(255),
    service character varying(255),
    workspace character varying(255),
    gsuser_id bigint,
    instance_id bigint,
    profile_id bigint
);


--
-- TOC entry 2310 (class 1259 OID 62422)
-- Dependencies: 992 7
-- Name: gr_rule_limits; Type: TABLE; Schema: georepo; Owner: -; Tablespace: 
--

CREATE TABLE gr_rule_limits (
    id bigint NOT NULL,
    area public.geometry,
    rule_id bigint NOT NULL
);

--
-- TOC entry 2311 (class 1259 OID 62482)
-- Dependencies: 7
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: georepo; Owner: -
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 2614 (class 2606 OID 62340)
-- Dependencies: 2300 2300
-- Name: gr_gruser_extid_key; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_gruser
    ADD CONSTRAINT gr_gruser_extid_key UNIQUE (extid);


--
-- TOC entry 2616 (class 2606 OID 62342)
-- Dependencies: 2300 2300
-- Name: gr_gruser_name_key; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_gruser
    ADD CONSTRAINT gr_gruser_name_key UNIQUE (name);


--
-- TOC entry 2618 (class 2606 OID 62338)
-- Dependencies: 2300 2300
-- Name: gr_gruser_pkey; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_gruser
    ADD CONSTRAINT gr_gruser_pkey PRIMARY KEY (id);


--
-- TOC entry 2620 (class 2606 OID 62350)
-- Dependencies: 2301 2301
-- Name: gr_gsinstance_pkey; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_gsinstance
    ADD CONSTRAINT gr_gsinstance_pkey PRIMARY KEY (id);


--
-- TOC entry 2622 (class 2606 OID 62360)
-- Dependencies: 2302 2302
-- Name: gr_gsuser_extid_key; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_gsuser
    ADD CONSTRAINT gr_gsuser_extid_key UNIQUE (extid);


--
-- TOC entry 2624 (class 2606 OID 62362)
-- Dependencies: 2302 2302
-- Name: gr_gsuser_name_key; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_gsuser
    ADD CONSTRAINT gr_gsuser_name_key UNIQUE (name);


--
-- TOC entry 2626 (class 2606 OID 62358)
-- Dependencies: 2302 2302
-- Name: gr_gsuser_pkey; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_gsuser
    ADD CONSTRAINT gr_gsuser_pkey PRIMARY KEY (id);


--
-- TOC entry 2628 (class 2606 OID 62370)
-- Dependencies: 2303 2303 2303
-- Name: gr_layer_attributes_pkey; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_layer_attributes
    ADD CONSTRAINT gr_layer_attributes_pkey PRIMARY KEY (details_id, name);


--
-- TOC entry 2630 (class 2606 OID 62378)
-- Dependencies: 2304 2304 2304
-- Name: gr_layer_custom_props_pkey; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_layer_custom_props
    ADD CONSTRAINT gr_layer_custom_props_pkey PRIMARY KEY (details_id, propkey);


--
-- TOC entry 2632 (class 2606 OID 62386)
-- Dependencies: 2305 2305
-- Name: gr_layer_details_pkey; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_layer_details
    ADD CONSTRAINT gr_layer_details_pkey PRIMARY KEY (id);


--
-- TOC entry 2634 (class 2606 OID 62388)
-- Dependencies: 2305 2305
-- Name: gr_layer_details_rule_id_key; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_layer_details
    ADD CONSTRAINT gr_layer_details_rule_id_key UNIQUE (rule_id);


--
-- TOC entry 2642 (class 2606 OID 62411)
-- Dependencies: 2308 2308 2308
-- Name: gr_profile_custom_props_pkey; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_profile_custom_props
    ADD CONSTRAINT gr_profile_custom_props_pkey PRIMARY KEY (profile_id, propkey);


--
-- TOC entry 2636 (class 2606 OID 62401)
-- Dependencies: 2307 2307
-- Name: gr_profile_extid_key; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_profile
    ADD CONSTRAINT gr_profile_extid_key UNIQUE (extid);


--
-- TOC entry 2638 (class 2606 OID 62403)
-- Dependencies: 2307 2307
-- Name: gr_profile_name_key; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_profile
    ADD CONSTRAINT gr_profile_name_key UNIQUE (name);


--
-- TOC entry 2640 (class 2606 OID 62399)
-- Dependencies: 2307 2307
-- Name: gr_profile_pkey; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_profile
    ADD CONSTRAINT gr_profile_pkey PRIMARY KEY (id);


--
-- TOC entry 2644 (class 2606 OID 62421)
-- Dependencies: 2309 2309 2309 2309 2309 2309 2309 2309
-- Name: gr_rule_gsuser_id_key; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_rule
    ADD CONSTRAINT gr_rule_gsuser_id_key UNIQUE (gsuser_id, profile_id, instance_id, service, request, workspace, layer);


--
-- TOC entry 2648 (class 2606 OID 62429)
-- Dependencies: 2310 2310
-- Name: gr_rule_limits_pkey; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_rule_limits
    ADD CONSTRAINT gr_rule_limits_pkey PRIMARY KEY (id);


--
-- TOC entry 2650 (class 2606 OID 62431)
-- Dependencies: 2310 2310
-- Name: gr_rule_limits_rule_id_key; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_rule_limits
    ADD CONSTRAINT gr_rule_limits_rule_id_key UNIQUE (rule_id);


--
-- TOC entry 2646 (class 2606 OID 62419)
-- Dependencies: 2309 2309
-- Name: gr_rule_pkey; Type: CONSTRAINT; Schema: georepo; Owner: -; Tablespace: 
--

ALTER TABLE ONLY gr_rule
    ADD CONSTRAINT gr_rule_pkey PRIMARY KEY (id);


--
-- TOC entry 2688 (class 2606 OID 62437)
-- Dependencies: 2631 2303 2305
-- Name: fk_attribute_layer; Type: FK CONSTRAINT; Schema: georepo; Owner: -
--

ALTER TABLE ONLY gr_layer_attributes
    ADD CONSTRAINT fk_attribute_layer FOREIGN KEY (details_id) REFERENCES gr_layer_details(id);


--
-- TOC entry 2689 (class 2606 OID 62442)
-- Dependencies: 2305 2631 2304
-- Name: fk_custom_layer; Type: FK CONSTRAINT; Schema: georepo; Owner: -
--

ALTER TABLE ONLY gr_layer_custom_props
    ADD CONSTRAINT fk_custom_layer FOREIGN KEY (details_id) REFERENCES gr_layer_details(id);


--
-- TOC entry 2692 (class 2606 OID 62457)
-- Dependencies: 2639 2308 2307
-- Name: fk_custom_profile; Type: FK CONSTRAINT; Schema: georepo; Owner: -
--

ALTER TABLE ONLY gr_profile_custom_props
    ADD CONSTRAINT fk_custom_profile FOREIGN KEY (profile_id) REFERENCES gr_profile(id);


--
-- TOC entry 2690 (class 2606 OID 62447)
-- Dependencies: 2309 2305 2645
-- Name: fk_details_rule; Type: FK CONSTRAINT; Schema: georepo; Owner: -
--

ALTER TABLE ONLY gr_layer_details
    ADD CONSTRAINT fk_details_rule FOREIGN KEY (rule_id) REFERENCES gr_rule(id);


--
-- TOC entry 2696 (class 2606 OID 62477)
-- Dependencies: 2310 2309 2645
-- Name: fk_limits_rule; Type: FK CONSTRAINT; Schema: georepo; Owner: -
--

ALTER TABLE ONLY gr_rule_limits
    ADD CONSTRAINT fk_limits_rule FOREIGN KEY (rule_id) REFERENCES gr_rule(id);


--
-- TOC entry 2695 (class 2606 OID 62472)
-- Dependencies: 2619 2309 2301
-- Name: fk_rule_instance; Type: FK CONSTRAINT; Schema: georepo; Owner: -
--

ALTER TABLE ONLY gr_rule
    ADD CONSTRAINT fk_rule_instance FOREIGN KEY (instance_id) REFERENCES gr_gsinstance(id);


--
-- TOC entry 2694 (class 2606 OID 62467)
-- Dependencies: 2309 2307 2639
-- Name: fk_rule_profile; Type: FK CONSTRAINT; Schema: georepo; Owner: -
--

ALTER TABLE ONLY gr_rule
    ADD CONSTRAINT fk_rule_profile FOREIGN KEY (profile_id) REFERENCES gr_profile(id);


--
-- TOC entry 2693 (class 2606 OID 62462)
-- Dependencies: 2309 2625 2302
-- Name: fk_rule_user; Type: FK CONSTRAINT; Schema: georepo; Owner: -
--

ALTER TABLE ONLY gr_rule
    ADD CONSTRAINT fk_rule_user FOREIGN KEY (gsuser_id) REFERENCES gr_gsuser(id);


--
-- TOC entry 2691 (class 2606 OID 62452)
-- Dependencies: 2306 2631 2305
-- Name: fk_styles_layer; Type: FK CONSTRAINT; Schema: georepo; Owner: -
--

ALTER TABLE ONLY gr_layer_styles
    ADD CONSTRAINT fk_styles_layer FOREIGN KEY (details_id) REFERENCES gr_layer_details(id);


--
-- TOC entry 2687 (class 2606 OID 62432)
-- Dependencies: 2307 2302 2639
-- Name: fk_user_profile; Type: FK CONSTRAINT; Schema: georepo; Owner: -
--

ALTER TABLE ONLY gr_gsuser
    ADD CONSTRAINT fk_user_profile FOREIGN KEY (profile_id) REFERENCES gr_profile(id);

--
-- TOC entry 2708 (class 0 OID 0)
-- Dependencies: 7
-- Name: georepo; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA georepo FROM PUBLIC;
REVOKE ALL ON SCHEMA georepo FROM postgres;
GRANT ALL ON SCHEMA georepo TO postgres;
GRANT ALL ON SCHEMA georepo TO georepo;


-- Completed on 2012-03-03 17:36:12

--
-- PostgreSQL database dump complete
--

