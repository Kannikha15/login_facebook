package com.example.myapplication10_1_63


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import com.example.login_facebook.R
import com.example.login_facebook.authen

/**
 * A simple [Fragment] subclass.
 */
class login : Fragment() {

    private var login : Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_login, container, false)
        // Inflate the layout for this fragment

        login = view.findViewById<Button>(R.id.button)

        login!!.setOnClickListener{
            val fragment_profile = authen()
            val frgMng = fragmentManager
            val transaction : FragmentTransaction = frgMng!!.beginTransaction()
            transaction.replace(R.id.layout, fragment_profile,"Fragment_profile")
            transaction.addToBackStack("Fragment_profile")
            transaction.commit()
        }
        return view;

    }
}
