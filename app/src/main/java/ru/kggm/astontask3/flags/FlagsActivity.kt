package ru.kggm.astontask3.flags

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import ru.kggm.astontask3.databinding.ActivityFlagsBinding

class FlagsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFlagsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlagsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}