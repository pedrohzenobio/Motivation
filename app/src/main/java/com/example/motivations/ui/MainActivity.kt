package com.example.motivations.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.motivations.infra.MotivationConstants
import com.example.motivations.R
import com.example.motivations.data.Mock
import com.example.motivations.infra.SecurityPreferences
import com.example.motivations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var categoryID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleUserName()
        handlePhrase()
        handleFilter(R.id.image_button_loop)

        binding.buttonNovaFrase.setOnClickListener(this)
        binding.imageButtonEmoji.setOnClickListener(this)
        binding.imageButtonLoop.setOnClickListener(this)
        binding.imageButtonSun.setOnClickListener(this)
        binding.textOla.setOnClickListener(this)
        binding.textClearName.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_nova_frase -> {
                handlePhrase()
            }
            in listOf(
                R.id.image_button_emoji,
                R.id.image_button_loop,
                R.id.image_button_sun
            ) -> {
                handleFilter(v.id)
            }
            R.id.text_ola -> {
                startActivity(Intent(this,UserActivity::class.java))
            }
            R.id.text_clear_name -> {
                startActivity(Intent(this,UserActivity::class.java))
                clearName()
            }
        }
    }

    private fun handlePhrase(){
        binding.textMensage.text = Mock().getPhrase(categoryID)
    }

    private fun handleUserName() {
        val name = SecurityPreferences(this).getString(MotivationConstants.KEY.USER_NAME1)
        binding.textOla.text = "Ola, $name!"
    }

    private fun handleFilter(id: Int) {

        binding.imageButtonLoop.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.imageButtonSun.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.imageButtonEmoji.setColorFilter(ContextCompat.getColor(this, R.color.black))

        when (id) {
            R.id.image_button_loop -> {
                binding.imageButtonLoop.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryID = MotivationConstants.FILTER.LOOP
            }

            R.id.image_button_sun -> {
                binding.imageButtonSun.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryID = MotivationConstants.FILTER.SUN
            }

            R.id.image_button_emoji -> {
                binding.imageButtonEmoji.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryID = MotivationConstants.FILTER.EMOJI
            }
        }
    }

    private fun clearName(){
        val name = ""
        SecurityPreferences(this).storeString(MotivationConstants.KEY.USER_NAME1,name)
        finish()
    }
}