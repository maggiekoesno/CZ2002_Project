#!/usr/bin/env bash

cd data
rm *.ser
touch courses.ser
touch students.ser
touch records.ser
cd ../source
javac -d ../classes/ scrame/boundary/ScrameApp.java
cd ../classes
java scrame.boundary.ScrameApp
