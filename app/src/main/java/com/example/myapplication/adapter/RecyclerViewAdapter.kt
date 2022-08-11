package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.RecyclerRowBinding
import com.example.myapplication.model.ResultsItem

class RecyclerViewAdapter(
    var results: ArrayList<ResultsItem>,
    private var clickListener: OnUserItemClickListener
) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private val originalList: ArrayList<ResultsItem> = ArrayList()
    private var selectedFilter: String = "all"

    fun setUpdatedData(filteredUsers: ArrayList<ResultsItem>, isFromFilter: Boolean) {
        if (!isFromFilter) {
            originalList.addAll(filteredUsers)
        }
        if (isFromFilter) {
            this.results.clear()
        }
        this.results.addAll(filteredUsers)
        notifyDataSetChanged()
    }

    fun removeItem(item: Int) {
        this.results.removeAt(item)
        notifyItemRemoved(item)
    }

    class MyViewHolder(private val binding: RecyclerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ResultsItem, action: OnUserItemClickListener) {

            binding.result = data
            binding.root.setOnClickListener {
                action.onItemClick(data, absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)

        val listItemBinding = RecyclerRowBinding.inflate(view, parent, false)

        return MyViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    interface OnUserItemClickListener {
        fun onItemClick(item: ResultsItem, position: Int)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(results[position], clickListener)
    }

    fun filterList(gender: String) {
        val userList: List<ResultsItem> =
            originalList.filter { s -> s.gender.equals(gender, ignoreCase = true) }
        selectedFilter = gender
        when (gender) {
            "all" -> {
                setUpdatedData(originalList, true)
            }
            "female", "male" -> {
                setUpdatedData(userList as ArrayList<ResultsItem>, true)
            }
        }
    }
}
