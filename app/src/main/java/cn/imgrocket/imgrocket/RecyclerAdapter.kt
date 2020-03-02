package cn.imgrocket.imgrocket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.imgrocket.imgrocket.APP.Companion.context

class RecyclerAdapter(private val items: List<String>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_processing, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(R.id.processing_item_text)
    }


}
