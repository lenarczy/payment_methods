package com.mobica.paymentsmethod.paypal

import android.content.Intent
import android.support.v4.app.Fragment
import com.mobica.paymentsmethod.PaymentMethodDelegate

class PayPalDropUIDelegate(private val fragment: Fragment): PaymentMethodDelegate {
    private val payPalHandler = PayPalHandler()

    override fun init() {
        payPalHandler.updateClientToken()
    }

    override fun requestCode() = 4

    override fun startActivityForResult() {
        payPalHandler.startPayPalActivity(fragment, requestCode())
    }

    override fun handleActivityResult(resultCode: Int, data: Intent?) {
        payPalHandler.handlePaypalData(fragment.context, data)
    }
}