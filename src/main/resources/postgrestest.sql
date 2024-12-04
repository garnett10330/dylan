-- public.company_stock definition

-- Drop table

-- DROP TABLE public.company_stock;

CREATE TABLE public.company_stock (
	id int4 DEFAULT nextval('company_stock_sequence'::regclass) NOT NULL,
	symbol varchar(225) DEFAULT ''::character varying NOT NULL,
	"name" varchar(225) DEFAULT ''::character varying NOT NULL,
	stock_exchange varchar(225) DEFAULT ''::character varying NULL,
	exchange_short_name varchar(100) DEFAULT ''::character varying NULL,
	currency varchar(30) DEFAULT ''::character varying NULL,
	gmt_created timestamp(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
	gmt_modified timestamp(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
	CONSTRAINT company_stock_pkey PRIMARY KEY (id)
);

-- public.company_stock_sequence definition

-- DROP SEQUENCE public.company_stock_sequence;

CREATE SEQUENCE public.company_stock_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;