#!/bin/bash
# Setting of maven test flag
DIRNAME=`dirname $0`
MVN_SKIP_TEST=true
MVN_SKIP_TEST=false

# ROOT of build directory
#BUILD_DIR=${HOME}/workspaces/motrice/pawap
BUILD_DIR=${HOME}/workspaces/inheritsource-develop/pawap

# ROOT of directory holding the j2ee containers
.  ${DIRNAME}/MOTRICE_REVISION
CONTAINER_ROOT=${HOME}/${MOTRICE_REVISION}

# ROOT of Hippo jcr content repository
CONTENT_ROOT=${CONTAINER_ROOT}/jcr-inherit-portal
CARGO_TOMCAT_FULL_VERSION=`grep cargo.tomcat.full.version ${DIRNAME}/../inherit-portal/pom.xml | cut -d ">" -f 2 | cut -d  "<" -f 1`
CARGO_TOMCAT_MAJOR_VERSION=`grep cargo.tomcat.major ${DIRNAME}/../inherit-portal/pom.xml | cut -d ">" -f 2 | cut -d  "<" -f 1`
TOMCAT_DIR=apache-tomcat-${CARGO_TOMCAT_FULL_VERSION}
TOMCAT_TGZ=${TOMCAT_DIR}.tar.gz
TOMCAT_DOWNLOAD_URL=http://apache.mirrors.spacedump.net/tomcat/tomcat-${CARGO_TOMCAT_MAJOR_VERSION}/v${CARGO_TOMCAT_FULL_VERSION}/bin/${TOMCAT_TGZ}

#####################################################################
# Container config 
#####################################################################

ESERVICE=hippo-eservice-tomcat        # PATH in CONTAINER_ROOT
ESERVICE_SSL=true                     # true/false
ESERVICE_HOST=eminburk.malmo.se       # DNS name
ESERVICE_EXTERNAL_PORT=443            # external port normally 80 or 443


ESERVICEPATCH=${ESERVICE_HOST}

ESERVICE_PORT=8080                   # internal port i.e. tomcat port

