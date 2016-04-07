# Copyright (C) 2009-2015, O.S. Systems Software Ltda. All Rights Reserved
# Released under the MIT license (see packages/COPYING)

DESCRIPTION ?= "Browser made by mozilla"
DEPENDS += "alsa-lib curl startup-notification libevent cairo libnotify libvpx \
            virtual/libgl nss nspr pulseaudio yasm-native icu"

LICENSE = "MPLv1 | GPLv2+ | LGPLv2.1+"
LIC_FILES_CHKSUM = "file://toolkit/content/license.html;endline=39;md5=f7e14664a6dca6a06efe93d70f711c0e"

SRC_URI = "https://archive.mozilla.org/pub/firefox/releases/${PV}/source/firefox-${PV}.source.tar.bz2;name=archive \
           file://vendor.js \
           file://fix-python-path.patch \
           file://prefs/Don-t-auto-disable-extensions-in-system-directories.patch \
           file://prefs/Set-DPI-to-system-settings.patch \
           file://prefs/Set-javascript.options.showInConsole.patch \
           file://porting/Add-xptcall-support-for-SH4-processors.patch \
           file://porting/NSS-Fix-FTBFS-on-Hurd-because-of-MAXPATHLEN.patch \
           file://porting/NSS-GNU-kFreeBSD-support.patch \
           file://porting/Make-powerpc-not-use-static-page-sizes-in-mozjemalloc.patch \
           file://fixes/Allow-.js-preference-files-to-set-locked-prefs-with-.patch \
           file://fixes/Avoid-spurious-Run-items-in-application-handlers.patch \
           file://fixes/Bug-1136958-Remove-duplicate-SkDiscardableMemory_none.patch \
           file://fixes/Bug-1166243-Remove-build-function-from-js-and-xpc.patch \
           file://fixes/Bug-1166538-Use-dozip.py-for-langpacks.patch \
           file://fixes/Bug-1094324-Set-browser.newtabpage.enhanced-default.patch \
           file://fixes/Bug-1168231-Normalize-file-mode-in-jars.patch \
           file://fixes/Bug-1098343-part-1-support-sticky-preferences-meaning.patch \
           file://fixes/Fix-build-error-in-MIPS-SIMD-when-compiling-with-mfpxx.patch \
           file://iceweasel-branding/Use-MOZ_APP_DISPLAYNAME-to-fill-appstrings.properties.patch \
           file://iceweasel-branding/Modify-search-plugins-depending-on-MOZ_APP_NAME.patch \
           file://iceweasel-branding/Determine-which-phishing-shavar-to-use-depending-on.patch \
           file://iceweasel-branding/Use-firefox-instead-of-MOZ_APP_NAME-for-profile-reset.patch \
           file://debian-hacks/Avoid-wrong-sessionstore-data-to-keep-windows-out-of.patch \
           file://debian-hacks/Add-another-preferences-directory-for-applications.patch \
           file://debian-hacks/Don-t-register-plugins-if-the-MOZILLA_DISABLE_PLUGINS.patch \
           file://debian-hacks/Use-a-variable-for-xulrunner-base-version-in-various.patch \
           file://debian-hacks/Don-t-error-out-when-run-time-libsqlite-is-older-than.patch \
           file://debian-hacks/Add-a-2-minutes-timeout-on-xpcshell-tests.patch \
           file://debian-hacks/Load-distribution-search-plugins-from.patch \
           file://debian-hacks/Handle-transition-to-etc-appname-searchplugins.patch \
           file://debian-hacks/Preprocess-appstrings.properties.patch \
           file://debian-hacks/Disable-Firefox-Health-Report.patch \
           file://debian-hacks/Bump-search-engine-max-icon-size-to-35kB.patch \
           file://debian-hacks/NSS-Adds-the-SPI-Inc.-and-CAcert.org-CA-certificates.patch \
           file://debian-hacks/Work-around-binutils-assertion-on-mips.patch \
           file://debian-hacks/Revert-Bump-search-engine-max-icon-size-to-35kB.patch \
           "

SRC_URI[archive.md5sum] = "cc74abc48ac7a888aeb24ba31a7ff209"
SRC_URI[archive.sha256sum] = "91174d0118ac7178b5902bd6e82743b4eab5d567ec6431abebf4da093ffbd0ff"

S = "${WORKDIR}/mozilla-esr38"
# MOZ_APP_BASE_VERSION should be incremented after a release
MOZ_APP_BASE_VERSION = "38.6"

inherit mozilla

PACKAGES = "${PN}-dbg ${PN}"

EXTRA_OEMAKE += "installdir=${libdir}/${PN}"

ARM_INSTRUCTION_SET = "arm"

do_install() {
    oe_runmake -f client.mk package
    install -d ${D}${libdir}
    tar xvfj ${MOZ_OBJDIR}/dist/${PN}-38.0.en-US.linux-gnueabi-arm.tar.bz2 -C ${D}${libdir}
}

FILES_${PN} = "${libdir}/${PN}/"
FILES_${PN}-dbg += "${libdir}/${PN}/.debug \
                    ${libdir}/${PN}/*/.debug"

# We don't build XUL as system shared lib, so we can mark all libs as private
PRIVATE_LIBS = "libmozjs.so \
                libxpcom.so \
                libnspr4.so \
                libxul.so \
                libmozalloc.so \
                libplc4.so \
                libplds4.so \
                libmozsqlite3.so"

# mark libraries also provided by nss as private too
PRIVATE_LIBS += " \
    libfreebl3.so \
    libnss3.so \
    libnssckbi.so \
    libsmime3.so \
    libnssutil3.so \
    libnssdbm3.so \
    libssl3.so \
    libsoftokn3.so \
"

PRIVATE_LIBS += "libdbusservice.so"