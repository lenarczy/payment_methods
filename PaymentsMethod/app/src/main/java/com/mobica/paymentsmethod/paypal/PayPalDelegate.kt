package com.mobica.paymentsmethod.paypal

import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import com.mobica.paymentsmethod.PaymentMethodDelegate

class PayPalDelegate(private val fragment: Fragment): PaymentMethodDelegate {
    override fun requestCode() = 4

    override fun startActivityForResult() {
        Toast.makeText(fragment.context, "Not implemented", Toast.LENGTH_LONG).show()
    }

    override fun handleActivityResult(resultCode: Int, data: Intent?) {
    }
}