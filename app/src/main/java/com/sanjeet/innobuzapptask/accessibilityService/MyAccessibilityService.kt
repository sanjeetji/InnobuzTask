package com.sanjeet.innobuzapptask.accessibilityService

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast


class MyAccessibilityService : AccessibilityService() {


    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("MyAccessibilityService", "onServiceConnected:")
        val info = AccessibilityServiceInfo()
        info.eventTypes =
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED//AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK
        info.notificationTimeout = 100
        info.packageNames = null
        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        //To Get the message and sender info need to change AccessibilityEvent type
        // TYPE_WINDOW_STATE_CHANGED to TYPE_NOTIFICATION_STATE_CHANGED on onServiceConnected()

        Log.d("MyAccessibilityService", "onAccessibilityEvent: $event")
        Log.e("=====", "Access onAccessibilityEvent package name is :  ${event.packageName}")
        Log.e("=====", "Access onAccessibilityEvent event type is :  ${event.eventType}")
        Log.e("=====", "Access onAccessibilityEvent event time is : ${event.eventTime}")
        if (event.packageName == "com.whatsapp" && event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            // WhatsApp is launched
            showToast("WhatsApp Launched. !")
        }
    }

    override fun onInterrupt() {
        Log.e("MyAccessibilityService", "onInterrupt Access AccessibilityEvent")
        // This method is called when the accessibility service is interrupted.
        showToast("Getting some issue to access the accessibility")
    }

    private fun showToast(message: String) {
        Log.e("MyAccessibilityService", "Access onAccessibilityEvent : $message")
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}