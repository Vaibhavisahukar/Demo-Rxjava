package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.RecyclerRowBinding
import com.example.myapplication.model.ResultsItem

class RecyclerViewAdapter(var results : ArrayList<ResultsItem>, var clickListener: OnUserItemClickListener): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    fun setUpdatedData(results : ArrayList<ResultsItem>) {
        this.results = results
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: ResultsItem, action: OnUserItemClickListener){

            binding.result = data

            binding.root.setOnClickListener {
                action.onItemClick(data,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)

        val listItemBinding = RecyclerRowBinding.inflate(view,parent,false)

        return MyViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(results.get(position),clickListener)

    }

    override fun getItemCount(): Int {
        return results.size
    }

    interface OnUserItemClickListener{
        fun onItemClick(item: ResultsItem,position: Int)
    }
}
