package com.purp.mathree

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.purp.mathree.viewmodel.CharacterViewModel

class HubActivity : AppCompatActivity() {
    private val characterViewModel: CharacterViewModel by lazy {
        CharacterViewModel.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hub_layout)
        var character = characterViewModel.setCharacter(this)

        findViewById<ImageView>(R.id.hubAbilityShop).setOnClickListener{
            val intent = Intent(this, AbilityShopActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.hubProgress).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.hubEnterTheDungeon).setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.hubStatShop).setOnClickListener{
            val intent = Intent(this, StatShopActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onBackPressed() {}
}