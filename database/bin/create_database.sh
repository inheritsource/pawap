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
    DB_NAME=motricedb
    DB_OWNER=motriceuser
  else
    DB_NAME=$1
    DB_OWNER=$2
fi


# -U : User used for creating the new data
# -O : Owner
# -E : Encoding
# -D : Tablespace
# -l : locale

createdb -U postgres -O ${DB_OWNER} -T template0 -E 'UTF8' -D pg_default -l 'sv_SE.UTF-8' ${DB_NAME}

exit

