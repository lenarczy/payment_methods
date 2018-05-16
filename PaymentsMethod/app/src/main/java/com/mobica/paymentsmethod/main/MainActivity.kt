package com.mobica.paymentsmethod.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mobica.paymentsmethod.R
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), StartFragment.OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(myToolbar)
        Timber.tag("LifeCycles")
        val transaction = supportFragmentManager.beginTransaction()
        val startFragment = StartFragment.newInstance()
        transaction.add(R.id.main_fragment_container, startFragment)
        transaction.commit()
        Timber.d("MainActivity created")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("Request code: $requestCode, result code: $resultCode, data: $data")
    }

    override fun onFragmentInteraction(item: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
