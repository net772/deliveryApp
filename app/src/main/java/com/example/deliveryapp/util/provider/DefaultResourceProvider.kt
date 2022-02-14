package com.example.deliveryapp.util.provider

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class DefaultResourceProvider(
    private val context: Context
) : ResourcesProvider{
    override fun getString(@StringRes resId: Int): String = context.getString(resId)

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String = context.getString(resId, formatArgs)

    override fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(context, resId)

    override fun getColorStateList(@ColorRes resId: Int) = context.getColorStateList(resId)
}