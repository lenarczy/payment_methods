package com.mobica.paymentsmethod.paycard

import android.app.Activity
import android.content.Intent
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import com.mobica.paymentsmethod.PaymentMethodDelegate
import timber.log.Timber

class PayCardDelegate(private val fragment: Fragment): PaymentMethodDelegate {

    override fun requestCode() = 1

    override fun startActivityForResult() {
        val intent = ScanCardIntent.Builder(fragment.context)
                .setSoundEnabled(true)
                .setSaveCard(true)
                .build()
        fragment.startActivityForResult(intent, requestCode())
    }

    override fun handleActivityResult(resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> data?.let { intent ->
                val card: Card = intent.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD)
                with(card) {
                    val cardData = "PayCards\n"+
                            "Card number: $cardNumberRedacted\n" +
                            "Card holder: $cardHolderName\n" +
                            "Card expiration date: $expirationDate"
                    Timber.d("Card info: $cardData")
                    fragment.activity?.let { activity ->
                        val transaction = activity.supportFragmentManager.beginTransaction()
                        val fragment: DialogFragment = ScanResultFragment.newInstance(cardData, intent.getByteArrayExtra(ScanCardIntent.RESULT_CARD_IMAGE))
                        fragment.show(transaction, null)
                    }

                }
            }
            Activity.RESULT_CANCELED -> Timber.i("Scan canceled")
            else -> Timber.i( "Scan failed")
        }
    }
}