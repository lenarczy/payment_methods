package com.mobica.paymentsmethod.gpay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*
import com.mobica.paymentsmethod.R
import com.stripe.android.model.Token
import timber.log.Timber

class GPayActivity : AppCompatActivity() {

    companion object {
        private const val LOAD_PAYMENT_DATA_REQUEST_CODE = 28
    }
    private var paymentsClient: PaymentsClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpay)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Handler().postDelayed(Runnable {
            val request = createPaymentDataRequest()
            paymentsClient?.let {
                AutoResolveHelper.resolveTask(it.loadPaymentData(request), this@GPayActivity,
                        LOAD_PAYMENT_DATA_REQUEST_CODE)
                // LOAD_PAYMENT_DATA_REQUEST_CODE is a constant integer of your choice,
                // similar to what you would use in startActivityForResult
            }
        }, 100L)

        paymentsClient = Wallet.getPaymentsClient(applicationContext, Wallet.WalletOptions
                .Builder().setEnvironment(WalletConstants.ENVIRONMENT_TEST).build())
        isReadyToPay()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE ->
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        data?.let {
                            val paymentData = PaymentData.getFromIntent(it)
                            // You can get some data on the user's card, such as the brand and last 4 digits
                            paymentData?.let {
                                val info = it.cardInfo
                                // You can also pull the user address from the PaymentData object.
                                val address = it.shippingAddress
                                // This is the raw JSON string version of your Stripe token.
                                val rawToken = it.paymentMethodToken.token

                                // Now that you have a Stripe token object, charge that by using the id
                                val stripeToken = Token.fromString(rawToken)
                                stripeToken?.let {
                                    chargeToken(it.id)
                                }
                            }
                        }
                    }
                    AutoResolveHelper.RESULT_ERROR -> {
                        val status = AutoResolveHelper.getStatusFromIntent(data);
                        // Log the status for debugging
                        // Generally there is no need to show an error to
                        // the user as the Google Payment API will do that
                    }
                    else -> Timber.d("Result code other then expected $resultCode")
                }
            else -> Timber.d("Request code other then expected $requestCode")
        }
        onBackPressed()
    }

    private fun chargeToken(id: String) {
        Toast.makeText(applicationContext, "Token id $id", Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun isReadyToPay() {
        val request = IsReadyToPayRequest.newBuilder()
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD).build()
        paymentsClient?.let {
            val task = it.isReadyToPay(request)
            task.addOnCompleteListener {
                try {
                    val result = task.getResult(ApiException::class.java)
                    if(result) {
                        //show Google as payment option
                    } else {
                        //hide Google as payment option
                    }
                } catch (exception: ApiException) { }
            }
        }

    }

    private fun createTokenizationParameters(): PaymentMethodTokenizationParameters {
        return PaymentMethodTokenizationParameters.newBuilder()
                .setPaymentMethodTokenizationType(WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_PAYMENT_GATEWAY)
                .addParameter("gateway", "example")
                .addParameter("gatewayMerchantId", "exampleGatewayMerchantId")
                .build()
    }

    private fun createPaymentDataRequest(): PaymentDataRequest {
        val request = PaymentDataRequest.newBuilder()
                .setTransactionInfo(
                        TransactionInfo.newBuilder()
                                .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                                .setTotalPrice("10.00")
                                .setCurrencyCode("PLN")
                                .build())
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                .setCardRequirements(
                        CardRequirements.newBuilder()
                                .addAllowedCardNetworks(listOf(
                                        WalletConstants.CARD_NETWORK_AMEX,
                                        WalletConstants.CARD_NETWORK_DISCOVER,
                                        WalletConstants.CARD_NETWORK_VISA,
                                        WalletConstants.CARD_NETWORK_MASTERCARD))
                                .build())

        request.setPaymentMethodTokenizationParameters(createTokenizationParameters())
        return request.build()
    }

}
