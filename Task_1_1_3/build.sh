#!/bin/bash

rm -rf build
mkdir -p build/classes build/docs build/jar

# Компилируем ВСЕ java файлы включая подпакеты
find src/main/java -name "*.java" > sources.txt
javac -d build/classes @sources.txt
rm sources.txt

javadoc -d build/docs -sourcepath src/main/java -subpackages ru.nsu.gstubarev.expression

jar -cf build/jar/expression.jar -C build/classes .

java -cp build/classes ru.nsu.gstubarev.expression.Main