package com.mobica.paymentsmethod.gpay

import android.content.Intent
import android.support.v4.app.Fragment
import com.mobica.paymentsmethod.PaymentMethodDelegate

class GPayDelegate(private val fragment: Fragment) : PaymentMethodDelegate {

    override fun init() {
        //left empty intentionally
    }

    override fun requestCode() = 3

    override fun startActivityForResult() {
        fragment.startActivityForResult(Intent(fragment.context, GPayActivity::class.java), requestCode())
    }

    override fun handleActivityResult(resultCode: Int, data: Intent?) = Unit
}