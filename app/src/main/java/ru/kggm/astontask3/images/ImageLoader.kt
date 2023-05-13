package ru.kggm.astontask3.images

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

sealed class ImageLoader() {
    abstract fun load(
        context: Context,
        url: String,
        imageView: AppCompatImageView,
        onError: () -> Unit
    )

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
                .transition(withCrossFade())
                .into(imageView)
        }
    }

    object CoilLoader: ImageLoader() {
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

    object PicassoLoader: ImageLoader() {
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
                    override fun onSuccess() { }
                    override fun onError(e: Exception?) { onError() }
                })
        }

    }

    object NativeLoader: ImageLoader() {
        override fun load(
            context: Context,
            url: String,
            imageView: AppCompatImageView,
            onError: () -> Unit
        ) {

        }
    }
}
