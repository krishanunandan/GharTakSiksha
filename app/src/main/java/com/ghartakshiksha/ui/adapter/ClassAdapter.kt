package com.ghartakshiksha.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ghartakshiksha.R
import com.ghartakshiksha.network.model.ClassAndSubjectData


class ClassAdapter(
    private val mContext: Context,
    private val mLayoutResourceId: Int,
    classArrayList: ArrayList<ClassAndSubjectData>
) :
    ArrayAdapter<ClassAndSubjectData>(mContext, mLayoutResourceId, classArrayList) {
    private val classArray: MutableList<ClassAndSubjectData> = ArrayList(classArrayList)


    fun addItems(items: ArrayList<ClassAndSubjectData>) {
        classArray.addAll(items)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return classArray.size
    }

    override fun getItem(position: Int): ClassAndSubjectData {
        return classArray[position]
    }

    override fun getItemId(position: Int): Long {
        return classArray[position].id.toLong()
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
            tvDropDown.text = classArray[position].className
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertView!!
    }
}