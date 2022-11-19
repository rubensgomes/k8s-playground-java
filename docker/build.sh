#!/bin/bash -auh
#
# author : Rubens Gomes
# purpose: builds a docker image
#

ARTIFACT_ID="k8s-learning-ms"
APP_HOME="/home/rubens/github/k8s-learning/app"
DOCKER_DIR="/home/rubens/github/k8s-learning/docker"
DOCKER_VERSION="20.10.21"
REPO="rubensgomes"
DOCKER_DEBUG=""
APP_PKG_FOLDER="ctx/${ARTIFACT_ID}"

ERROR_CODE=100

##
## function name :  usage
##     arguments :  none
##        return :  always return 0
##       purpose :  display script usage.
##
function usage()
{
cat <<EOF
Usage: `basename ${0}` [-bcdhpr] IMAGE_NAME [TAG]

Options:
  -b : build/package app as jar and extracts into layered jar ctx folder
  -c : flag to run a docker system prune everything
  -d : flag to print debug information
  -h : prints usage
  -p : flag to push image to docker hub
  -r : flag to remove the image volume

IMAGE_NAME     : the docker image name: "k8s-learning-ms".
TAG (optional) : a semantic version for the new image; e.g., "1.0.0, 1.0.1, ..".
                 If TAG is not provided "latest" is used.
EOF
  return 0
}

##
## function name :  clean
##     arguments :  image_name, tag, is_vol_rm, is_prune, is_pkg
##        return :  return 0 if okay; something else if failed
##       purpose :  cleans docker environment prior to building image
##
function clean()
{
   if [ ${#} -ne 5 ]
   then
     echo "missing image_name/tag/is_vol_rm/is_prune/is_pkg arguments" 1>&2
     return 1
   fi

   local _image_name="${1}"
   local _tag="${2}"
   local _is_vol_rm="${3}"
   local _is_prune="${4}"
   local _is_pkg="${5}"

   # I am assuming the container and image have the exact same name
   local _container="${_image_name}"

   # I am assuming the volume and image have the exact same name
   local _volume="${_image_name}"

   # image that can referenced in the command line
   local _image="${REPO}/${_image_name}"
   local _image_tag="${_image}:${_tag}"

   if [ "${_is_pkg}" == "true" ]
   then

      if [ -d ${APP_PKG_FOLDER} ]
      then
         echo "removing ${APP_PKG_FOLDER} files..."
         \rm -fr ${APP_PKG_FOLDER}/*

         if [ ${?} -ne 0 ]
         then
            echo "failed to remove files from ${APP_PKG_FOLDER}." 1>&2
            return 1
         fi

      fi

      if [ ! -r "${APP_HOME}/pom.xml" ]
      then
         echo "could not find readable [pom.xml] file in ${APP_HOME} root folder." 1>&2
         return 1
      fi

      cd "${APP_HOME}"
      \mvn -U clean -f "pom.xml"

      if [ ${?} -ne 0 ]
      then
         echo "failed to run [mvn -U clean -f ${APP_HOME}/pom.xml]." 1>&2
         return 1
      fi

   fi

   \sudo docker ${DOCKER_DEBUG} container stop "${_container}"
   \sudo docker ${DOCKER_DEBUG} container rm -v "${_container}"
   \sudo docker ${DOCKER_DEBUG} container prune --force
   \sudo docker ${DOCKER_DEBUG} image rm "${_image_tag}"
   \sudo docker ${DOCKER_DEBUG} image prune --force

   if [ "${_is_vol_rm}" == "true" ]
   then
      \sudo docker ${DOCKER_DEBUG} volume rm "${_volume}"
      \sudo docker ${DOCKER_DEBUG} volume prune --force
   fi

   if [ "${_is_prune}" == "true" ]
   then
      \sudo docker ${DOCKER_DEBUG} system prune --all --volumes --force
   fi

   return 0
}

##
## function name :  build
##     arguments :  image_name, tag, ctx_dir, is_pkg
##        return :  return 0 if okay; something else if failed
##       purpose :  builds docker image
##
function build()
{
   if [ ${#} -ne 4 ]
   then
     echo "missing image_name/tag/ctx_dir arguments" 1>&2
     return 1
   fi

   local _image_name="${1}"
   local _tag="${2}"
   local _build_arg_tag="${_tag}"

   if [ -z ${_tag} ]
   then
     echo "setting build-tag to latest"
      _build_arg_tag="latest"
   fi

   local _ctx_dir="${3}"
   local _is_pkg="${4}"

   # image that can referenced in the command line
   local _image="${REPO}/${_image_name}"
   local _image_tag="${_image}:${_tag}"

   if [ -z ${_tag} ]
   then
     echo "setting tag to empty"
      _image_tag="${_image}"
   fi


   if [ "${_is_pkg}" == "true" ]
   then

      if [ ! -r "${APP_HOME}/pom.xml" ]
      then
         echo "could not find readable [pom.xml] file in ${APP_HOME} root folder." 1>&2
         return 2
      fi

      cd "${APP_HOME}"
      \mvn -U package -f "pom.xml"

      if [ ${?} -ne 0 ]
      then
         echo "failed to run [mvn -U clean -f ${APP_HOME}/pom.xml]." 1>&2
         return 2
      fi

      \java -Djarmode=layertools -jar target/${ARTIFACT_ID}-*-exec.jar list

      if [ ${?} -ne 0 ]
      then
         echo "failed to find [${APP_HOME}/target/${ARTIFACT_ID}-*-exec.jar]." 1>&2
         return 2
      fi

      # extract jar into layered folders in the jar image ctx
      \java -Djarmode=layertools -jar target/${ARTIFACT_ID}-*-exec.jar \
            extract --destination "${DOCKER_DIR}/ctx/${ARTIFACT_ID}"

      if [ ${?} -ne 0 ]
      then
         echo "failed to extract [${APP_HOME}/target/${ARTIFACT_ID}-*-exec.jar]." 1>&2
         return 2
      fi

   fi

   \cd "${_ctx_dir}"

   if [ ${?} -ne 0 ]
   then
      echo "failed to change to the context dir [${_ctx_dir}]" 1>&2
      return 2
   fi

   \sudo docker ${DOCKER_DEBUG} image build \
--build-arg "BUILD_DATE=`\date -u +'%Y-%m-%dT%H:%M:%SZ'`" \
--build-arg "DOCKER_VERSION=${DOCKER_VERSION}" \
--build-arg "IMAGE_NAME=${_image_name}" \
--build-arg "REPO=${REPO}" \
--build-arg "TAG=${_build_arg_tag}" \
--tag "${_image_tag}" .
   status=${?}
   return ${status}
}

##
## function name :  push
##     arguments :  image_name, tag
##        return :  return 0 if okay; something else if failed
##       purpose :  push docker image to dockerhub
##
function push()
{
   if [ ${#} -ne 2 ]
   then
     echo "missing image_name/tag arguments" 1>&2
     return 1
   fi

   local _image_name="${1}"
   local _tag="${2}"

   # image that can referenced in the command line
   local _image="${REPO}/${_image_name}"
   local _image_tag="${_image}:${_tag}"

   if [ -z ${_tag} ]
   then
     echo "setting tag to empty"
      _image_tag="${_image}"
   fi

   # ensure image is found locally prior to pushing
   \sudo docker ${DOCKER_DEBUG} image inspect "${_image_tag}" >/dev/null 2>&1
   local status=${?}

   if [ ${status} -ne 0 ]
   then
      echo "image [${_image_tag}] not found." 1>&2
      return ${status}
   fi

   \sudo docker ${DOCKER_DEBUG} image push "${_image_tag}"
   status=${?}

   if [ ${status} -ne 0 ]
   then
      echo "image [${_image_tag}] not pushed: ENSURE YOU PREVIOUSLY LOGGED IN TO DOCKER.IO." 1>&2
      echo "run: sudo docker login -urubensgomes docker.io" 1>&2
   fi

   return ${status}
}

###########################################################
# main body

is_pkg="false"
is_prune="false"
is_push="false"
is_vol_rm="false"
image_name=
OPTIND=0
while getopts "bcdhpr" option
do
   case "${option}" in
      b) # compiles/package jar
         is_pkg="true"
         ;;
      c) # run system prune
         is_prune="true"
         ;;
      d) # debug mode
         DOCKER_DEBUG="--debug"
         \set -x
         ;;
      h) # usage
         usage
         exit 0
         ;;
      p) # push image to dockerhub
         is_push="true"
         ;;
      r) # remove volume
         is_vol_rm="true"
         ;;
      \?) # invalid option
         usage
         exit ${ERROR_CODE}
         ;;
   esac
done
shift $((OPTIND-1))

# check required arguments (IMAGE_NAME/TAG) have been pased.
if [ ${#} -lt 1 -o ${#} -gt 2 ]
then
   echo "Missing or invalid arguments: IMAGE_NAME [TAG]." 1>&2
   usage
   exit ${ERROR_CODE}
fi

#---------------IMAGE_NAME------------------------------------------------------

# read in IMAGE_NAME
image_name="${1}"

if [[ ! ${image_name} =~ ^[0-9a-z-]+$ ]]
then
   echo "IMAGE_NAME [${image_name}] not valid: only small letters and "-" allowed." 1>&2
   usage
   exit ${ERROR_CODE}
fi

#---------------TAG-------------------------------------------------------------

# read in TAG
tag=""

if [ ${#} -eq 2 ]
then
    tag="${2}"

    if [[ ! ${tag} =~ ^[0-9]+[.]{1}[0-9]+[.]{1}[0-9]+$ ]]
    then
       echo "TAG [${tag}] not valid: must be a semantic version." 1>&2
       usage
       exit ${ERROR_CODE}
    fi

fi

#---------------Dockerfile------------------------------------------------------

ctx_dir="${DOCKER_DIR}/ctx"

if [ ! -d "${ctx_dir}" ]
then
   echo "[${ctx_dir}] is not found or not a valid directory." 1>&2
   exit ${ERROR_CODE}
fi

docker_file="${ctx_dir}/Dockerfile"

if [ ! -r "${docker_file}" ]
then
   echo "[${docker_file}] is not a readable file." 1>&2
   exit ${ERROR_CODE}
fi

#---------------docker----------------------------------------------------------

\which docker >/dev/null 2>&1

if [ ${?} -ne 0  ]
then
   echo "\"docker\" not found or not executable file." 1>&2
   exit ${ERROR_CODE}
fi

\sudo docker ${DOCKER_DEBUG} system info >/dev/null 2>&1

if [ ${?} -ne 0  ]
then
   echo "\"docker\" not running." 1>&2
   exit ${ERROR_CODE}
fi

#---------------clean-----------------------------------------------------------

echo "cleaning docker environment prior to buidling image"
clean "${image_name}" "${tag}" "${is_vol_rm}" "${is_prune}" "${is_pkg}"

if [ ${?} -ne 0 ]
then
   echo "\"clean\" failed." 1>&2
   exit ${ERROR_CODE}
fi

#---------------build-----------------------------------------------------------

echo "building docker image [${REPO}/${image_name}:${tag}] ..."
build "${image_name}" "${tag}" "${ctx_dir}" "${is_pkg}"

if [ ${?} -ne 0 ]
then
   echo "\"build\" failed." 1>&2
   exit ${ERROR_CODE}
fi

#---------------push------------------------------------------------------------

if [ "${is_push}" == "true" ]
then
   echo "pushing image to dockerhub"
   push "${image_name}" "${tag}"

   if [ ${?} -ne 0 ]
   then
      echo "\"push\" failed." 1>&2
      exit ${ERROR_CODE}
   fi

fi

echo "build succeeded!"
exit 0
