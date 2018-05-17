package com.mobica.paymentsmethod.paypal

import android.content.Intent
import android.support.v4.app.Fragment
import com.mobica.paymentsmethod.PaymentMethodDelegate


class PayPalDirectDelegate(private val fragment: Fragment): PaymentMethodDelegate {
    private val payPalHandler = PayPalHandler()

    override fun init() {
        payPalHandler.updateClientToken()
    }

    override fun requestCode() = 5

    override fun startActivityForResult() {
        payPalHandler.startBillingAgreement(fragment.activity)
    }

    override fun handleActivityResult(resultCode: Int, data: Intent?) {
        //left empty intentionally
    }
}