package com.mobica.paymentsmethod.paypal

import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import com.mobica.paymentsmethod.PaymentMethodDelegate

class PayPalDelegate(private val fragment: Fragment): PaymentMethodDelegate {
    private val payPalDelegateHandler = PayPalDelegateHandler()

    override fun init() {
        payPalDelegateHandler.updateClientToken()
    }

    override fun requestCode() = 4

    override fun startActivityForResult() {
        payPalDelegateHandler.startPayPalActivity(fragment, requestCode())
    }

    override fun handleActivityResult(resultCode: Int, data: Intent?) {
        payPalDelegateHandler.handlePaypalData(data)
    }
}