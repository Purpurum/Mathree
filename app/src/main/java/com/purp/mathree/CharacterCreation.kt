package com.purp.mathree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.purp.mathree.view.loadImageFromAsset
import com.purp.mathree.viewmodel.CharacterViewModel

class CharacterCreationActivity : AppCompatActivity() {
    private val characterViewModel: CharacterViewModel by lazy {
        CharacterViewModel.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_creation)

        val nameET: EditText = findViewById(R.id.nameET)
        val createBtn: Button = findViewById(R.id.createBtn)
        val warriorBtn: Button = findViewById(R.id.warriorBtn)
        val rogueBtn: Button = findViewById(R.id.rogueBtn)
        val wizardBtn: Button = findViewById(R.id.wizardBtn)
        var characterClass: String = ""
        val data: MutableList<Any> = mutableListOf()

        loadImageFromAsset(findViewById(R.id.imageView2),"img/wizard.png")
        loadImageFromAsset(findViewById(R.id.imageView3),"img/warrior.png")
        loadImageFromAsset(findViewById(R.id.imageView4),"img/rogue.png")

        rogueBtn.setOnClickListener {
            characterClass = getString(R.string.rogue)
            findViewById<TextView>(R.id.textView4).setText(R.string.rogueDesc)
        }
        warriorBtn.setOnClickListener {
            characterClass = getString(R.string.warrior)
            findViewById<TextView>(R.id.textView4).setText(R.string.warriorDesc)
        }
        wizardBtn.setOnClickListener {
            characterClass = getString(R.string.wizard)
            findViewById<TextView>(R.id.textView4).setText(R.string.wizardDesc)
        }
        createBtn.setOnClickListener {
            if (characterClass == getString(R.string.rogue)) {
                data.add(nameET.text.toString())
                data.add(characterClass)
                data.add(mutableListOf<Int>())
                data.add(5)
                data.add(5)
                data.add(10)
                data.add("img/rogue.png")
                characterViewModel.saveCreatedCharacterToJSON(this, data)
            }
            if (characterClass == getString(R.string.warrior)) {
                data.add(nameET.text.toString())
                data.add(characterClass)
                data.add(mutableListOf<Int>())
                data.add(10)
                data.add(5)
                data.add(5)
                data.add("img/warrior.png")
                characterViewModel.saveCreatedCharacterToJSON(this, data)
            }
            if (characterClass == getString(R.string.wizard)) {
                data.add(nameET.text.toString())
                data.add(characterClass)
                data.add(mutableListOf<Int>(5))
                data.add(5)
                data.add(10)
                data.add(5)
                data.add("img/wizard.png")
                characterViewModel.saveCreatedCharacterToJSON(this, data)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {}
}