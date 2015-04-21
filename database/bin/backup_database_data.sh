#!/bin/bash
if [ `whoami` != postgres ]
  then
     echo "To be run as postgres"
  exit
fi

if [ $# != 0 ] && [ $# != 1 ] ; then
  echo 'Input database name or leave them blank!'
  echo 'exit...'
  exit
fi

if [ -z "$1" ]
  then
    DB_NAME=motricedb
  else
    DB_NAME=$1
fi

touch ${DB_NAME}.backup
chmod 777 ${DB_NAME}.backup


# -U : user
# -a : data only
# -f : backup filename
# -T : exclude-table

pg_dump ${DB_NAME} -U postgres -f ${DB_NAME}.backup -T mtf_tag_type -a

exit

