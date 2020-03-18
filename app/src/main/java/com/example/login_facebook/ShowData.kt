package com.example.realtimedatabse


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.login_facebook.Adapter
import com.example.login_facebook.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class ShowData : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_show_data, container, false)
        // Inflate the layout for this fragment

        val mRootRef = FirebaseDatabase.getInstance().reference
        val mMessagesRef = mRootRef.child("data")

        mMessagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val list = JSONArray()
                val listView = view.findViewById<ListView>(R.id.listView)

                for (ds in dataSnapshot.children) {

                    val jObject = JSONObject()

                    val username = ds.child("username").getValue(String::class.java)!!
                    val text = ds.child("text").getValue(String::class.java)!!

                    jObject.put("key",ds.key)
                    jObject.put("username",username)
                    jObject.put("text",text)

                    list.put(jObject)

                }

                val adapter = Adapter(activity!!, list)

                listView.adapter = adapter

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })



        return view
    }


}
