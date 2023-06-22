package com.purp.mathree

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.purp.mathree.viewmodel.CharacterViewModel
import com.purp.mathree.viewmodel.ProgressViewModel
import java.io.File

class MainActivity : AppCompatActivity() {
    private val characterViewModel: CharacterViewModel by lazy {
        CharacterViewModel.getInstance()
    }
    private val progressViewModel: ProgressViewModel by lazy {
        ProgressViewModel.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        fun showSettingsPopup(context:Context){
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.popup_settings, null)
            builder.setView(dialogView)
            val dialog = builder.create()
            var counter: Int = 0
            dialogView.findViewById<Button>(R.id.deleteData).setOnClickListener {
                if(counter < 6){
                    counter += 1
                    Toast.makeText(context,"Чтобы удалить данные, нажмите на кнопку удаления ещё ${7-counter} раз.", Toast.LENGTH_SHORT).show()
                } else {
                    var fileName = "Character.json"
                    var file = File(this.filesDir, fileName)
                    file.delete()
                    fileName = "Progress.json"
                    file = File(this.filesDir, fileName)
                    file.delete()
                    recreate()
                }
            }
            dialog.show()
        }
        findViewById<ImageView>(R.id.settingsIV).setOnClickListener {
            showSettingsPopup(this)
        }
        progressViewModel.createProgressFile(this)
        val progress = progressViewModel.setProgress(this)
        val fileName = "Character.json"
        val file = File(this.filesDir, fileName)
        if (file.exists()) {
            characterViewModel.setCharacter(this)
            findViewById<TextView>(R.id.intLabel2).text = "Попыток: ${progress.tries}"
            findViewById<TextView>(R.id.intel2).text = "Убийств: ${progress.monstersSlayed}"
            val fillerData = characterViewModel.getDataForMenu(characterViewModel.character)
            findViewById<TextView>(R.id.name).text = fillerData[0].toString()
            findViewById<TextView>(R.id.characterClass).text = fillerData[1].toString()
            findViewById<TextView>(R.id.lvl).text = fillerData[2].toString()
            findViewById<TextView>(R.id.str).text = fillerData[3].toString()
            findViewById<TextView>(R.id.strLabel).text = "Сила"
            findViewById<TextView>(R.id.dexLabel).text = "Ловкость"
            findViewById<TextView>(R.id.intLabel).text = "Интеллект"
            findViewById<TextView>(R.id.dex).text = fillerData[4].toString()
            findViewById<TextView>(R.id.intel).text = fillerData[5].toString()
            val drawable: Drawable? = Drawable.createFromStream(this.assets.open(fillerData[6].toString()), null)
            Glide.with(this)
                .load(drawable)
                .override(300, 300)
                .into(findViewById<ImageView>(R.id.imageView))

        }

        findViewById<Button>(R.id.startBtn).setOnClickListener {
            if (file.exists()) {
                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, CharacterCreationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}