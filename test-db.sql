--
-- PostgreSQL database dump
--

-- Started on 2011-02-08 17:32:48

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = georepo_test, pg_catalog;

--
-- TOC entry 2644 (class 0 OID 0)
-- Dependencies: 2289
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: georepo_test; Owner: georepo_test
--

SELECT pg_catalog.setval('hibernate_sequence', 6, true);


--
-- TOC entry 2632 (class 0 OID 18703)
-- Dependencies: 2279
-- Data for Name: gr_gsinstance; Type: TABLE DATA; Schema: georepo_test; Owner: georepo_test
--

INSERT INTO gr_gsinstance (id, baseurl, datecreation, description, name) VALUES (1, 'http://demo.geo-solutions.it/geoserver', '2011-01-01 00:00:00', 'Demo GeoSolutions', 'demoGeoSolutions');


--
-- TOC entry 2638 (class 0 OID 18750)
-- Dependencies: 2285
-- Data for Name: gr_profile; Type: TABLE DATA; Schema: georepo_test; Owner: georepo_test
--

INSERT INTO gr_profile (id, datecreation, enabled, name) VALUES (1, '2011-01-01 00:00:00', true, 'BASE');
INSERT INTO gr_profile (id, datecreation, enabled, name) VALUES (2, '2011-01-01 00:00:00', true, 'ANALYSIS');
INSERT INTO gr_profile (id, datecreation, enabled, name) VALUES (3, '2011-01-01 00:00:00', true, 'ADVANCED');


--
-- TOC entry 2633 (class 0 OID 18711)
-- Dependencies: 2280 2638
-- Data for Name: gr_gsuser; Type: TABLE DATA; Schema: georepo_test; Owner: georepo_test
--

INSERT INTO gr_gsuser (id, allowedarea, datecreation, emailaddress, enabled, fullname, name, password, profile_id) VALUES (1, NULL, '2011-01-01 00:00:00', 'alessio.fabiani@gmail.com', true, 'Alessio Fabiani', 'afabiani', 'AlexAa', 1);


--
-- TOC entry 2640 (class 0 OID 18765)
-- Dependencies: 2287 2633 2638 2632
-- Data for Name: gr_rule; Type: TABLE DATA; Schema: georepo_test; Owner: georepo_test
--

INSERT INTO gr_rule (id, access, layer, priority, request, service, workspace, gsuser_id, instance_id, profile_id) VALUES (1, 'ALLOW', 'missions', 0, '*', 'WMS', 'nurc', NULL, 1, NULL);
INSERT INTO gr_rule (id, access, layer, priority, request, service, workspace, gsuser_id, instance_id, profile_id) VALUES (2, 'DENY', '*', 1, '*', '*', '*', NULL, NULL, NULL);


--
-- TOC entry 2636 (class 0 OID 18737)
-- Dependencies: 2283 2640
-- Data for Name: gr_layer_details; Type: TABLE DATA; Schema: georepo_test; Owner: georepo_test
--

INSERT INTO gr_layer_details (id, area, cqlfilterread, cqlfilterwrite, defaultstyle, rule_id) VALUES (1, NULL, NULL, NULL, NULL, 1);


--
-- TOC entry 2634 (class 0 OID 18721)
-- Dependencies: 2281 2636
-- Data for Name: gr_layer_attributes; Type: TABLE DATA; Schema: georepo_test; Owner: georepo_test
--



--
-- TOC entry 2635 (class 0 OID 18729)
-- Dependencies: 2282 2636
-- Data for Name: gr_layer_custom_props; Type: TABLE DATA; Schema: georepo_test; Owner: georepo_test
--

INSERT INTO gr_layer_custom_props (details_id, propvalue, propkey) VALUES (1, 'value-a', 'a');


--
-- TOC entry 2637 (class 0 OID 18747)
-- Dependencies: 2284 2636
-- Data for Name: gr_layer_styles; Type: TABLE DATA; Schema: georepo_test; Owner: georepo_test
--



--
-- TOC entry 2639 (class 0 OID 18757)
-- Dependencies: 2286 2638
-- Data for Name: gr_profile_custom_props; Type: TABLE DATA; Schema: georepo_test; Owner: georepo_test
--



--
-- TOC entry 2641 (class 0 OID 18775)
-- Dependencies: 2288 2640
-- Data for Name: gr_rule_limits; Type: TABLE DATA; Schema: georepo_test; Owner: georepo_test
--



SET search_path = public, pg_catalog;

--
-- TOC entry 2631 (class 0 OID 16770)
-- Dependencies: 2278
-- Data for Name: geometry_columns; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2630 (class 0 OID 16762)
-- Dependencies: 2277
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: public; Owner: postgres
--

-- Completed on 2011-02-08 17:32:49

--
-- PostgreSQL database dump complete
--

