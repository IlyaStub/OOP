#!/bin/bash

rm -rf build
mkdir -p build/classes build/docs build/jar

javac -d build/classes src/main/java/ru/nsu/g/stubarev/blackjack/*.java

javadoc -d build/docs -sourcepath src/main/java -subpackages ru.nsu.g.stubarev.blackjack

jar -cf build/jar/blackjack.jar -C build/classes .

java -cp build/classes ru.nsu.g.stubarev.blackjack.GameController