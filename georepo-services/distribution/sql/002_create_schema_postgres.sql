--
-- PostgreSQL database dump
--

-- Started on 2011-11-02 18:59:51

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 7 (class 2615 OID 17524)
-- Name: georepo; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA georepo;


ALTER SCHEMA georepo OWNER TO postgres;

SET search_path = georepo, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 2318 (class 1259 OID 17942)
-- Dependencies: 7
-- Name: gr_gruser; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
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


ALTER TABLE georepo.gr_gruser OWNER TO georepo;

--
-- TOC entry 2319 (class 1259 OID 17954)
-- Dependencies: 7
-- Name: gr_gsinstance; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
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


ALTER TABLE georepo.gr_gsinstance OWNER TO georepo;

--
-- TOC entry 2320 (class 1259 OID 17962)
-- Dependencies: 994 7
-- Name: gr_gsuser; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
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


ALTER TABLE georepo.gr_gsuser OWNER TO georepo;

--
-- TOC entry 2321 (class 1259 OID 17974)
-- Dependencies: 7
-- Name: gr_layer_attributes; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
--

CREATE TABLE gr_layer_attributes (
    details_id bigint NOT NULL,
    access_type character varying(255),
    data_type character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE georepo.gr_layer_attributes OWNER TO georepo;

--
-- TOC entry 2322 (class 1259 OID 17982)
-- Dependencies: 7
-- Name: gr_layer_custom_props; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
--

CREATE TABLE gr_layer_custom_props (
    details_id bigint NOT NULL,
    propvalue character varying(255),
    propkey character varying(255) NOT NULL
);


ALTER TABLE georepo.gr_layer_custom_props OWNER TO georepo;

--
-- TOC entry 2323 (class 1259 OID 17990)
-- Dependencies: 994 7
-- Name: gr_layer_details; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
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


ALTER TABLE georepo.gr_layer_details OWNER TO georepo;

--
-- TOC entry 2324 (class 1259 OID 18000)
-- Dependencies: 7
-- Name: gr_layer_styles; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
--

CREATE TABLE gr_layer_styles (
    details_id bigint NOT NULL,
    stylename character varying(255)
);


ALTER TABLE georepo.gr_layer_styles OWNER TO georepo;

--
-- TOC entry 2325 (class 1259 OID 18003)
-- Dependencies: 7
-- Name: gr_profile; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
--

CREATE TABLE gr_profile (
    id bigint NOT NULL,
    datecreation timestamp without time zone,
    enabled boolean NOT NULL,
    extid character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE georepo.gr_profile OWNER TO georepo;

--
-- TOC entry 2326 (class 1259 OID 18015)
-- Dependencies: 7
-- Name: gr_profile_custom_props; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
--

CREATE TABLE gr_profile_custom_props (
    profile_id bigint NOT NULL,
    propvalue character varying(255),
    propkey character varying(255) NOT NULL
);


ALTER TABLE georepo.gr_profile_custom_props OWNER TO georepo;

--
-- TOC entry 2327 (class 1259 OID 18023)
-- Dependencies: 7
-- Name: gr_rule; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
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


ALTER TABLE georepo.gr_rule OWNER TO georepo;

--
-- TOC entry 2328 (class 1259 OID 18033)
-- Dependencies: 994 7
-- Name: gr_rule_limits; Type: TABLE; Schema: georepo; Owner: georepo; Tablespace: 
--

CREATE TABLE gr_rule_limits (
    id bigint NOT NULL,
    area public.geometry,
    rule_id bigint NOT NULL
);


ALTER TABLE georepo.gr_rule_limits OWNER TO georepo;

--
-- TOC entry 2329 (class 1259 OID 18043)
-- Dependencies: 7
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: georepo; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE georepo.hibernate_sequence OWNER TO georepo;

--
-- TOC entry 2624 (class 2606 OID 17951)
-- Dependencies: 2318 2318
-- Name: gr_gruser_extid_key; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_gruser
    ADD CONSTRAINT gr_gruser_extid_key UNIQUE (extid);


--
-- TOC entry 2626 (class 2606 OID 17953)
-- Dependencies: 2318 2318
-- Name: gr_gruser_name_key; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_gruser
    ADD CONSTRAINT gr_gruser_name_key UNIQUE (name);


--
-- TOC entry 2628 (class 2606 OID 17949)
-- Dependencies: 2318 2318
-- Name: gr_gruser_pkey; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_gruser
    ADD CONSTRAINT gr_gruser_pkey PRIMARY KEY (id);


--
-- TOC entry 2630 (class 2606 OID 17961)
-- Dependencies: 2319 2319
-- Name: gr_gsinstance_pkey; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_gsinstance
    ADD CONSTRAINT gr_gsinstance_pkey PRIMARY KEY (id);


--
-- TOC entry 2632 (class 2606 OID 17971)
-- Dependencies: 2320 2320
-- Name: gr_gsuser_extid_key; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_gsuser
    ADD CONSTRAINT gr_gsuser_extid_key UNIQUE (extid);


--
-- TOC entry 2634 (class 2606 OID 17973)
-- Dependencies: 2320 2320
-- Name: gr_gsuser_name_key; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_gsuser
    ADD CONSTRAINT gr_gsuser_name_key UNIQUE (name);


--
-- TOC entry 2636 (class 2606 OID 17969)
-- Dependencies: 2320 2320
-- Name: gr_gsuser_pkey; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_gsuser
    ADD CONSTRAINT gr_gsuser_pkey PRIMARY KEY (id);


--
-- TOC entry 2638 (class 2606 OID 17981)
-- Dependencies: 2321 2321 2321
-- Name: gr_layer_attributes_pkey; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_layer_attributes
    ADD CONSTRAINT gr_layer_attributes_pkey PRIMARY KEY (details_id, name);


--
-- TOC entry 2640 (class 2606 OID 17989)
-- Dependencies: 2322 2322 2322
-- Name: gr_layer_custom_props_pkey; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_layer_custom_props
    ADD CONSTRAINT gr_layer_custom_props_pkey PRIMARY KEY (details_id, propkey);


--
-- TOC entry 2642 (class 2606 OID 17997)
-- Dependencies: 2323 2323
-- Name: gr_layer_details_pkey; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_layer_details
    ADD CONSTRAINT gr_layer_details_pkey PRIMARY KEY (id);


--
-- TOC entry 2644 (class 2606 OID 17999)
-- Dependencies: 2323 2323
-- Name: gr_layer_details_rule_id_key; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_layer_details
    ADD CONSTRAINT gr_layer_details_rule_id_key UNIQUE (rule_id);


--
-- TOC entry 2652 (class 2606 OID 18022)
-- Dependencies: 2326 2326 2326
-- Name: gr_profile_custom_props_pkey; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_profile_custom_props
    ADD CONSTRAINT gr_profile_custom_props_pkey PRIMARY KEY (profile_id, propkey);


--
-- TOC entry 2646 (class 2606 OID 18012)
-- Dependencies: 2325 2325
-- Name: gr_profile_extid_key; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_profile
    ADD CONSTRAINT gr_profile_extid_key UNIQUE (extid);


--
-- TOC entry 2648 (class 2606 OID 18014)
-- Dependencies: 2325 2325
-- Name: gr_profile_name_key; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_profile
    ADD CONSTRAINT gr_profile_name_key UNIQUE (name);


--
-- TOC entry 2650 (class 2606 OID 18010)
-- Dependencies: 2325 2325
-- Name: gr_profile_pkey; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_profile
    ADD CONSTRAINT gr_profile_pkey PRIMARY KEY (id);


--
-- TOC entry 2654 (class 2606 OID 18032)
-- Dependencies: 2327 2327 2327 2327 2327 2327 2327 2327
-- Name: gr_rule_gsuser_id_key; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_rule
    ADD CONSTRAINT gr_rule_gsuser_id_key UNIQUE (gsuser_id, profile_id, instance_id, service, request, workspace, layer);


--
-- TOC entry 2658 (class 2606 OID 18040)
-- Dependencies: 2328 2328
-- Name: gr_rule_limits_pkey; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_rule_limits
    ADD CONSTRAINT gr_rule_limits_pkey PRIMARY KEY (id);


--
-- TOC entry 2660 (class 2606 OID 18042)
-- Dependencies: 2328 2328
-- Name: gr_rule_limits_rule_id_key; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_rule_limits
    ADD CONSTRAINT gr_rule_limits_rule_id_key UNIQUE (rule_id);


--
-- TOC entry 2656 (class 2606 OID 18030)
-- Dependencies: 2327 2327
-- Name: gr_rule_pkey; Type: CONSTRAINT; Schema: georepo; Owner: georepo; Tablespace: 
--

ALTER TABLE ONLY gr_rule
    ADD CONSTRAINT gr_rule_pkey PRIMARY KEY (id);


--
-- TOC entry 2662 (class 2606 OID 18051)
-- Dependencies: 2323 2321 2641
-- Name: fk_attribute_layer; Type: FK CONSTRAINT; Schema: georepo; Owner: georepo
--

ALTER TABLE ONLY gr_layer_attributes
    ADD CONSTRAINT fk_attribute_layer FOREIGN KEY (details_id) REFERENCES gr_layer_details(id);


--
-- TOC entry 2663 (class 2606 OID 18056)
-- Dependencies: 2322 2641 2323
-- Name: fk_custom_layer; Type: FK CONSTRAINT; Schema: georepo; Owner: georepo
--

ALTER TABLE ONLY gr_layer_custom_props
    ADD CONSTRAINT fk_custom_layer FOREIGN KEY (details_id) REFERENCES gr_layer_details(id);


--
-- TOC entry 2666 (class 2606 OID 18071)
-- Dependencies: 2649 2326 2325
-- Name: fk_custom_profile; Type: FK CONSTRAINT; Schema: georepo; Owner: georepo
--

ALTER TABLE ONLY gr_profile_custom_props
    ADD CONSTRAINT fk_custom_profile FOREIGN KEY (profile_id) REFERENCES gr_profile(id);


--
-- TOC entry 2664 (class 2606 OID 18061)
-- Dependencies: 2655 2327 2323
-- Name: fk_details_rule; Type: FK CONSTRAINT; Schema: georepo; Owner: georepo
--

ALTER TABLE ONLY gr_layer_details
    ADD CONSTRAINT fk_details_rule FOREIGN KEY (rule_id) REFERENCES gr_rule(id);


--
-- TOC entry 2670 (class 2606 OID 18091)
-- Dependencies: 2328 2655 2327
-- Name: fk_limits_rule; Type: FK CONSTRAINT; Schema: georepo; Owner: georepo
--

ALTER TABLE ONLY gr_rule_limits
    ADD CONSTRAINT fk_limits_rule FOREIGN KEY (rule_id) REFERENCES gr_rule(id);


--
-- TOC entry 2669 (class 2606 OID 18086)
-- Dependencies: 2319 2327 2629
-- Name: fk_rule_instance; Type: FK CONSTRAINT; Schema: georepo; Owner: georepo
--

ALTER TABLE ONLY gr_rule
    ADD CONSTRAINT fk_rule_instance FOREIGN KEY (instance_id) REFERENCES gr_gsinstance(id);


--
-- TOC entry 2668 (class 2606 OID 18081)
-- Dependencies: 2325 2327 2649
-- Name: fk_rule_profile; Type: FK CONSTRAINT; Schema: georepo; Owner: georepo
--

ALTER TABLE ONLY gr_rule
    ADD CONSTRAINT fk_rule_profile FOREIGN KEY (profile_id) REFERENCES gr_profile(id);


--
-- TOC entry 2667 (class 2606 OID 18076)
-- Dependencies: 2635 2327 2320
-- Name: fk_rule_user; Type: FK CONSTRAINT; Schema: georepo; Owner: georepo
--

ALTER TABLE ONLY gr_rule
    ADD CONSTRAINT fk_rule_user FOREIGN KEY (gsuser_id) REFERENCES gr_gsuser(id);


--
-- TOC entry 2665 (class 2606 OID 18066)
-- Dependencies: 2324 2323 2641
-- Name: fk_styles_layer; Type: FK CONSTRAINT; Schema: georepo; Owner: georepo
--

ALTER TABLE ONLY gr_layer_styles
    ADD CONSTRAINT fk_styles_layer FOREIGN KEY (details_id) REFERENCES gr_layer_details(id);


--
-- TOC entry 2661 (class 2606 OID 18046)
-- Dependencies: 2320 2649 2325
-- Name: fk_user_profile; Type: FK CONSTRAINT; Schema: georepo; Owner: georepo
--

ALTER TABLE ONLY gr_gsuser
    ADD CONSTRAINT fk_user_profile FOREIGN KEY (profile_id) REFERENCES gr_profile(id);


--
-- TOC entry 2673 (class 0 OID 0)
-- Dependencies: 7
-- Name: georepo; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA georepo FROM PUBLIC;
REVOKE ALL ON SCHEMA georepo FROM postgres;
GRANT ALL ON SCHEMA georepo TO postgres;
GRANT ALL ON SCHEMA georepo TO georepo;


-- Completed on 2011-11-02 18:59:51

--
-- PostgreSQL database dump complete
--

