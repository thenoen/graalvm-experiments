#!/bin/bash

GRAALVM_SDK=jdks/graalvm-1.0.0-rc1/bin #configure according you local environment
HOTSPOTVM_SDK=jdks/jdk1.8.0_112/bin #configure according you local environment

MAIN_CLASS="TopTen"

OPTIMIZED_INPUT="/tmp/optimized_input.txt"
UNOPTIMIZED_INPUT="/tmp/unoptimized_input.txt"


executeWith () {
    echo "=============================="
    VM_PATH=$1
    CLASS=$2
    INPUT=$3
    $VM_PATH/javac $CLASS.java
    $VM_PATH/java -version
    time $VM_PATH/java $CLASS $INPUT
}

generateOptimizedInput () {
    for i in {0..2500}; do cat lorem.txt >> $1; done
}

generateUnoptimizedInput () {
    $HOTSPOTVM_SDK/javac UnoptimizedInputGenerator.java
    $HOTSPOTVM_SDK/java UnoptimizedInputGenerator $1
    rm UnoptimizedInputGenerator.class
}


generateOptimizedInput $OPTIMIZED_INPUT
generateUnoptimizedInput $UNOPTIMIZED_INPUT

executeWith $GRAALVM_SDK $MAIN_CLASS $OPTIMIZED_INPUT
executeWith $HOTSPOTVM_SDK $MAIN_CLASS $OPTIMIZED_INPUT
executeWith $GRAALVM_SDK $MAIN_CLASS $UNOPTIMIZED_INPUT
executeWith $HOTSPOTVM_SDK $MAIN_CLASS $UNOPTIMIZED_INPUT

echo -e "=================================\n"
echo -e "Bonus: java program is compiled to native image and is used with unoptimized input - expected duration is few minutes:\n"
$GRAALVM_SDK/native-image --no-server $MAIN_CLASS
time ./topten $OPTIMIZED_INPUT
time ./topten $UNOPTIMIZED_INPUT

# CLEANUP
rm $OPTIMIZED_INPUT
rm $UNOPTIMIZED_INPUT
rm *.class
rm topten