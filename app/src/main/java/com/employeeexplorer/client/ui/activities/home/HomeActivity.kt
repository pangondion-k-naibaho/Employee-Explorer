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
import com.employeeexplorer.client.ui.activities.detail.DetailActivity
import com.employeeexplorer.client.ui.custom_components.InputSearchView
import com.employeeexplorer.client.ui.custom_components.PopUpNotificationListener
import com.employeeexplorer.client.ui.custom_components.showPopUpNotification
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
            setLayoutForLoading(it)
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
                            startActivity(DetailActivity.newIntent(this@HomeActivity, item))
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
                        rvEmployee.visibility = View.VISIBLE
                        tvEmpty.visibility = View.GONE
                    })
                    isvHome.clearText()
                }
            }

            btnTrash.apply {
                setOnClickListener {
                    homeViewModel.getEmptyEmployees()
                    homeViewModel.collectionOtherEmployeesResponse.observe(this@HomeActivity, {otherEmployeeResponse->
                        rvAdapter!!.updateItem(otherEmployeeResponse.employees!!)
                    })
                    rvEmployee.visibility = View.GONE
                    tvEmpty.visibility = View.VISIBLE
                    isvHome.clearText()
                }
            }

            isvHome.apply {
                setTextHelper(getString(R.string.tvSearchViewHint))
                setListener(object: InputSearchView.InputSearchListener{
                    override fun onClickSearch() {
                        homeViewModel.getMalformedEmployees()
                        homeViewModel.collectionOtherEmployeesResponse.observe(this@HomeActivity, {otherEmployeeResponse->
                            setLayoutForLoading(true)
                            this@HomeActivity.showPopUpNotification(
                                textTitle = getString(R.string.popUpNotificationDataEmptyTitle),
                                textDesc = getString(R.string.popUpNotificationDataEmptyDesc),
                                backgroundImage = R.drawable.ic_fail,
                                listener = object: PopUpNotificationListener{
                                    override fun onPopUpClosed() {
                                        setLayoutForLoading(false)
                                        rvAdapter!!.apply {
                                            updateItem(otherEmployeeResponse.employees!!)
                                            setMalform(true)
                                        }
                                    }
                                }
                            )
                        })
                    }

                    override fun onClearSearch() {
                        isvHome.clearText()
                        homeViewModel.getEmployees()
                        homeViewModel.collectionEmployeesResponse.observe(this@HomeActivity, {employeesResponse->
                            rvAdapter!!.updateItem(employeesResponse.employees!!)
                            rvEmployee.visibility = View.VISIBLE
                            tvEmpty.visibility = View.GONE
                        })
                    }

                })
            }
        }
    }

//    private fun setLayoutForPopUp(isShown: Boolean){
//        if(isShown){
//            binding.loadingLayout.visibility = View.VISIBLE
//            binding.pbLoading.visibility = View.GONE
//        }else{
//            binding.loadingLayout.visibility = View.GONE
//            binding.pbLoading.visibility = View.GONE
//        }
//    }
//
//    private fun setLayoutForLoading(isLoading: Boolean){
//        if(isLoading) {
//            binding.loadingLayout.visibility = View.VISIBLE
//            binding.pbLoading.visibility = View.VISIBLE
//        } else {
//            binding.loadingLayout.visibility = View.GONE
//            binding.pbLoading.visibility = View.GONE
//        }
//    }

    private fun setLayoutForLoading(input: Boolean){
        if(input){
            binding.loadingLayout.visibility = View.VISIBLE
            binding.pbLoading.visibility = View.GONE
        }else{
            binding.loadingLayout.visibility = View.GONE
            binding.pbLoading.visibility = View.GONE
        }
    }

}