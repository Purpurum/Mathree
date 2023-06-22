package com.purp.mathree

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.purp.mathree.R
import com.purp.mathree.viewmodel.CharacterViewModel
import android.app.Activity
import android.content.Intent
import com.purp.mathree.viewmodel.ProgressViewModel


class DeathActivity : AppCompatActivity() {
    private val characterViewModel: CharacterViewModel by lazy {
        CharacterViewModel.getInstance()
    }
    private val progressViewModel: ProgressViewModel by lazy {
        ProgressViewModel.getInstance()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.death_layout)
        val progress = progressViewModel.setProgress(this)

        findViewById<Button>(R.id.deathContinue).setOnClickListener {
            if (findViewById<Button>(R.id.deathContinue).text == "Далее" && progress.tries <= 1 ) {
                findViewById<TextView>(R.id.deathTextView).setText(R.string.deathText2)
                val newDrawable: Drawable? =
                    ContextCompat.getDrawable(this, R.drawable.bleeding_eye)
                findViewById<ImageView>(R.id.deathImageView).setImageDrawable(newDrawable)
                findViewById<Button>(R.id.deathContinue).text = "Что же это?..."

            }
            else if (findViewById<Button>(R.id.deathContinue).text == "Что же это?..." && !progress.storyDialogHubShown) {
                findViewById<TextView>(R.id.deathTextView).setText(R.string.deathText3)
                val newDrawable: Drawable? =
                    ContextCompat.getDrawable(this, R.drawable.wisdom)
                findViewById<ImageView>(R.id.deathImageView).setImageDrawable(newDrawable)
                findViewById<Button>(R.id.deathContinue).text = "Благодарю тебя"
                progress.storyDialogHubShown = true
            }
            else {
                val intent = Intent(this, HubActivity::class.java)
                startActivity(intent)

            }
        }
    }
    override fun onBackPressed() {}
}