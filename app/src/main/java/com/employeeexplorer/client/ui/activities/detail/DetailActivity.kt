package com.employeeexplorer.client.ui.activities.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.employeeexplorer.client.R
import com.employeeexplorer.client.data.Constants
import com.employeeexplorer.client.data.Constants.EXTRA.Companion.EXTRA_EMPLOYEE
import com.employeeexplorer.client.data.Constants.TYPE_CONSTANTS.Companion.FULL_TIME
import com.employeeexplorer.client.data.Constants.TYPE_CONSTANTS.Companion.PART_TIME
import com.employeeexplorer.client.data.model.response.EmployeeResponse
import com.employeeexplorer.client.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private val TAG = DetailActivity::class.java.simpleName
    private lateinit var binding: ActivityDetailBinding
    private lateinit var deliveredEmployee: EmployeeResponse

    companion object{
        fun newIntent(context: Context, employee: EmployeeResponse): Intent = Intent(context, DetailActivity::class.java)
            .putExtra(EXTRA_EMPLOYEE, employee)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
    }

    private fun setUpView(){
        deliveredEmployee = intent.getParcelableExtra<EmployeeResponse>(EXTRA_EMPLOYEE)!!

        binding.apply {
            Glide.with(this@DetailActivity)
                .load(deliveredEmployee.photoUrlLarge)
                .into(ivPhoto)

            tvName.text = deliveredEmployee.fullName
            tvEmail.text = deliveredEmployee.emailAddress
            tvPhoneNum.text = deliveredEmployee.phoneNumber
            tvTeam.text = deliveredEmployee.team

            val employmentType = when(deliveredEmployee.employeeType){
                FULL_TIME -> "Full Time"
                PART_TIME -> "Part Time"
                else -> "Contractor"
            }
            tvType.text = employmentType
            tvBiography.text = deliveredEmployee.biography
        }
    }
}