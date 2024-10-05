package com.employeeexplorer.client.ui.activities.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.employeeexplorer.client.R
import com.employeeexplorer.client.data.model.response.EmployeeResponse
import com.employeeexplorer.client.data.remote.ApiConfig
import com.employeeexplorer.client.data.repository.CollectionEmployeesRepositoryImpl
import com.employeeexplorer.client.databinding.ActivityHomeBinding
import com.employeeexplorer.client.ui.rv_adapters.ItemEmployeeAdapter
import com.employeeexplorer.client.ui.viewmodels.home.HomeViewModel
import com.employeeexplorer.client.ui.viewmodels.home.HomeViewModelFactory

class HomeActivity : ComponentActivity() {
    private val TAG = HomeActivity::class.java.simpleName
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private var rvAdapter: ItemEmployeeAdapter?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiService = ApiConfig.createApiService()
        val collectionEmployeesRepository = CollectionEmployeesRepositoryImpl(apiService)

        val factory = HomeViewModelFactory(collectionEmployeesRepository)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        observeStatus()

        setUpView()
    }

    private fun observeStatus(){
        homeViewModel.isLoading.observe(this@HomeActivity, {
            setLayoutBitDarker(it)
        })

        homeViewModel.isFail.observe(this@HomeActivity, {
            when(it){
                true -> Toast.makeText(this@HomeActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                false -> binding.srlHome.isRefreshing = false
            }
        })
    }

    private fun setUpView(){
        homeViewModel.getEmployees()

        homeViewModel.collectionEmployeesResponse.observe(this@HomeActivity,{employeesResponse->
            binding.rvEmployee.apply {
                rvAdapter = ItemEmployeeAdapter(
                    employeesResponse.employees!!.toMutableList(),
                    object: ItemEmployeeAdapter.ItemListener{
                        override fun onItemClicked(item: EmployeeResponse) {
                            Toast.makeText(this@HomeActivity, "Item Clicked", Toast.LENGTH_SHORT).show()
                        }

                        override fun onItemMalformedClicked() {
                            Toast.makeText(this@HomeActivity, getString(R.string.toastEmployeeMalformedMessage), Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                val rvlayoutManager = LinearLayoutManager(this@HomeActivity)

                adapter = rvAdapter
                layoutManager = rvlayoutManager
            }
        })

        binding.apply {
            srlHome.apply {
                setOnRefreshListener {
                    homeViewModel.getEmployees()
                    homeViewModel.collectionEmployeesResponse.observe(this@HomeActivity, {employeesResponse->
                        rvAdapter!!.updateItem(employeesResponse.employees!!)
                    })
                }
            }

            btnTrash.apply {
                setOnClickListener {
                    homeViewModel.getEmptyEmployees()
                    homeViewModel.collectionOtherEmployeesResponse.observe(this@HomeActivity, {otherEmployeeResponse->
                        rvAdapter!!.updateItem(otherEmployeeResponse.employees!!)

                        if(otherEmployeeResponse.employees.size == 0) {
                            rvEmployee.visibility = View.GONE
                            tvEmpty.visibility = View.VISIBLE
                        }
                    })
                }
            }
        }
    }

    private fun setLayoutForPopUp(isShown: Boolean){
        if(isShown){
            binding.loadingLayout.visibility = View.VISIBLE
            binding.pbLoading.visibility = View.GONE
        }else{
            binding.loadingLayout.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun setLayoutForLoading(isLoading: Boolean){
        if(isLoading) {
            binding.loadingLayout.visibility = View.VISIBLE
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.loadingLayout.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun setLayoutBitDarker(input: Boolean){
        if(input){
            binding.loadingLayout.visibility = View.VISIBLE
            binding.pbLoading.visibility = View.GONE
        }else{
            binding.loadingLayout.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE
        }
    }

}