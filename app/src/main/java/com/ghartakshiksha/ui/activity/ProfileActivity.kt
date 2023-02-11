package com.ghartakshiksha.ui.activity

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.ghartakshiksha.R
import com.ghartakshiksha.databinding.ActivityProfileBinding
import com.ghartakshiksha.network.model.ClassAndSubjectData
import com.ghartakshiksha.ui.adapter.ClassAdapter
import com.ghartakshiksha.ui.adapter.CommonDropdownAdapter
import com.ghartakshiksha.ui.viewmodel.ProfileViewModel
import com.ghartakshiksha.utility.setStatusBarColor
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarColor(Color.parseColor("#ED9030"))
        supportActionBar?.hide()
        initViewAndListener()
        profileViewModel.getClassAndSubject()

    }

    private fun initViewAndListener() {
        binding.ibBack.setOnClickListener(this)

        setDropDownData()
        setObserver()
    }

    private fun setObserver() {
        profileViewModel.classAndSubjectResponse.observeForever {

        }
    }


    private fun setDropDownData() {
        val genderArrayList = kotlin.collections.ArrayList<String>()
        genderArrayList.add("Select gender")
        genderArrayList.add("Male")
        genderArrayList.add("Female")
        val genderAdapter = CommonDropdownAdapter(this, R.layout.dropdown_item, genderArrayList)
        binding.spinnerGender.adapter = genderAdapter
        /*  binding.spinnerClass.setOnItemClickListener { parent, _, position, _ ->
              val city = classAdapter.getItem(position) as ClassAndSubjectData?
              // citySpinner.setText(city?.definition)
          }*/

    }

    private fun openDatePicker() {
        val cal = Calendar.getInstance()
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                binding.tvDOB.text = sdf.format(cal.time)


            }
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.CalenderDialog,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    override fun onClick(view: View?) {
        if (view == binding.ibBack) {
            finish()
        }

    }

}