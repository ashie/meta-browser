SUMMARY = "HTML5-based Phone UI for the Boot 2 Gecko Project"
LICENSE = "Apache-2.0"
DEPENDS = "b2g"

LIC_FILES_CHKSUM = "file://LICENSE;md5=7eca70cd144bd72119f935f821f4f922"

SRC_URI = "https://github.com/mozilla-b2g/gaia/archive/B2G_2_5_20160125_MERGEDAY.tar.gz \
           file://b2g.png \
           file://b2g.desktop \
           file://b2g.sh \
           "
SRC_URI[md5sum] = "a270c5955f569e26f050d0aade3246c0"
SRC_URI[sha256sum] = "26f499b9229c4373bafde504f57093bc50ab524a62cf76043dc79ac3073dd1e5"

PACKAGES = "${PN}"

EXTRA_OEMAKE += "SHELL=/bin/sh"

export NOFTU = "1"
export NO_LOCK_SCREEN = "1"
export GAIA_DEVICE_TYPE = "phone"

S = "${WORKDIR}/${PN}-B2G_2_5_20160125_MERGEDAY"

GAIA_COREAPPSDIR_PREF = "user_pref('b2g.coreappsdir', \"${libdir}/${PN}\");"

do_compile() {
    oe_runmake b2g_sdk
    oe_runmake
    echo "${GAIA_COREAPPSDIR_PREF}" >> ${S}/profile/user.js
    echo "${GAIA_COREAPPSDIR_PREF}" >> ${S}/profile/defaults/pref/user.js
}

do_install() {
    install -d ${D}${libdir}/${PN}
    install -d ${D}${datadir}/${PN}
    tar cvfz profile.tar.gz ${S}/profile --exclude webapps
    cp -r ${S}/profile/webapps ${D}${libdir}/${PN}
    install -m 0644 ${S}/profile.tar.gz ${D}${datadir}/${PN}

    install -d ${D}${bindir}
    install -d ${D}${datadir}/applications
    install -d ${D}${datadir}/pixmaps
    install -m 0755 ${WORKDIR}/b2g.sh ${D}${bindir}/b2g
    install -m 0644 ${WORKDIR}/b2g.desktop ${D}${datadir}/applications/
    install -m 0644 ${WORKDIR}/b2g.png ${D}${datadir}/pixmaps/
}
