package ru.kggm.astontask3.images

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kggm.astontask3.R
import ru.kggm.astontask3.databinding.ActivityFlagsBinding
import ru.kggm.astontask3.databinding.ActivityImagesBinding

class ImagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImagesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}