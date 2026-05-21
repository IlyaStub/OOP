#!/bin/bash

SRC_DIR="src/main/java"
BUILD_DIR="build"
MAIN_CLASS="ru.nsu.gstubarev.topology.Main"
JAR="$BUILD_DIR/jar/topology.jar"

build() {
    echo "=== Building ==="
    rm -rf "$BUILD_DIR"
    mkdir -p "$BUILD_DIR/classes" "$BUILD_DIR/jar"

    find "$SRC_DIR" -name "*.java" > sources.txt
    javac -d "$BUILD_DIR/classes" @sources.txt
    rm sources.txt

    jar -cfe "$JAR" "$MAIN_CLASS" -C "$BUILD_DIR/classes" .
    echo "=== Build done ==="
}

run_master() {
    java -jar "$JAR" master 9000
}

run_worker() {
    if [ -z "$1" ]; then
        echo "Usage: ./run.sh worker <port>"
        exit 1
    fi
    java -jar "$JAR" worker localhost 9000 "$1"
}

demo() {
    build

    echo "=== Starting Master ==="
    java -jar "$JAR" master 9000 &
    MASTER_PID=$!
    sleep 1

    echo "=== Starting Worker 1 (port 8081) ==="
    java -jar "$JAR" worker localhost 9000 8081 &
    W1_PID=$!
    sleep 0.5

    echo "=== Starting Worker 2 (port 8082) ==="
    java -jar "$JAR" worker localhost 9000 8082 &
    W2_PID=$!

    wait $MASTER_PID
    EXIT_CODE=$?

    kill $W1_PID $W2_PID 2>/dev/null
    echo "=== Done (exit code: $EXIT_CODE) ==="
}

case "$1" in
    build)          build ;;
    master)         run_master ;;
    worker)         run_worker "$2" ;;
    demo)           demo ;;
    *)
esac