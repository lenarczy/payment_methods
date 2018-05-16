package com.mobica.paymentsmethod.cardio

import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import com.mobica.paymentsmethod.PaymentMethodDelegate

class CardIODelegate(private val fragment: Fragment) : PaymentMethodDelegate {

    override fun init() {
        //left empty intentionally
    }

    override fun requestCode() = 2

    override fun startActivityForResult() {
        Toast.makeText(fragment.context, "Card IO is hidden because affect PayCard", Toast.LENGTH_LONG).show()
//        TODO it is commented, because affects PayCard implementation
//        val intent = with(Intent(fragment, CardIOActivity::class.java)) {
//            putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true) // default: false
//            putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true) // default: false
//            putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, true) // default: false
//        }
//        fragment.startActivityForResult(intent, requestCode())
    }

    override fun handleActivityResult(resultCode: Int, data: Intent?) {
//        TODO it is commented, because affects PayCard implementation
//        data?.let {
//            val card: CreditCard = it.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)
//            with(card) {
//                val cardData = "CardIO\n"+
//                        "Card number: $redactedCardNumber\n" +
//                        "Card holder: $cardholderName\n" +
//                        "Card expiration date: $expiryMonth/$expiryYear\n" +
//                        "CVV: $cvv\n" +
//                        "Card type $cardType\n" +
//                        "Postal Code: $postalCode"
//                Log.i(TAG, "Card info: $cardData")
//                Toast.makeText(applicationContext, cardData, Toast.LENGTH_LONG).show()
//            }
//        }
    }
}