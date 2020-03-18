package com.example.myapplication10_1_63


import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.DialogInterface
import android.icu.text.CaseMap
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.login_facebook.R
import com.example.login_facebook.profile
import com.example.realtimedatabse.ShowData
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class detail : Fragment() {
    private var title : String? = null
    private var detail : String? = null
    private var image : String? = null
    private var price : String? = null
    private var bnt_com : Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val layoutPrice =view?.findViewById<TextView>(R.id.price)
        val layoutTitle =view?.findViewById<TextView>(R.id.title)
        val layoutDetail = view?.findViewById<TextView>(R.id.det)
        val layoutImage  = view.findViewById<ImageView>(R.id.image)

        layoutPrice?.text = this.price
        layoutTitle?.text = this.title
        layoutDetail?.text = this.detail

        Glide.with(activity!!.baseContext)
            .load(image)
            .into(layoutImage)


        //alert---------------------------------------------------
        val mRootRef = FirebaseDatabase.getInstance().getReference()

        //อ้างอิงไปที่ path ที่เราต้องการจะจัดการข้อมูล ตัวอย่างคือ users และ messages
        val mUsersRef = mRootRef.child("users")
        val mMessagesRef = mRootRef.child("messages")
        bnt_com = view?.findViewById<Button>(R.id.buy_now)
        bnt_com!!.setOnClickListener {
            Toast.makeText(context,"Add to cart Success", Toast.LENGTH_SHORT).show()
            val mMessagesRef2 = mRootRef.child("data")

            val key = mMessagesRef.push().key
            val postValues: HashMap<String, Any> = HashMap()
            postValues["username"] = title.toString()
            postValues["text"] = "1"

            val childUpdates: MutableMap<String, Any> = HashMap()

            childUpdates["$key"] = postValues

            mMessagesRef2.updateChildren(childUpdates)
        }
        val bnt_cart = view?.findViewById<Button>(R.id.cart_order)
        bnt_cart!!.setOnClickListener {
            val authen = ShowData()
            val manager = activity!!.supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.layout, authen,"fragment_authen")
            transaction.addToBackStack("fragment_authen")
            transaction.commit()
        }
        return view
    }

    fun newInstance(price: String,title: String, detail: String, image: String): detail{
        var fragment = detail()
        var bundle = Bundle()
        bundle.putString("price", price)
        bundle.putString("title", title)
        bundle.putString("detail", detail)
        bundle.putString("image",image)

        fragment.setArguments(bundle)
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var bundle  = arguments
        if (bundle != null){
            price = bundle.getString("price").toString()
            title = bundle.getString("title").toString()
            detail = bundle.getString("detail").toString()
            image = bundle.getString("image").toString()

        }
    }


}
