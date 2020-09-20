#!/bin/sh
set -e

npm --prefix ./spa run build

cp -r ./spa/build/* ./src/main/webapp