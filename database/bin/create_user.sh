#!/bin/bash
if [ `whoami` != postgres ]
  then
     echo "To be run as postgres"
  exit
fi
if [ -z "$1" ]
  then
    USER=motriceuser
  else
    USER=$1
fi

createuser -U postgres -D -P ${USER} 

# -U : User used for creating the new user
# -D : User will not be able to create a database
# -P : Prompt for the password of the new user

exit

