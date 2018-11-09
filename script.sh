#!/usr/bin/env bash

cd source
javac -d ../classes/ scrame/boundary/ScrameApp.java
cd ../classes
java scrame.boundary.ScrameApp
