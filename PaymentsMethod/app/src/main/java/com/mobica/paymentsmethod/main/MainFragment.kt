package com.mobica.paymentsmethod.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobica.paymentsmethod.PaymentMethodDelegate
import com.mobica.paymentsmethod.R
import com.mobica.paymentsmethod.cardio.CardIODelegate
import com.mobica.paymentsmethod.gpay.GPayDelegate
import com.mobica.paymentsmethod.paycard.CARD_IMAGE
import com.mobica.paymentsmethod.paycard.CARD_INFO
import com.mobica.paymentsmethod.paycard.PayCardDelegate
import com.mobica.paymentsmethod.paypal.PayPalDelegate
import kotlinx.android.synthetic.main.fragment_start.*
import timber.log.Timber


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [StartFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StartFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val methodDelegates = HashMap<String, PaymentMethodDelegate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(CARD_INFO)
            param2 = it.getString(CARD_IMAGE)
        }
        methodDelegates.apply {
            put("Pay Card", PayCardDelegate(this@StartFragment))
            put("Card IO", CardIODelegate(this@StartFragment))
            put("Google Pay", GPayDelegate(this@StartFragment))
            put( "Pay pal", PayPalDelegate(this@StartFragment))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentMethodList.layoutManager = LinearLayoutManager(context)
        paymentMethodList.adapter = PaymentMethodsAdapter(methodDelegates.keys.toList()) {
            val delegate = methodDelegates[it]
            delegate?.let { it.startActivityForResult() }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(id: Int) {
        listener?.onFragmentInteraction(id)
        Timber.d("Instance startActivityResult $this")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        Timber.d("Clear payment methods")
        methodDelegates.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d( "OnFragmentResult $requestCode $resultCode $data")
        methodDelegates.values.forEach {
            val handled = it.onActivityResult(requestCode, resultCode, data)
            if (handled) {
                Timber.d("OnActivityResult is handled by $it")
                return@forEach
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(item: Int)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String = "", param2: String = "") =
                StartFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }

        private val methods = listOf("Pay Card", "Card IO", "Google Pay", "Pay pal")
    }
}
