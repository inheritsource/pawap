#!/bin/bash
SCRIPTMODE=${1}
MOTRICE_PROPERTIES=/usr/local/etc/motrice/motrice.properties
GIT_PROJECT=pawap
if [ -f ${MOTRICE_PROPERTIES} ];
then
   echo "Configuration file found...." 
else 
   echo "Configuration file not found...." 
   exit 1;
fi


# get the source 
PAWAP_DIR=${HOME}/workspaces/inheritsource-master
if [ -d ${PAWAP_DIR} ];
then
   echo "Cannot get source at ${PAWAP_DIR}. Directory already exists."
   exit 0;
fi

MKDIR="mkdir -p ${PAWAP_DIR}" 

if [ "${SCRIPTMODE}" != "test"  ]
then
  echo "not test.." 
  ${MKDIR} 
else 
  echo ${MKDIR}
fi

#GIT_CLONE="git clone git@github.com:inheritsource/${GIT_PROJECT}.git"
GIT_CLONE="git clone https://github.com/inheritsource/${GIT_PROJECT}" 
GIT_CHECKOUT="git checkout @MYRELEASE@"
#
if [ "${SCRIPTMODE}" != "test"  ]
then
  echo "not test.." 
  cd ${PAWAP_DIR}
  ${GIT_CLONE}
  if [ -d ${GIT_PROJECT} ];
  then
    cd ${GIT_PROJECT} 
    ${GIT_CHECKOUT}
  else 
    echo "git clone seems to have failed " 
    exit 1 
  fi 
else 
  echo  ${GIT_CLONE}
  echo  ${GIT_CHECKOUT}
fi
#
## create database 
## get databasename and user from properties file 
DATABASEUSERNAME=`grep "dataSource.username.=" ${MOTRICE_PROPERTIES} | cut -d = -f 2  | tr -d [:blank:]`
DATABASENAME=`grep "dataSource.url.=" ${MOTRICE_PROPERTIES}  | cut -d \/ -f 4  | tr -d [:blank:]`
# cd ${PAWAP_DIR}/database/bin
CREATE_USER="sudo  su postgres ./create_user.sh ${DATABASEUSERNAME}"
CREATE_DATABASE="sudo  su postgres ./create_database.sh ${DATABASENAME} ${DATABASEUSERNAME}"
CREATE_TABLES="sudo  su postgres ./create_tables.sh ${DATABASENAME} ${DATABASEUSERNAME}"
if [ "${SCRIPTMODE}" != "test"  ]
then
  echo "not test.." 
  cd ${PAWAP_DIR}/${GIT_PROJECT}/database/bin/
    ${CREATE_USER} 
    ${CREATE_DATABASE} 
    ${CREATE_TABLES} 
else 
   echo ${CREATE_USER} 
   echo ${CREATE_DATABASE} 
   echo ${CREATE_TABLES} 
fi
## 
## configure ldap TODO 

## compile and deploy 
## get hostname from  properties file 
HOSTNAME=`grep "site.base.uri=.*/site" /usr/local/etc/motrice/motrice.properties| cut -d / -f 3` 

APPEND1='echo "ESERVICE_HOST=${HOSTNAME} " >> bin/config_deploy_minburk.sh'
APPEND2='echo "MVN_SKIP_TEST=true" >> bin/config_deploy_minburk.sh' 
APPEND3='echo "BUILD_DIR=${PAWAP_DIR}/${GIT_PROJECT}" >> bin/config_deploy_minburk.sh' 
if [ "${SCRIPTMODE}" != "test"  ]
then
  echo "not test.." 
    cd ${PAWAP_DIR}/${GIT_PROJECT}
    echo "ESERVICE_HOST=${HOSTNAME} " >> bin/config_deploy_minburk.sh
    echo "MVN_SKIP_TEST=true" >> bin/config_deploy_minburk.sh
    echo "BUILD_DIR=${PAWAP_DIR}/${GIT_PROJECT}" >> bin/config_deploy_minburk.sh 
else 
   echo ${APPEND1} 
   echo ${APPEND2} 
   echo ${APPEND3} 
fi
CREATE_DEPLOY="./bin/create-new-deploy-environment-motrice.sh"
DEPLOY="./bin/deploy-motrice-platform.sh" 
if [ "${SCRIPTMODE}" != "test"  ]
then
  echo "not test.." 
    ${CREATE_DEPLOY} 
    ${DEPLOY} 
else 
   echo ${CREATE_DEPLOY} 
   echo ${DEPLOY} 
fi
