#!/bin/bash

rm -rf build
mkdir -p build/classes build/docs build/jar

find src/main/java -name "*.java" > sources.txt

javac -d build/classes @sources.txt
rm sources.txt

javadoc -d build/docs -sourcepath src/main/java -subpackages ru.nsu.gstubarev.poisk

jar -cf build/jar/poisk.jar -C build/classes .