package com.ghartakshiksha.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.ghartakshiksha.R
import com.ghartakshiksha.databinding.ActivityClassAndSubjectBinding
import com.ghartakshiksha.network.model.ClassAndSubjectData
import com.ghartakshiksha.ui.adapter.ClassAdapter
import com.ghartakshiksha.ui.viewmodel.ProfileViewModel
import com.ghartakshiksha.utility.setStatusBarColor
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ClassAndSubjectActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityClassAndSubjectBinding
    lateinit var classArrayList: ArrayList<ClassAndSubjectData>
    lateinit var classAdapter: ClassAdapter
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassAndSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarColor(Color.parseColor("#ED9030"))
        supportActionBar?.hide()
        initViewAndListener()
        profileViewModel.getClassAndSubject()

    }

    private fun initViewAndListener() {
        classArrayList = ArrayList<ClassAndSubjectData>()
        binding.ibBack.setOnClickListener(this)

        setDropDownData()
        setObserver()
    }

    private fun setObserver() {
        profileViewModel.classAndSubjectResponse.observeForever {
            if (it.data.isNotEmpty()) {
                classArrayList = it.data
                classAdapter.addItems(classArrayList)
            }
        }
    }


    private fun setDropDownData() {
        classAdapter = ClassAdapter(this, R.layout.dropdown_item, classArrayList)
        binding.spinnerClass.adapter = classAdapter
        /*  binding.spinnerClass.setOnItemClickListener { parent, _, position, _ ->
              val city = classAdapter.getItem(position) as ClassAndSubjectData?
              // citySpinner.setText(city?.definition)
          }*/

    }

    override fun onClick(view: View?) {
        if (view == binding.ibBack) {
            finish()
        }

    }

}