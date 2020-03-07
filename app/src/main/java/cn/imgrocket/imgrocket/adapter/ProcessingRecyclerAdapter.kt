package cn.imgrocket.imgrocket.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.imgrocket.imgrocket.R

class ProcessingRecyclerAdapter(private val items: List<HashMap<String, Any>>, val context: Context) : RecyclerView.Adapter<ProcessingRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_processing, parent, false)
        view.findViewById<ImageView>(R.id.processing_item_op).setOnClickListener(OnClick())
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val opImg = arrayListOf<Drawable>(context.resources.getDrawable(R.drawable.ic_pause_black_24dp, null), context.resources.getDrawable(R.drawable.ic_play_arrow_black_24dp, null))
        holder.op.setImageDrawable(opImg[items[position]["op"] as Int])
        holder.text.text = items[position]["text"] as CharSequence?
        //holder.icon.setImageBitmap(items[position]["icon"] as Bitmap?)
        holder.progress.progress = (items[position]["progress"] as Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(R.id.processing_item_text)
        var icon: ImageView = itemView.findViewById(R.id.processing_item_icon)
        var op: ImageView = itemView.findViewById(R.id.processing_item_op)
        var progress: ProgressBar = itemView.findViewById(R.id.processing_progress)

    }

    class OnClick : View.OnClickListener {

        override fun onClick(v: View?) {

        }

    }


}
