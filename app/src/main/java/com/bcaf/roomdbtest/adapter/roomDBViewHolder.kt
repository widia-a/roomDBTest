package com.bcaf.roomdbtest.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bcaf.roomdbtest.model.PostDummyData
import kotlinx.android.synthetic.main.recycler_result.view.*

class roomDBViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val view = view

    fun setData(context: Context, data: PostDummyData, position: Int){
        view.dText.setText(data.text)
        view.dTags.setText(data.tags.toString())
        view.dLikes.setText(data.likes.toString())
    }
}