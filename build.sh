#!/bin/sh
set -e

npm --prefix ./spa install

npm --prefix ./spa run build

cp -r ./spa/build/* ./src/main/webapp