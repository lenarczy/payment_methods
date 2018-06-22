package com.mobica.paymentsmethod.visa

import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import com.mobica.paymentsmethod.PaymentMethodDelegate
import com.visa.checkout.*
import com.visa.checkout.ManualCheckoutSession.ManualCheckoutLaunchHandler
import timber.log.Timber
import java.math.BigDecimal


class VisaCheckoutDelegate(private val fragment: Fragment) : PaymentMethodDelegate {

    companion object {
        private const val VISA_CHECKOUT_API_KEY = "ZXTU9X97THTGBZLQYSRA21kyW8qTfVA3mJOmA57AzQipIjJJs"
        private const val VISA_CHECKOUT_PROFILE_NAME = "SYSTEMDEFAULT"
    }

    private var launchHandler: ManualCheckoutSession.ManualCheckoutLaunchHandler? = null

    override fun init() {
        val profile = Profile.ProfileBuilder(VISA_CHECKOUT_API_KEY, Environment.SANDBOX)
                .setProfileName(VISA_CHECKOUT_PROFILE_NAME)
                .build()

        val purchaseInfo = PurchaseInfo.PurchaseInfoBuilder(BigDecimal("10.23"),
                PurchaseInfo.Currency.USD)
                .build()

        VisaCheckoutSdk.initManualCheckoutSession(fragment.activity, profile, purchaseInfo, object : ManualCheckoutSession {
            override fun onReady(manualCheckoutLaunchHandler: ManualCheckoutLaunchHandler) {
                launchHandler = manualCheckoutLaunchHandler
            }

            override fun onResult(visaPaymentSummary: VisaPaymentSummary) {
                Timber.d("initManualCheckoutSession $this")

                when {
                    VisaPaymentSummary.PAYMENT_SUCCESS.equals(visaPaymentSummary.statusName, ignoreCase = true) -> Timber.d("Visa checkout success $this" + visaPaymentSummary.statusName)
                    VisaPaymentSummary.PAYMENT_CANCEL.equals(visaPaymentSummary.statusName, ignoreCase = true) -> Timber.d("Visa checkout canceled $this" + visaPaymentSummary.statusName)
                    VisaPaymentSummary.PAYMENT_ERROR.equals(visaPaymentSummary.statusName, ignoreCase = true) -> Timber.d("Visa checkout error $this" + visaPaymentSummary.statusName)
                    VisaPaymentSummary.PAYMENT_FAILURE.equals(visaPaymentSummary.statusName, ignoreCase = true) -> Timber.d("Visa checkout generic unknown failure $this" + visaPaymentSummary.statusName)
                }
            }
        })
    }

    override fun requestCode() = 6

    override fun startActivityForResult() {
        if (launchHandler == null) {
            val message = "VisaCheckout is not ready yet."
            Timber.d(message)
            Toast.makeText(fragment.context, message, Toast.LENGTH_SHORT).show()
            return
        }
        launchHandler?.launch()
    }

    override fun handleActivityResult(resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode(), resultCode, data)
    }
}