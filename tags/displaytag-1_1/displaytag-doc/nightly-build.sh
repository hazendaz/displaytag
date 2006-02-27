#! /bin/sh
# script for displaytag nightly builds.
# you only need to have this script locally, it will fetch sources from cvs, compile them, generate the website and upload everything.
# IT WILL DOWNLOAD EVERYTHING TO "displaytag-build" AND DELETE SUCH DIRECTORY WHEN FINISHED!

CVSREPO=:pserver:anonymous:@cvs1.sourceforge.net:/cvsroot/displaytag

echo "*** getting sources from cvs ***"
mkdir displaytag-build
cd displaytag-build
cvs -d $CVSREPO login
cvs -d $CVSREPO co displaytag-all
cd displaytag-doc

echo "*** start build ***"
maven -e nightly

echo "*** removing work dir ***"
cd ../..
rm -Rf displaytag-build