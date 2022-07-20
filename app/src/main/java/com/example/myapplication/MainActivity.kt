package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.RecyclerViewAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.ResultsItem
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(),RecyclerViewAdapter.OnUserItemClickListener {

    lateinit var mbinding : ActivityMainBinding

    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        (application as UserApplication).applicationComponent.inject(this)

        mainViewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)

        mainViewModel.getModelMutableLiveData().observe(this, Observer {
            if(it != null)
                recyclerViewAdapter.setUpdatedData(it.results as ArrayList<ResultsItem>)
            else
                Toast.makeText(this,"Error in getting the data", Toast.LENGTH_SHORT).show()
        })

        mbinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)

            val result = ArrayList<ResultsItem>()

            recyclerViewAdapter = RecyclerViewAdapter(result,this@MainActivity)
            adapter = recyclerViewAdapter
        }
    }

    override fun onItemClick(item: ResultsItem, position: Int) {
        val intent : Intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("User item",item)
        startActivity(intent)
    }
}
