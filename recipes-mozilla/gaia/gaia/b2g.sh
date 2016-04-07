#!/bin/sh

set -e

B2G_PATH=/usr/lib/b2g/b2g
PROFILE_BASE_DIR=${HOME}/.mozilla/b2g

if [ ! -d "${PROFILE_BASE_DIR}" ]; then
    echo "Initializing the b2g profile..."
    mkdir -p ${PROFILE_BASE_DIR}
    tar xf /usr/share/b2g/profile.tar.gz -C ${PROFILE_BASE_DIR}
fi

${B2G_PATH} -profile ${PROFILE_BASE_DIR}/profile $@
