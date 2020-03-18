package com.example.myapplication10_1_63

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login_facebook.R
import org.json.JSONArray

class MyRecyclerAdapter (fragmentActivity: FragmentActivity, val dataSource: JSONArray) : RecyclerView.Adapter<MyRecyclerAdapter.Holder>(){
    private val thiscontext : Context = fragmentActivity.baseContext
    private val thisActivity = fragmentActivity

    class Holder(view : View) : RecyclerView.ViewHolder(view) {
        private val View = view;

        lateinit var layout : LinearLayout
        lateinit var titleTextView: TextView
        lateinit var detailTextView: TextView
        lateinit var image: ImageView
        lateinit var price: TextView


        fun Holder(){

            layout = View.findViewById<View>(R.id.recyview_layout) as LinearLayout
            titleTextView = View.findViewById<View>(R.id.tv_name) as TextView
            detailTextView = View.findViewById<View>(R.id.tv_description) as TextView
            image = View.findViewById<View>(R.id.imgV) as ImageView
            price = View.findViewById<View>(R.id.price) as TextView


        }

    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): Holder {

        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.recy_layout, parent, false))

    }

    override fun getItemCount(): Int {
        return dataSource.length()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.Holder()
        holder.price.setText( dataSource.getJSONObject(position).getString("price").toString() )
        holder.titleTextView.setText( dataSource.getJSONObject(position).getString("title").toString() )
        //holder.detailTextView.setText( dataSource.getJSONObject(position).getString("description").toString() )

        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("image").toString())
            .into(holder.image)

        holder.layout.setOnClickListener{
            val send_img :String = dataSource.getJSONObject(position).getString("image").toString()
            val send_det :String = dataSource.getJSONObject(position).getString("description").toString()
            //Toast.makeText(thiscontext,holder.titleTextView.text.toString(),Toast.LENGTH_SHORT).show()
            val fragment_detail = detail().newInstance(holder.price.text.toString(),holder.titleTextView.text.toString(),send_det,send_img)
            val fm = thisActivity.supportFragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout, fragment_detail, "fragment_detail")
            transaction.addToBackStack("fragment_detail")
            transaction.commit()
        }

    }


}

