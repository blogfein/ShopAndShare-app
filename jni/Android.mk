LOCAL_PATH := $(call my-dir)
MYPATH := $(LOCAL_PATH)

TESSERACT_PATH :=/home/cian/Development/tesseract-3.00
LEPTONICA_PATH :=/home/cian/Development/leptonlib-1.66
LIBJPEG_PATH :=/home/cian/Development/libjpeg

include $(CLEAR_VARS)

# Only needed for first build of liblept.so and liftiff.so
#include com_googlecode_leptonica_android/Android.mk
#include com_googlecode_tesseract_android/Android.mk
include lept/Android.mk
include tess/Android.mk

include $(CLEAR_VARS)

include /home/cian/Development/OpenCV-2.3.1/share/OpenCV/OpenCV.mk

LOCAL_PATH := $(MYPATH)
LOCAL_C_INCLUDES += headers/
LOCAL_LDLIBS := -llog -ldl
LOCAL_MODULE := ShopAndShare
LOCAL_STATIC_LIBRARIES += lept
LOCAL_STATIC_LIBRARIES += tess
LOCAL_STATIC_LIBRARIES += libtiff
LOCAL_SRC_FILES := ShopAndShare.cpp logger.cpp filetomat.cpp image.cpp bgelim.cpp analyse.cpp mattofile.cpp constants.cpp result.cpp
LOCAL_CPPFLAGS := -g #debug
include $(BUILD_SHARED_LIBRARY)
