package com.mobica.paymentsmethod

import android.content.Intent
import timber.log.Timber

interface PaymentMethodDelegate {
    fun init()
    fun requestCode(): Int
    fun startActivityForResult()
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode != requestCode()) {
            return false
        }
        Timber.d("Handled request code: $requestCode, result code: $resultCode")
        handleActivityResult(resultCode, data)
        return true
    }

    fun handleActivityResult(resultCode: Int, data: Intent?)
}