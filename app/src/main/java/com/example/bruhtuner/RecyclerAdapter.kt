package com.example.bruhtuner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter ( var tunings: List<TuningMode>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()
{
        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val tuningNameTextView: TextView = itemView.findViewById(R.id.tuningNameTextView)

            val firstStringTextView: TextView = itemView.findViewById(R.id.firstStringTextView)
            val secondStringTextView: TextView = itemView.findViewById(R.id.secondStringTextView)
            val thirdStringTextView: TextView = itemView.findViewById(R.id.thirdStringTextView)
            val fourthStringTextView: TextView = itemView.findViewById(R.id.fourthStringTextView)
            val fifthStringTextView:TextView = itemView.findViewById(R.id.fifthStringTextView)
            val sixthStringTextView:TextView = itemView.findViewById(R.id.sixthStringTextView)

            init{
                itemView.setOnClickListener{
                    v:View ->
                    val position: Int = adapterPosition
                    Toast.makeText(itemView.context,"bruh",Toast.LENGTH_LONG).show()
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v:View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return tunings.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.tuningNameTextView.text = tunings[position].tuningModeName

       holder.firstStringTextView.text = tunings[position].firstStringName
       holder.secondStringTextView.text = tunings[position].secondStringName
       holder.thirdStringTextView.text = tunings[position].thirdStringName
       holder.fourthStringTextView.text = tunings[position].fourthStringName
       holder.fifthStringTextView.text = tunings[position].fifthStringName
       holder.sixthStringTextView.text = tunings[position].sixthStringName
    }
}