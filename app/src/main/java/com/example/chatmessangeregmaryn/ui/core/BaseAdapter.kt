package com.example.chatmessangeregmaryn.ui.core

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH : BaseAdapter.BaseViewHolder> : RecyclerView.Adapter<VH>() { //базовый класс адаптера
    init {
        Log.d("Egor", "Всем хло, мы в BaseAdapter")
    }

    var items: ArrayList<Any> = ArrayList() // элементы списка, которые будут выводиться в RecyclerView

    var onClick: OnClick? = null // объект слушателя

    abstract val layoutRes: Int // id макета элемента списка

    abstract fun createHolder(view: View, viewType: Int): VH // функция, создающая ViewHolder, будет реализована в классах-наследниках

    override fun getItemCount(): Int { // возвращает количество элементов в списке
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Log.d("Egor", "BaseAdapter onBindViewHolder()")
        holder.bind(getItem(position)) // с помощью данного метода заполняет макет данными

        holder.onClick = onClick // присваиваем вью холдеру слушателя нажатий
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        Log.d("Egor", "BaseAdapter onCreateViewHolder()")
        val v = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return createHolder(v, viewType)
    }


    fun getItem(position: Int): Any { // возвращает из списка элемент
        Log.d("Egor", "BaseAdapter getItem()")
        return items[position]
    }


    fun add(newItem: Any) { // добавляем элемент в список
        Log.d("Egor", "BaseAdapter add(newItem: Any)")
        items.add(newItem)
    }

    fun add(newItems: List<Any>) { // перегружаем мтеод, добавляем элемент в список
        Log.d("Egor", "BaseAdapter add(newItems: List<Any>)")
        items.addAll(newItems)
    }

    fun clear() { // очищаем список
        Log.d("Egor", "BaseAdapter clear()")
        items.clear()
    }


    fun setOnClick(click: (Any?, View) -> Unit, longClick: (Any?, View) -> Unit = {_,_ ->}) { // сеттер для onClick. Принимает функции высшего порядка для простого и длительного нажатия
        Log.d("Egor", "BaseAdapter setOnClick()")
        onClick = object : OnClick {
            override fun onClick(item: Any?, view: View) {
                click(item, view)
            }

            override fun onLongClick(item: Any?, view: View) {
                longClick(item, view)
            }
        }
    }

    interface OnClick {
        fun onClick(item: Any?, view: View)
        fun onLongClick(item: Any?, view: View)
    }

    abstract class BaseViewHolder(protected val view: View) : RecyclerView.ViewHolder(view) { // базовый вьюхолдер для базового адаптера
        var onClick: OnClick? = null // также слушатель нажатий
        var item: Any? = null // элемент списка

        init {
            Log.d("Egor", "BaseViewHolder init")
            view.setOnClickListener { // присваиваем к вью слушателя короткого нажатия
                onClick?.onClick(item, it)
            }
            view.setOnLongClickListener { // и присваиваем слушателя длинного нажатия
                onClick?.onLongClick(item, it)
                true
            }
        }

        protected abstract fun onBind(item: Any) // абстрактная функция, заполняющая макет элемента списка данными

        fun bind(item: Any) { //присваивает элемент списка вью холдеру
            this.item = item

            onBind(item) // делегириуем заполнение вьюхолдера onBind
        }

    }
}