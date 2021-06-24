package com.example.note_mvvm_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.note_mvvm_app.databinding.ListItemBinding
import com.example.note_mvvm_app.db.Subscriber

class MyRecyclerViewAdapter(private  val subscribersList: List<Subscriber>,private val clickListener:(Subscriber)->Unit) : RecyclerView.Adapter<MyViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {

        val layoutInflater=LayoutInflater.from(parent.context)
        val binding:ListItemBinding=DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return  MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position],clickListener)

    }

    override fun getItemCount(): Int {
        return  subscribersList.size
    }

}

class MyViewHolder(private val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(subscriber: Subscriber,clickListener:(Subscriber)->Unit){
        binding.usernameTv.text=subscriber.name
        binding.emailTv.text=subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}