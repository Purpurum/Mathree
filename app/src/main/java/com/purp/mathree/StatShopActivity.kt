package com.purp.mathree

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.purp.mathree.viewmodel.CharacterViewModel

class StatShopActivity : AppCompatActivity() {
    private val characterViewModel: CharacterViewModel by lazy {
        CharacterViewModel.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat_shop)
        var character = characterViewModel.setCharacter(this)

        fun notEnoughEssenceDialog(context: Context){
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.popup_essence_shortage, null)
            builder.setView(dialogView)
            val dialog = builder.create()
            dialogView.findViewById<Button>(R.id.essenceShortageButton).setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        findViewById<TextView>(R.id.strNumTV).text = character.strength.toString()
        findViewById<TextView>(R.id.intNumTV).text = character.intelligence.toString()
        findViewById<TextView>(R.id.dexNumTV).text = character.dexterity.toString()

        findViewById<ImageView>(R.id.strengthShop).setOnClickListener {
            if (character._essence.get()!! >= 500){
                character.strength += 1
                character.setEssence(-500)
                findViewById<TextView>(R.id.strNumTV).text = character.strength.toString()
            } else {notEnoughEssenceDialog(this)}
        }
        findViewById<ImageView>(R.id.intelligenceShop).setOnClickListener {
            if (character._essence.get()!! >= 500){
                character.intelligence += 1
                character.setEssence(-500)
                findViewById<TextView>(R.id.intNumTV).text = character.intelligence.toString()
            } else {notEnoughEssenceDialog(this)}
        }
        findViewById<ImageView>(R.id.dexterityShop).setOnClickListener {
            if (character._essence.get()!! >= 500){
                character.dexterity += 1
                character.setEssence(-500)
                findViewById<TextView>(R.id.dexNumTV).text = character.dexterity.toString()
            } else {notEnoughEssenceDialog(this)}
        }
        findViewById<ImageView>(R.id.statShopBack).setOnClickListener {
            val intent = Intent(this, HubActivity::class.java)
            startActivity(intent)
        }
    }
}