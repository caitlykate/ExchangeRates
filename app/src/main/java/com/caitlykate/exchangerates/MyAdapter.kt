package com.caitlykate.exchangerates

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(): RecyclerView.Adapter<MyAdapter.ViewHolder>() {      //в <> передаем ViewHolder

    val listArray = ArrayList<ListItem>()
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {   //здесь находим все элементы в шаблоне ч/з findViewById, заполняем наш item_layout
        val tvCode = view.findViewById<TextView>(R.id.tvCode)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvRate = view.findViewById<TextView>(R.id.tvRate)

        fun bind(listItem: ListItem){     //заполняем шаблон, context передаем, чтобы вывести Toast
            tvCode.text = listItem.code
            tvName.text = listItem.name
            tvRate.text = listItem.rate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //надуваем и отрисовываем наш item_layout
        //берем inflater с MainAct потому что там будет все рисоваться
        val inflater = LayoutInflater.from(parent.context) //спец класс, который рисует
        return ViewHolder(inflater.inflate(R.layout.list_item,parent,false))      //запускаем ViewHolder и передаем в него отрисованный шаблон для item
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {      //здесь запускаем ф-ю bind
        holder.bind(listArray[position])
    }

    override fun getItemCount(): Int {
        return listArray.size
    }


    fun updateAdapter(listArrayNew: ArrayList<ListItem>){
        listArray.clear()
        listArray.addAll(listArrayNew)
        notifyDataSetChanged()                  //сообщаем адаптеру, что данные изменились
    }
}