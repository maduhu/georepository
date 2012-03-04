--
-- PostgreSQL database dump
--

-- Started on 2012-03-03 17:47:40

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = georepo, pg_catalog;

--
-- TOC entry 2645 (class 0 OID 0)
-- Dependencies: 2290
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: georepo; Owner: georepo
--

SELECT pg_catalog.setval('hibernate_sequence', 1, true);


--
-- TOC entry 2632 (class 0 OID 62331)
-- Dependencies: 2279
-- Data for Name: gr_gruser; Type: TABLE DATA; Schema: georepo; Owner: georepo
--

INSERT INTO gr_gruser (id, datecreation, emailaddress, enabled, extid, fullname, name, password) VALUES (1, '2012-02-16 18:51:42.87', NULL, true, NULL, 'admin', 'admin', '21232f297a57a5a743894ae4a801fc3');


--
-- TOC entry 2633 (class 0 OID 62343)
-- Dependencies: 2280
-- Data for Name: gr_gsinstance; Type: TABLE DATA; Schema: georepo; Owner: georepo
--

INSERT INTO gr_gsinstance (id, baseurl, datecreation, description, name, password, username) VALUES (2, 'http://localhost:8181/geoserver/', NULL, NULL, 'default-gs', 'admin', 'admin');


--
-- TOC entry 2639 (class 0 OID 62392)
-- Dependencies: 2286
-- Data for Name: gr_profile; Type: TABLE DATA; Schema: georepo; Owner: georepo
--

INSERT INTO gr_profile (id, datecreation, enabled, extid, name) VALUES (3, '2012-02-16 09:24:12.712', true, NULL, 'Analisi');
INSERT INTO gr_profile (id, datecreation, enabled, extid, name) VALUES (4, '2012-02-16 09:24:12.768', true, NULL, 'Avanzato');
INSERT INTO gr_profile (id, datecreation, enabled, extid, name) VALUES (5, '2012-02-16 09:24:12.78', true, NULL, 'Base');


--
-- TOC entry 2634 (class 0 OID 62351)
-- Dependencies: 2281 2639
-- Data for Name: gr_gsuser; Type: TABLE DATA; Schema: georepo; Owner: georepo
--

INSERT INTO gr_gsuser (id, admin, allowedarea, datecreation, emailaddress, enabled, extid, fullname, name, password, profile_id) VALUES (6, true, NULL, '2012-02-16 09:24:12.79', 'admin@georepo.it', true, NULL, 'admin', 'admin', 'admin', 29);


