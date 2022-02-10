package com.example.chatmessangeregmaryn.ui.core

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatmessangeregmaryn.R

abstract class BaseListFragment : BaseFragment() { // Для выделения поведения фрагментов содержащих список.
    init {
        Log.d("Egor", "Всем хло, мы в BaseListFragment")
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var lm: RecyclerView.LayoutManager

    protected abstract val viewAdapter: BaseAdapter<*>

    override val layoutId = R.layout.fragment_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Egor", "BaseListFragment onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
        lm = LinearLayoutManager(context)

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = lm
            adapter = viewAdapter
        }
    }

    protected fun setOnItemClickListener(func: (Any?, View) -> Unit) {
        Log.d("Egor", "BaseListFragment onViewCreated()")
        viewAdapter.setOnClick(func)
    }

    protected fun setOnItemLongClickListener(func: (Any?, View) -> Unit) {
        Log.d("Egor", "BaseListFragment onViewCreated()")
        viewAdapter.setOnClick({_,_ ->}, longClick = func)
    }
}