#! /bin/sh
# script for displaytag nightly builds.
# you only need to have this script locally, it will fetch sources from cvs, compile them, generate the website and upload everything.
# IT WILL DOWNLOAD EVERYTHING TO "displaytag" AND DELETE SUCH DIRECTORY WHEN FINISHED!

WORKDIR=displaytag
DEST=/home/groups/d/di/displaytag/htdocs/nightly/
CVSREPO=:pserver:anonymous:@cvs1.sourceforge.net:/cvsroot/displaytag
DATE=`date`
. .shrc

echo "*** getting sources from cvs ***"
# export is faster but statscvs requires a working cvs checkout
#cvs -q -d$CVSREPO export -Dtomorrow $WORKDIR
cvs -d $CVSREPO login 
cvs -d $CVSREPO co $WORKDIR 
cd $WORKDIR

echo "*** start build ***"
maven -e nightly

echo "*** removing work dir ***"
cd ..
rm -Rf $WORKDIR