#!/bin/bash
if [ `whoami` != postgres ]
  then
     echo "To be run as postgres"
  exit
fi

if [ $# != 0 ] && [ $# != 2 ] ; then
  echo 'Input database name and user or leave them blank!'
  echo 'exit...'
  exit
fi

if [ -z "$1" ]
  then
    export DB_NAME=motricedb
    export DB_USER=motriceuser
  else
    export DB_NAME=$1
    export DB_USER=$2    
fi

./execute_file.sh ${DB_NAME} ../create/motrice.postgres.create.hibernate.sql
./execute_file.sh ${DB_NAME} ../create/motrice.postgres.create.box.sql
./execute_file.sh ${DB_NAME} ../create/motrice.postgres.create.sig.sql
./execute_file.sh ${DB_NAME} ../create/motrice.postgres.create.crd.sql
./execute_file.sh ${DB_NAME} ../create/motrice.postgres.create.mtf.sql
./execute_file.sh ${DB_NAME} ../create/motrice.postgres.create.pxd.sql
./execute_file.sh ${DB_NAME} ../create/motrice.postgres.create.mig.sql
./execute_file.sh ${DB_NAME} ../create/motrice.postgres.create.o311.sql
./execute_file.sh ${DB_NAME} ../create/activiti.postgres.create.engine.sql
./execute_file.sh ${DB_NAME} ../create/activiti.postgres.create.history.sql
./execute_file.sh ${DB_NAME} ../create/activiti.postgres.create.identity.sql
./execute_file.sh ${DB_NAME} ../create/motrice.postgres.basedata.sql

#
# postgres user owns the created tables. Change ownership to DB_USER for all tables
#
tables=`psql -qAt  -U postgres -d ${DB_NAME} -c 'select tablename from pg_tables where schemaname = '\''public'\'';' `
for tbl in $tables ; do
  echo "Change ownership on table ${tbl} to ${DB_USER}"
  psql -c "alter table $tbl owner to $DB_USER" $DB_NAME;
done

#
# postgres user owns the created sequences. Change ownership to DB_USER for all sequences
#
sequences=`psql -qAt  -U postgres -d ${DB_NAME} -c 'SELECT c.relname FROM pg_class c WHERE c.relkind = '\''S'\'';'`
for sequence in $sequences; do
  echo "Change ownership on sequence ${sequence} to ${DB_USER}"
  psql -c "alter sequence $sequence owner to $DB_USER" $DB_NAME;
done

exit

