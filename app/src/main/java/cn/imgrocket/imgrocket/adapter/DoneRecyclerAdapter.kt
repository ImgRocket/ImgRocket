package cn.imgrocket.imgrocket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.imgrocket.imgrocket.APP
import cn.imgrocket.imgrocket.R

class DoneRecyclerAdapter(private val items: List<String>) : RecyclerView.Adapter<DoneRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(APP.context).inflate(R.layout.item_done, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}