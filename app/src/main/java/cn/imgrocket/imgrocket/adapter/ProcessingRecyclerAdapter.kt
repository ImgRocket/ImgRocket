package cn.imgrocket.imgrocket.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import cn.imgrocket.imgrocket.APP.Companion.context
import cn.imgrocket.imgrocket.R

class ProcessingRecyclerAdapter(private val items: List<String>) : RecyclerView.Adapter<ProcessingRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_processing, parent, false)
        view.setOnClickListener(OnClick());
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(R.id.processing_item_text)
        var icon: ImageView = itemView.findViewById(R.id.processing_item_icon)
        var op: ImageView = itemView.findViewById(R.id.processing_item_op)
        var progress: ProgressBar = itemView.findViewById(R.id.processing_progress)


    }

    inner class OnClick : View.OnClickListener {

        override fun onClick(v: View?) {
            Toast.makeText(context, "哈哈哈", Toast.LENGTH_LONG).show();
            v!!.findViewById<TextView>(R.id.processing_item_text).text = "123"
        }

    }


}
