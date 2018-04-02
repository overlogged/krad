#!/bin/sh
rsync -r --delete-after --quiet $TRAVIS_BUILD_DIR/target ubuntu@119.29.32.204:/home/ubuntu/krad
ls