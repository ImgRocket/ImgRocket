package cn.imgrocket.imgrocket.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import cn.imgrocket.imgrocket.tool.Function
import cn.imgrocket.imgrocket.R
import cn.imgrocket.imgrocket.room.model.TaskItem

class ProcessingRecyclerAdapter(private val items: ArrayList<TaskItem>, val context: Context) : RecyclerView.Adapter<ProcessingRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_processing, parent, false)
        view.findViewById<ImageView>(R.id.processing_item_op).setOnClickListener(OnClick())
        view.setOnLongClickListener(OnLongClick())
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = items[position].id
        holder.progress.progress = items[position].progress
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(R.id.processing_item_text)
        var progress: ProgressBar = itemView.findViewById(R.id.processing_progress)
    }

    class OnClick : View.OnClickListener {
        override fun onClick(v: View) {
            Function.toast(v.resources.getString(R.string.this_function_is_unavailable))
        }
    }

    class OnLongClick : View.OnLongClickListener {
        override fun onLongClick(v: View): Boolean {
            Function.toast(v.resources.getString(R.string.this_function_is_unavailable))
            return true
        }
    }

    fun onUpdate(items: ArrayList<TaskItem>) {
        this.items.clear()
        this.items.addAll(items.filter { it.progress != 100 })
        notifyDataSetChanged()
    }

}
