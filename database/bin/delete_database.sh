#!/bin/bash
if [ `whoami` != postgres ]
  then
     echo "To be run as postgres"
  exit
fi

if [ -z "$1" ]
  then
    USER=motricedb
  else
    USER=$1
fi


# -U : User used for creating the new user

dropdb -U postgres ${USER}

exit

