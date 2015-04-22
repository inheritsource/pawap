#!/bin/bash
DIRNAME=`dirname $0`
################################################################
# CONFIG                                                #
################################################################

###### current_config.sh  #####
# symlink to actual config of current installation
.  ${DIRNAME}/current_config.sh

################################################################
# END OF CONFIG                                                #
################################################################

PROPERTIES_LOCAL_BEFOREPATCH=properties-local.xml.beforepatch 

ERRORSTATUS=0

function getPidByPort () {
   local _outvar=$1
   local _result # Use some naming convention to avoid OUTVARs to clash
   _result=$(netstat -ntlp 2> /dev/null | grep '0 \:\:\:'${2} | awk '{print substr($7,1,match($7,"/")-1)}')
   eval $_outvar=\$_result # Instead of just =$_result
}

function shutdownContainer () { 
    #if no argument, then NOOP
    if [ -n "$1" ] 
    then 
	echo "Shutting down eservice, pid: " $1
	./shutdown.sh
	sleep 1
	LOOPVAR=0
	while ps -p $1 && [ ${LOOPVAR} -lt 6  ]
	do
	    LOOPVAR=$(expr ${LOOPVAR} + 1)
	    sleep 1
	done

    # If proper shutdown did not bite
	if ps -p $1
	then 
	    echo "Force shutting down eservice, pid: " $1
	    kill  $1
	    sleep 6
	fi

    # If still did not bite
	if ps -p $1
	then 
	    echo "Failed to shut down eservice, pid: " $1
	    ERRORSTATUS=1;
	fi
    else
	echo "shutdownContainer: No container argument"
    fi
}

function installInContainer () {
    echo "Installing on $1 container"
    if [ -d  ${CONTAINER_ROOT}/$1 ]
    then
	pushd ${CONTAINER_ROOT}/$1
	if [[ "$1" == "${ESERVICE}" ]]
	then
	    tar xzfv ${BUILD_DIR}/inherit-portal/target/inherit-portal-1.01.00-SNAPSHOT-distribution-eservices.tar.gz
	    cd webapps
	    rm -fr cms site orbeon exist docbox coordinatrice
        else
  	  echo "Unknown Directory ${CONTAINER_ROOT}/$1. Halting."
	  exit 1
        fi
	popd
    else
	echo "Directory ${CONTAINER_ROOT}/$1 does not exist. Halting."
	exit 1
    fi
}

function restartContainer () {
  local SERVICE_PID
  local LOOPCOUNTER=0

  case $1 in
    ${ESERVICE} )   CURRENT_PORT=${ESERVICE_PORT}   ;;
    *) echo "Unknown Service, halting..." ; exit 1 ;;
  esac

  pushd ${CONTAINER_ROOT}
    echo "Restarting container $1..."
    cd ${1}/bin/
    ./startup.sh 
    getPidByPort SERVICE_PID ${CURRENT_PORT}
    while [ -z "${SERVICE_PID}" -a  ${LOOPCOUNTER} -lt 30  ]
    do
      LOOPCOUNTER=$(expr ${LOOPCOUNTER} + 1)
      sleep 1
      getPidByPort SERVICE_PID ${CURRENT_PORT}
    done

    if [ -z "${SERVICE_PID}" ]
    then
      echo "Error: could not start $1"
      ERRORSTATUS=1
    fi
  popd
}

# 1. Sanity check of supplied parameters

echo "BUILD_DIR: $BUILD_DIR"
echo "CONTAINER_ROOT: $CONTAINER_ROOT"
echo "CONTENT_ROOT: $CONTENT_ROOT"

if [ ! -d "${BUILD_DIR}" ] || [ ! -d "${CONTAINER_ROOT}" ] || [ ! -d "${CONTENT_ROOT}" ]
then
    echo "Either of $BUILD_DIR, $CONTAINER_ROOT or $CONTENT_ROOT do not exist, aborting execution of $0"
    ERRORSTATUS=1
    exit $ERRORSTATUS
fi

echo "CONTAINER_ROOT/ESERVICE: $CONTAINER_ROOT/$ESERVICE"

if [ ! -d ${CONTAINER_ROOT}/${ESERVICE} ] 
then
    echo "${CONTAINER_ROOT}/${ESERVICE}  do not exist, aborting execution of $0"
    ERRORSTATUS=1
    exit $ERRORSTATUS
fi

echo "ESERVICE_PORT: $ESERVICE_PORT"


if [ -z "${ESERVICE_PORT}" ] 
then
    echo "Parameter ESERVICE_PORT unset, aborting execution of $0"
    ERRORSTATUS=1
    exit $ERRORSTATUS
fi

echo "ESERVICEPATCH: $ESERVICEPATCH"

if [ -z "${ESERVICEPATCH}"  ]
then
    echo "Parameters ESERVICEPATCH unset, aborting execution of $0"
    ERRORSTATUS=1
    exit $ERRORSTATUS
fi

# 2. Patching files for usage in production like environment
#    (the original versions are suitable in a test environment with
#    mvn -P cargo.run)

# 2a. Patching properties-local.xml for eservicetest and kservicetest
if [ -d ${BUILD_DIR}/inherit-portal/orbeon/src/main/webapp/WEB-INF/resources/config ]
then
    pushd ${BUILD_DIR}/inherit-portal/orbeon/src/main/webapp/WEB-INF/resources/config
    mv properties-local.xml $PROPERTIES_LOCAL_BEFOREPATCH
    cp $PROPERTIES_LOCAL_BEFOREPATCH properties-local.xml # thereby conserving mod date of properties-local.xml
                                                             # when $PROPERTIES_LOCAL_BEFOREPATCH is renamed
                                                             # back to properties-local.xml in step 8
    if ${ESERVICE_SSL}; then
	sed -e "s/http:\/\/localhost:8080\/site\/confirmdispatcher/https:\/\/${ESERVICE_HOST}:${ESERVICE_EXTERNAL_PORT}\/site\/confirmdispatcher/g" properties-local.xml > properties-local.xml.eservicepatch
    else
	sed -e "s/http:\/\/localhost:8080\/site\/confirmdispatcher/http:\/\/${ESERVICE_HOST}:${ESERVICE_EXTERNAL_PORT}\/site\/confirmdispatcher/g" properties-local.xml > properties-local.xml.eservicepatch
    fi

   mv properties-local.xml.eservicepatch properties-local.xml
fi 
# 3. Build eservice-platform
if [ -z ${MVN_SKIP_TEST} ] 
then 
    MVN_SKIP_TEST=true 
fi 
pushd ${BUILD_DIR}
if mvn -DskipTests=${MVN_SKIP_TEST} clean install
then
    echo "Executing mvn -DskipTests=true clean install - patched for eservicetest..."
else
    ERRORSTATUS=$?
    echo "Compilation failed. Aborting execution"
    exit $ERRORSTATUS
fi

# 4. Build eservice-platform distribution snapshot
cd inherit-portal
if mvn -P dist
then
    echo "Creating eservicetest snapshot distribution tar.gz..."
    mv target/inherit-portal-1.01.00-SNAPSHOT-distribution.tar.gz target/inherit-portal-1.01.00-SNAPSHOT-distribution-eservices.tar.gz
else
    ERRORSTATUS=$?
    echo "Building of snapshot distribution failed. Aborting execution"
    exit $ERRORSTATUS
fi
popd


#8. Restore to original state the patched files from step 2 in order to be able to run
#   the deployment script multiple times

# 8a. Restore original properties-local.xml to original state. Necessary to make step 2a
#    (patching properties-local.xml) work correctly next time script is run
    pushd ${BUILD_DIR}/inherit-portal/orbeon/src/main/webapp/WEB-INF/resources/config
       mv $PROPERTIES_LOCAL_BEFOREPATCH properties-local.xml
    popd


# 9. Stop j2ee containers
    pushd ${CONTAINER_ROOT}

cd ${ESERVICE}/bin/

getPidByPort ESERVICE_PID ${ESERVICE_PORT}
shutdownContainer "${ESERVICE_PID}"

popd

if [ ${ERRORSTATUS} -eq 1 ]
then
    echo "Failed to shutdown all containers, aborting execution of script"
    exit ${ERRORSTATUS}
fi

# 10. Install on eservice container
echo "Installing wars in ${ESERVICE} container"
installInContainer ${ESERVICE}

# 13. Clean up content repositories
echo "Clean up content repository..."
pushd ${CONTENT_ROOT}
rm -fr repository version workspaces 
popd

# 14. Restart containers

restartContainer ${ESERVICE}

exit ${ERRORSTATUS}
