#!/bin/bash
if [ `whoami` != postgres ]
  then
     echo "To be run as postgres"
  exit
fi

echo "database: $1"
echo "file: $2"

if [ $# != 2 ] ; then
  echo 'Input database name and filename or leave them blank!'
  echo 'exit...'
  exit
fi

DB_NAME=$1
FILE=$2

psql --set ON_ERROR_STOP=on -U postgres -d ${DB_NAME} -f ${FILE}

if [ $? != 0 ]; then
    echo "Unable to execute SQL commands"
fi

exit

