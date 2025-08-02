#!/bin/bash

SOLIDIFY_TEMPLATES_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
SOLIDIFY_TMP_REPO=$(mktemp -d)"/"

echo $SOLIDIFY_TEMPLATES_DIR
echo $SOLIDIFY_TMP_REPO

qa package-all --templates $SOLIDIFY_TEMPLATES_DIR --output $SOLIDIFY_TMP_REPO
qa index --input $SOLIDIFY_TMP_REPO

 rsync -avz $SOLIDIFY_TMP_REPO rsync://rsyncuser@localhost:18873/data
