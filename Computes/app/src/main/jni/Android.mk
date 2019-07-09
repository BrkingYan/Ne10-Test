LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_LDLIBS    := -lm -llog
LOCAL_MODULE := JNITest
LOCAL_SRC_FILES := Algorithm.cpp
include $(BUILD_SHARED_LIBRARY)