#!/bin/bash

rm -rf build
mkdir -p build/classes build/docs

javac -d build/classes src/main/java/ru/nsu/gstubarev/book/*.java src/test/java/ru/nsu/gstubarev/book/*.java

javadoc -d build/docs -sourcepath src/main/java -subpackages ru.nsu.gstubarev.book

cd build/classes
java org.junit.platform.console.ConsoleLauncher --scan-class-path