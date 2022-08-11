package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.RecyclerViewAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.ResultsItem
import com.example.myapplication.utils.SwipeGesture
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnUserItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mbinding: ActivityMainBinding
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var mainViewModel: MainViewModel
    private val visibleThreshold = 5
    private var previousTotal = 0
    private val result = ArrayList<ResultsItem>()
    private var loading = true
    var pastVisibleItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        (application as UserApplication).applicationComponent.inject(this)

        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        mainViewModel.allUsers.observe(this, Observer {
            if (it != null)
                recyclerViewAdapter.setUpdatedData(it as ArrayList<ResultsItem>, false)
            else
                Toast.makeText(this, "Error in getting the data", Toast.LENGTH_SHORT).show()
        })

        mbinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration =
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)


            recyclerViewAdapter = RecyclerViewAdapter(result, this@MainActivity)
            adapter = recyclerViewAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) { //check for scroll down
                        visibleItemCount = (layoutManager as LinearLayoutManager).childCount
                        totalItemCount = (layoutManager as LinearLayoutManager).itemCount
                        pastVisibleItems =
                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        if (loading) {
                            if (totalItemCount > previousTotal) {
                                loading = false
                                previousTotal = totalItemCount
                            }
                        }
                        if (!loading && (totalItemCount - visibleItemCount)
                            <= (pastVisibleItems + visibleThreshold)
                        ) {

                            mainViewModel.getUser()

                            loading = true
                        }
                    }
                }
            })
        }

        allFilter.setOnClickListener {
            recyclerViewAdapter.filterList("all")
        }
        femaleFilter.setOnClickListener {
            recyclerViewAdapter.filterList("female")
        }
        maleFilter.setOnClickListener {
            recyclerViewAdapter.filterList("male")
        }

        val swipeGesture = object : SwipeGesture(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        mainViewModel.deleteUser(recyclerViewAdapter.results[viewHolder.absoluteAdapterPosition])
                        recyclerViewAdapter.removeItem(viewHolder.absoluteAdapterPosition)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onItemClick(item: ResultsItem, position: Int) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("User item", item)
        startActivity(intent)
    }
}
