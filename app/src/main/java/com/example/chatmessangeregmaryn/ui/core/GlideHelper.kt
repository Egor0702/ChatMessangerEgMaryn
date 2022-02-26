package com.example.chatmessangeregmaryn.ui.core

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.chatmessangeregmaryn.remote.service.ServiceFactory

object GlideHelper {
    fun loadImage(context: Context, path: String?, iv: ImageView, placeholder: Drawable = iv.drawable) {
        Log.d("Egor", "GlideHelper loadImage(context: Context, path: String?, iv: ImageView, placeholder: Drawable = iv.drawabl)")
        val imgPath = ServiceFactory.SERVER_URL + path?.replace("..", "")

        Glide.with(context)
                .load(imgPath)
                .placeholder(placeholder)
                .error(placeholder)
                .into(iv)
    }

    fun loadImage(context: Context, path: String?, iv: ImageView, placeholder: Int) {
        Log.d("Egor", "GlideHelper loadImage(context: Context, path: String?, iv: ImageView, placeholder: In)")
        loadImage(context, path, iv, context.resources.getDrawable(placeholder))
    }
}