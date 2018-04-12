#!/usr/bin/env bash

set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER probate;
    CREATE DATABASE probate
        WITH OWNER = probate
        ENCODING ='UTF-8'
        CONNECTION LIMIT = -1;
EOSQL

psql -v ON_ERROR_STOP=1 --dbname=probate --username "$POSTGRES_USER" <<-EOSQL
    CREATE SCHEMA probate AUTHORIZATION probate;
EOSQL
