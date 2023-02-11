package com.ghartakshiksha.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ghartakshiksha.R

class CommonDropdownAdapter(
    private val mContext: Context,
    private val mLayoutResourceId: Int,
    arrayList: ArrayList<String>
) :
    ArrayAdapter<String>(mContext, mLayoutResourceId, arrayList) {
    private val data: MutableList<String> = ArrayList(arrayList)


    fun addItems(items: ArrayList<String>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): String {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(mLayoutResourceId, parent, false)
        }
        try {
            val tvDropDown =
                convertView!!.findViewById<View>(R.id.tvDropdownItem) as TextView
            tvDropDown.text = data[position]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }
}