package com.example.note_mvvm_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note_mvvm_app.databinding.ActivityMainBinding
import com.example.note_mvvm_app.db.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao :SubscriberDAO= SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        displaySubscriberList()
        initRecyclerView()

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {

                Toast.makeText(this,it,Toast.LENGTH_LONG).show()

            }
        })

    }


    private fun initRecyclerView(){
        binding.subscriberRecyclerview.layoutManager=LinearLayoutManager(this)
        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        subscriberViewModel.subscribers.observe(this,{
            Log.d("MyTag",it.toString())
            binding.subscriberRecyclerview.adapter=MyRecyclerViewAdapter(it,{selectedItem:Subscriber->listItemClicked(selectedItem)})
        })
    }

    private  fun listItemClicked(subscriber: Subscriber){

        subscriberViewModel.initUpdateAndDelete(subscriber)

    }
}