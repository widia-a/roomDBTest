package com.bcaf.roomdbtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.roomdbtest.R
import com.bcaf.roomdbtest.model.PostDummyData

class roomDBAdapter(val response:List<PostDummyData>): RecyclerView.Adapter<roomDBViewHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): roomDBViewHolder {
        context = parent.context

        return roomDBViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_result, parent, false))
    }

    override fun onBindViewHolder(holder: roomDBViewHolder, position: Int) {
        holder.setData(context, response.get(position), position)
    }

    override fun getItemCount() = response.size


}