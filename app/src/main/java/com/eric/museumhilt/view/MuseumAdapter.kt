package com.eric.museumhilt.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eric.museumhilt.R
import com.eric.museumhilt.model.Museum
import kotlinx.android.synthetic.main.row_museum.view.*

class MuseumAdapter(private var museums:List<Museum>):RecyclerView.Adapter<MViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_museum, parent, false)
        return MViewHolder(view)    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(museums[position])
    }

    override fun getItemCount(): Int {
        return museums.size
    }

    fun update(data:List<Museum>){
        museums= data
        notifyDataSetChanged()
    }
}

class MViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val textViewName: TextView = view.textViewName
    private val imageView: ImageView = view.imageView
    fun bind(museum: Museum) {
        textViewName.text = museum.name
        Glide.with(imageView.context).load(museum.photo).into(imageView)
    }

}
