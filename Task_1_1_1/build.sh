#!/bin/bash

# Удаляем предыдущую сборку и создаем директории
rm -rf build
mkdir -p build/classes build/docs build/jar

javac -d build/classes src/main/java/ru/nsu/g/stubarev/heapsort/*.java

javadoc -d build/docs -sourcepath src/main/java -subpackages ru.nsu.g.stubarev.heapsort

jar -cf build/jar/heapsort.jar -C build/classes .

mkdir -p build/test-classes
javac -d build/test-classes -cp "build/classes:lib/*" src/test/java/ru/nsu/g/stubarev/heapsort/*.java
java -cp "build/test-classes:build/classes:lib/*" org.junit.platform.console.ConsoleLauncher -c ru.nsu.g.stubarev.heapsort.SortTest