CREATE TABLE operation (
	ID bigserial primary key,
	CLIENT_DOCUMENT text not null,
	CURRENCY text not null,
	VALUE numeric not null,
	CREATION_DATE timestamp not null
);