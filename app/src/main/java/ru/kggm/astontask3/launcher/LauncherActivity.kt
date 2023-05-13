package ru.kggm.astontask3.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kggm.astontask3.databinding.ActivityFlagsBinding
import ru.kggm.astontask3.databinding.ActivityLauncherBinding
import ru.kggm.astontask3.flags.FlagsActivity
import ru.kggm.astontask3.images.ImagesActivity

class LauncherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLauncherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickHandlers()
    }

    private fun setClickHandlers() {
        binding.buttonFlags.setOnClickListener {
            startActivity(Intent(this, FlagsActivity::class.java))
        }
        binding.buttonImages.setOnClickListener {
            startActivity(Intent(this, ImagesActivity::class.java))
        }
    }
}