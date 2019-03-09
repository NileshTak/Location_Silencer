package com.nil_projects.locationsilencer

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_list_view.*
import kotlinx.android.synthetic.main.activity_register_silencer.*
import kotlinx.android.synthetic.main.custom_list_view.*
import kotlinx.android.synthetic.main.custom_list_view.view.*

class list_view : AppCompatActivity() {

    val context = this
    var db = databaseHandler(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        read()

    }

    fun read() {
        var data = db.readData()
        var adapter = GroupAdapter<ViewHolder>()
        for (i in 0..(data.size - 1)) {
            var listdata = DataModel(data.get(i).Name!!, data.get(i).Longitude!!, data.get(i).Latitude!!)
            adapter.add(ActivityList(listdata))
            recycler_view.adapter = adapter
        }
//        adapter.setOnItemClickListener { item, view ->
//            db.deleteData(item.itemCount)
//            Toast.makeText(applicationContext,view.id.toString(),Toast.LENGTH_LONG).show()
//            db.readData()
//        }
    }


    class ActivityList(var activityfinal: DataModel) : Item<ViewHolder>() {
        override fun getLayout(): Int {
            return R.layout.custom_list_view
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.textName_list_custom.text = activityfinal.Name
            viewHolder.itemView.latlong_custom.text = activityfinal.Longitude + "," + activityfinal.Latitude

        }
    }
}
