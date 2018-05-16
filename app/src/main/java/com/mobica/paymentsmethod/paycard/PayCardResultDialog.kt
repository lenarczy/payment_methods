package com.mobica.paymentsmethod.paycard


import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobica.paymentsmethod.R
import kotlinx.android.synthetic.main.fragment_paycard_result.*


const val CARD_INFO = "card_info"
const val CARD_IMAGE = "card_image"

/**
 * A simple [Fragment] subclass.
 * Use the [ScanResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ScanResultFragment : DialogFragment() {

    private var cardData: String? = null
    private var image: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cardData = it.getString(CARD_INFO)
            image = it.getByteArray(CARD_IMAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.setTitle("Pay card result")
        return inflater.inflate(R.layout.fragment_paycard_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardInfo.text = cardData
        image?.let {
            val bMap = BitmapFactory.decodeByteArray(it, 0, it.size)
            cardImage.setImageBitmap(bMap)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScanResultFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: ByteArray) =
                ScanResultFragment().apply {
                    arguments = Bundle().apply {
                        putString(CARD_INFO, param1)
                        putByteArray(CARD_IMAGE, param2)
                    }
                }
    }
}
