package com.example.login_facebook


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.login_facebook.R
import com.google.firebase.database.FirebaseDatabase
import android.content.DialogInterface
import android.widget.Toast
import android.app.AlertDialog
import com.facebook.FacebookSdk.getApplicationContext


/**
 * A simple [Fragment] subclass.
 */
class Edit : Fragment() {

    var Str_key = ""
    var Str_username = ""
    var Str_text = ""

    fun newInstance(key: String,username: String,text: String): Edit {

        val fragment = Edit()
        val bundle = Bundle()
        bundle.putString("key", key)
        bundle.putString("username", username)
        bundle.putString("text", text)
        fragment.setArguments(bundle)

        return fragment
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        // Inflate the layout for this fragment

        //val key = view.findViewById<TextView>(R.id.key)
        val edit_username = view.findViewById<TextView>(R.id.edit_username)
        val edit_text = view.findViewById<TextView>(R.id.edit_text)
        val sendData = view.findViewById<Button>(R.id.sendData)
        val delData = view.findViewById<Button>(R.id.delData)

        //key.setText("KEY : " + Str_key)
        edit_username.setText("ชื่อสินค้า : " + Str_username)
        edit_text.setText(Str_text)

        sendData.setOnClickListener {
            val mRootRef = FirebaseDatabase.getInstance().getReference()
            val mMessagesRef = mRootRef.child("data")

            mMessagesRef.child(Str_key).child("text").setValue(edit_text.text.toString())

            val fm: FragmentManager = activity!!.getSupportFragmentManager()
            fm.popBackStack("fragment_Edit", FragmentManager.POP_BACK_STACK_INCLUSIVE)

        }

        delData.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setMessage("Delete product?")
            builder.setPositiveButton("Cancle", DialogInterface.OnClickListener { dialog, id ->
            })
            builder.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, which ->

            val mRootRef = FirebaseDatabase.getInstance().getReference()
            val mMessagesRef = mRootRef.child("data").child(Str_key)

            mMessagesRef.setValue(null)

            val fm: FragmentManager = activity!!.getSupportFragmentManager()
            fm.popBackStack("fragment_Edit", FragmentManager.POP_BACK_STACK_INCLUSIVE)

            })
            builder.show()
//            val mRootRef = FirebaseDatabase.getInstance().getReference()
//            val mMessagesRef = mRootRef.child("data").child(Str_key)
//
//            mMessagesRef.setValue(null)
//
//            val fm: FragmentManager = activity!!.getSupportFragmentManager()
//            fm.popBackStack("fragment_Edit", FragmentManager.POP_BACK_STACK_INCLUSIVE)


        }

        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            Str_key = bundle.getString("key").toString()
            Str_username = bundle.getString("username").toString()
            Str_text = bundle.getString("text").toString()

        }

    }

}
