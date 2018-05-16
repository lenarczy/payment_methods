package com.mobica.paymentsmethod.paypal;

import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import cz.msebera.android.httpclient.Header;

/**
 * Paypal payments handler
 */
public class PayPalDelegateHandler {

	private static final String TAG = "PayPal";
	private static final String TEST_SERVER_URL = "https://braintreetestapplication-php.herokuapp.com/";
	private static final String TEST_CHECKOUT_URL = TEST_SERVER_URL + "checkout.php";
	private static final String TEST_TOKEN_URL = TEST_SERVER_URL + "client_token";

	private String clientToken;

	public void updateClientToken() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(TEST_TOKEN_URL, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, String token) {
				Log.d(TAG, "Response after get token from server: " + token);
				clientToken = token;
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				Log.d(TAG, "Failure after get token from server: " + responseString);

			}
		});
	}

	public void startPayPalActivity(Fragment fragment, int requestCode) {
		String token = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJlMWIyZGQ0NDA5ZDUxM2RhMGExNGFlMGE4ZTE3ZmE4MzYyOWRjOTZmNzIyNTc2ZWQzNjlmYmRkNTkwODgwZjJifGNyZWF0ZWRfYXQ9MjAxOC0wNS0xNVQwNjozMDoyMy4zMzM5MDU2NDMrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=";
		//TODO: replace test token with one returned from server
		clientToken = token;
		DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
		fragment.startActivityForResult(dropInRequest.getIntent(fragment.getContext()), requestCode);
	}

	public void handlePaypalData(Intent data) {
		DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
		if (result == null) {
			Log.d(TAG, "No payment data returned");
			return;
		}
		PaymentMethodNonce paymentMethodNonce = result.getPaymentMethodNonce();
		if (paymentMethodNonce == null) {
			Log.d(TAG, "No payment method returned");
			return;
		}
		String nonce = result.getPaymentMethodNonce().getNonce();
		Log.d(TAG, "Result from Paypal activity: " + nonce);
		postNonceToServer(nonce);
	}

	private void postNonceToServer(String nonce) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		//TODO: replace test nonce with one returned from server
		params.put("payment_method_nonce", "fake-valid-nonce");
		params.put("amount", "55");
		client.post(TEST_CHECKOUT_URL, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						Log.d(TAG, "Response after post nonce to server.statusCode: " + statusCode);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
						Log.d(TAG, "Failure response after post nonce to server. statusCode: " + statusCode);
						Log.d(TAG, "Failure response after post nonce to server. Error: " + error.getMessage());
					}
				}
		);
	}
}
