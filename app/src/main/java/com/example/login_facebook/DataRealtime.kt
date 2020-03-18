package com.example.login_facebook


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.login_facebook.R
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
class DataRealtime : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_data_realtime, container, false)
        // Inflate the layout for this fragment

        val btn1 = view.findViewById<Button>(R.id.btn1)
        val btn2 = view.findViewById<Button>(R.id.btn2)
        val btn3 = view.findViewById<Button>(R.id.btn3)
        val btn4 = view.findViewById<Button>(R.id.btn4)

        //ประกาศตัวแปร DatabaseReference รับค่า Instance และอ้างถึง path ที่เราต้องการใน database
        val mRootRef = FirebaseDatabase.getInstance().getReference()

        //อ้างอิงไปที่ path ที่เราต้องการจะจัดการข้อมูล ตัวอย่างคือ users และ messages
        val mUsersRef = mRootRef.child("users")
        val mMessagesRef = mRootRef.child("messages")


        btn1.setOnClickListener {
            //setValue() เป็นการ write หรือ update ข้อมูล ไปยัง path ที่เราอ้างถึงได้ เช่น users/<user-id>/<username>
            mUsersRef.child("id-59160658").setValue("Thananan")
        }

        btn2.setOnClickListener {
            val friendlyMessage = FriendlyMessage("59160658", "HAHAHA");
            mMessagesRef.push().setValue(friendlyMessage);
        }

        btn3.setOnClickListener {
            // push เป็นการ generate $postid ของ object ชื่อ posts ออกมาก่อนเพื่อใช้ใน // /user-posts/$userid/$postid
            val key = mMessagesRef.push().key
            val postValues: HashMap<String, Any> = HashMap()
            postValues["username"] = "59160658"
            postValues["text"] = "HAHAHA"

            val childUpdates: MutableMap<String, Any> = HashMap()

            childUpdates["/messages/$key"] = postValues

            childUpdates["/user-messages/Thananan/$key"] = postValues

            mMessagesRef.updateChildren(childUpdates)

        }

        btn4.setOnClickListener {
            val mMessagesRef2 = mRootRef.child("data")

            val key = mMessagesRef.push().key
            val postValues: HashMap<String, Any> = HashMap()
            postValues["username"] = "59160658"
            postValues["text"] = "HAHAHA"

            val childUpdates: MutableMap<String, Any> = HashMap()

            childUpdates["$key"] = postValues

            mMessagesRef2.updateChildren(childUpdates)

        }

        return view

    }

    data class FriendlyMessage(
        var username: String? = "",
        var text: String? = ""
    )

}

