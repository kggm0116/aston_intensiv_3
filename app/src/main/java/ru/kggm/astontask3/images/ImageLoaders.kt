package ru.kggm.astontask3.images

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import coil.load
import coil.request.CachePolicy
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

sealed class ImageLoader {
    abstract fun load(
        context: Context,
        url: String,
        imageView: AppCompatImageView,
        onError: () -> Unit
    )
}

object GlideLoader : ImageLoader() {
    override fun load(
        context: Context,
        url: String,
        imageView: AppCompatImageView,
        onError: () -> Unit
    ) {
        Glide.with(context)
            .load(url)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onError()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ) = false
            })
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}

object CoilLoader : ImageLoader() {
    override fun load(
        context: Context,
        url: String,
        imageView: AppCompatImageView,
        onError: () -> Unit
    ) {
        imageView.load(url) {
            crossfade(true)
            listener(onError = { _, _ -> onError() })
        }
    }

}

object PicassoLoader : ImageLoader() {
    override fun load(
        context: Context,
        url: String,
        imageView: AppCompatImageView,
        onError: () -> Unit
    ) {
        if (url == "") {
            return
        }
        Picasso.get()
            .load(url)
            .into(imageView, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception?) {
                    onError()
                }
            })
    }
}

object NativeLoader : ImageLoader() {
    override fun load(
        context: Context,
        url: String,
        imageView: AppCompatImageView,
        onError: () -> Unit
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = withContext(Dispatchers.IO) {
                try {
                    BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
                } catch (e: Exception) {
                    null
                }
            }
            if (bitmap == null) {
                onError()
                return@launch
            }
            imageView.setImageBitmap(bitmap)
        }
    }
}