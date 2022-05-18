package com.example.albertsonsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.albertsonsapp.R
import com.example.albertsonsapp.databinding.MainItemBinding

class ExampleViewHolder(var mExampleItemBinding: MainItemBinding) :  RecyclerView.ViewHolder(mExampleItemBinding.root) {
}

class MainAdapter : RecyclerView.Adapter<ExampleViewHolder>() {
    var meaningsList = mutableListOf<String>()

    fun setMeaningsList2(meanings: List<String>) {
        this.meaningsList = meanings.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        // val binding = AdapterMovieBinding.inflate(inflater, parent, false)

        val exampleItemBinding = DataBindingUtil.inflate<MainItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.main_item,
            parent, false
        )

        return ExampleViewHolder(exampleItemBinding)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.mExampleItemBinding.textView1.text = (position + 1).toString()
        holder.mExampleItemBinding.textView2.text = meaningsList[position]

    }

    override fun getItemCount(): Int {
        return meaningsList.size
    }

}
