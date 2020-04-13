package cn.imgrocket.imgrocket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.imgrocket.imgrocket.R
import cn.imgrocket.imgrocket.tool.APP

class UploadRecyclerAdapter(private val items: List<String>) : RecyclerView.Adapter<UploadRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(APP.context).inflate(R.layout.item_upload, parent, false)
        return UploadRecyclerAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size;

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}