package ru.kggm.astontask3.images

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import ru.kggm.astontask3.R
import ru.kggm.astontask3.databinding.ActivityImagesBinding

class ImagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImagesBinding
    private var imageLoaderIndex = 0
    private var imageUrlText = ""

    companion object {
        private const val BUNDLE_ARG_IMAGE_LOADER_INDEX = "image_loader"
        private const val BUNDLE_ARG_IMAGE_URL_TEXT = "image_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let { restoreState(it) }
        checkLoaderOption()
        loadImageFromCurrentUrl()

        setViewListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BUNDLE_ARG_IMAGE_LOADER_INDEX, imageLoaderIndex)
    }

    private fun restoreState(bundle: Bundle) {
        bundle.getInt(BUNDLE_ARG_IMAGE_LOADER_INDEX).let {
            imageLoaderIndex = it
        }
        bundle.getString(BUNDLE_ARG_IMAGE_URL_TEXT)?.let {
            imageUrlText = it
        }
    }

    private fun setViewListeners() {
        binding.radioGroupImageLoaders.setOnCheckedChangeListener { _, index ->
            val loader = when (index) {
                binding.optionGlide.id ->ImageLoader.GlideLoader
                binding.optionCoil.id -> ImageLoader.CoilLoader
                binding.optionPicasso.id -> ImageLoader.PicassoLoader
                binding.optionNative.id -> ImageLoader.NativeLoader
                else -> throw Error("Unknown image loader option checkbox was selected")
            }
            imageLoaderIndex = getIndexOfLoader(loader)
            loadImageFromCurrentUrl()
        }
        binding.inputTextImageLink.addTextChangedListener { editable ->
            editable.toString().let { url ->
                if (url != imageUrlText) {
                    imageUrlText = url
                    loadImageFromCurrentUrl()
                }
            }
        }
    }

    private fun loadImageFromCurrentUrl() {
        getLoaderFromIndex(imageLoaderIndex)
            .load(this, imageUrlText, binding.imageView) { onImageLoadFailed() }
    }

    private fun getIndexOfLoader(loader: ImageLoader) = when (loader) {
        ImageLoader.GlideLoader -> 0
        ImageLoader.CoilLoader -> 1
        ImageLoader.PicassoLoader -> 2
        ImageLoader.NativeLoader -> 3
    }

    private fun getLoaderFromIndex(index: Int) = when (index) {
        0 -> ImageLoader.GlideLoader
        1 -> ImageLoader.CoilLoader
        2 -> ImageLoader.PicassoLoader
        3 -> ImageLoader.NativeLoader
        else -> throw Error("Index '${index}' can't be converted to ImageLoader")
    }

    private fun checkLoaderOption() {
        val option = when (getLoaderFromIndex(imageLoaderIndex)) {
            ImageLoader.GlideLoader -> binding.optionGlide
            ImageLoader.CoilLoader -> binding.optionCoil
            ImageLoader.PicassoLoader -> binding.optionPicasso
            ImageLoader.NativeLoader -> binding.optionNative
        }
        binding.radioGroupImageLoaders.check(option.id)
    }

    private fun onImageLoadFailed() {
        Log.w("", "Image load failed")
        Toast
            .makeText(this, getString(R.string.image_load_failed_toast), Toast.LENGTH_SHORT)
            .apply {
                setGravity(Gravity.TOP + Gravity.END, 0, 0)
            }
            .show()
    }
}