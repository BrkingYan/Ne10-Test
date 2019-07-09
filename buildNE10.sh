#!/bin/bash

rm /home/yy/Desktop/libNE10_test_demo.so
cd /Ne10
rm -rf build
mkdir build
cd build
export ANDROID_NDK=/home/yy/libs/android-ndk-r16b
export NE10_ANDROID_TARGET_ARCH=armv7
cmake -DCMAKE_TOOLCHAIN_FILE=../android/android_config.cmake ..
make

cp /home/yy/libs/NEONTest/Ne10/build/android/NE10Demo/jni/libNE10_test_demo.so /home/yy/Desktop
