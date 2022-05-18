package com.example.albertsonsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.albertsonsapp.R
import com.example.albertsonsapp.adapter.MainAdapter
import com.example.albertsonsapp.databinding.ActivityMainBinding
import com.example.albertsonsapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "Albertson"
    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    var mainAdapter: MainAdapter = MainAdapter()
    var recyclerView: RecyclerView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        ///init the View Model
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                                    .apply {
                                        this.setLifecycleOwner(this@MainActivity)
                                        this.viewModel = mainViewModel
                                    }
        // bind RecyclerView
        recyclerView = activityMainBinding?.recyclerView
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))
        recyclerView!!.setHasFixedSize(true)

        activityMainBinding?.recyclerView.adapter = mainAdapter
        mainViewModel.listData.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            mainAdapter.setMeaningsList2(it)
        })

        mainViewModel.errorMessage.observe(this, Observer {
        })
    }
}
