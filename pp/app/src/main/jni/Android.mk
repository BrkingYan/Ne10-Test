LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_LDLIBS    := -lm -llog
LOCAL_MODULE    := NE10_test_demo
LOCAL_SRC_FILES := NE10_test_demo.c



include $(BUILD_SHARED_LIBRARY)
