--
-- Create tables with their constraints (PK, UNIQUE,...) and other indexes
--

CREATE TABLE o311jurisdiction
(
  id bigint NOT NULL,
  version bigint NOT NULL,
  date_created timestamp without time zone,
  enabled_flag boolean,
  full_name character varying(160) NOT NULL,
  jurisdiction_id character varying(60) NOT NULL,
  last_updated timestamp without time zone,
  procdef_uuid character varying(64),
  service_notice character varying(240),
  CONSTRAINT o311jurisdiction_pkey PRIMARY KEY (id),
  CONSTRAINT o311jurisdiction_full_name_key UNIQUE (full_name),
  CONSTRAINT o311jurisdiction_jurisdiction_id_key UNIQUE (jurisdiction_id)
);

CREATE TABLE o311service
(
  id bigint NOT NULL,
  version bigint NOT NULL,
  code character varying(64) NOT NULL,
  date_created timestamp without time zone,
  description character varying(240),
  last_updated timestamp without time zone,
  name character varying(120) NOT NULL,
  CONSTRAINT o311service_pkey PRIMARY KEY (id),
  CONSTRAINT o311service_code_key UNIQUE (code),
  CONSTRAINT o311service_name_key UNIQUE (name)
);

CREATE TABLE o311service_group
(
  id bigint NOT NULL,
  version bigint NOT NULL,
  code character varying(64) NOT NULL,
  date_created timestamp without time zone,
  display_name character varying(120) NOT NULL,
  jurisdiction_id bigint NOT NULL,
  last_updated timestamp without time zone,
  CONSTRAINT o311service_group_pkey PRIMARY KEY (id),
  CONSTRAINT fkf0c5e8318911ead0 FOREIGN KEY (jurisdiction_id)
      REFERENCES o311jurisdiction (id),
  CONSTRAINT o311service_group_display_name_key UNIQUE (display_name),
  CONSTRAINT o311service_group_jurisdiction_id_code_key UNIQUE (jurisdiction_id, code)
);

CREATE TABLE o311service_in_jurisd
(
  id bigint NOT NULL,
  version bigint NOT NULL,
  jurisdiction_id bigint NOT NULL,
  service_id bigint NOT NULL,
  service_group_id bigint,
  CONSTRAINT o311service_in_jurisd_pkey PRIMARY KEY (id),
  CONSTRAINT fkf69b6f9f7ebb7aa5 FOREIGN KEY (service_group_id)
      REFERENCES o311service_group (id),
  CONSTRAINT fkf69b6f9f8911ead0 FOREIGN KEY (jurisdiction_id)
      REFERENCES o311jurisdiction (id),
  CONSTRAINT fkf69b6f9fbfcab24 FOREIGN KEY (service_id)
      REFERENCES o311service (id)
);

CREATE TABLE o311tenant
(
  id bigint NOT NULL,
  version bigint NOT NULL,
  api_key character varying(120) NOT NULL,
  date_created timestamp without time zone,
  display_name character varying(48) NOT NULL,
  email character varying(240) NOT NULL,
  first_name character varying(255),
  last_name character varying(120) NOT NULL,
  last_updated timestamp without time zone,
  organization_name character varying(120) NOT NULL,
  phone character varying(60) NOT NULL,
  CONSTRAINT o311tenant_pkey PRIMARY KEY (id),
  CONSTRAINT o311tenant_api_key_key UNIQUE (api_key),
  CONSTRAINT o311tenant_display_name_key UNIQUE (display_name)
);

CREATE TABLE o311tenant_in_jurisd
(
  id character varying(240) NOT NULL,
  version bigint NOT NULL,
  jurisdiction_id bigint NOT NULL,
  tenant_id bigint NOT NULL,
  enabled_flag boolean,
  CONSTRAINT o311tenant_in_jurisd_pkey PRIMARY KEY (id),
  CONSTRAINT fkcc2a705c8911ead0 FOREIGN KEY (jurisdiction_id)
      REFERENCES o311jurisdiction (id),
  CONSTRAINT fkcc2a705cafbdaeb0 FOREIGN KEY (tenant_id)
      REFERENCES o311tenant (id)
);
