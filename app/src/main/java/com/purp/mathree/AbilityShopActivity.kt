package com.purp.mathree

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.purp.mathree.view.loadImageFromAsset
import com.purp.mathree.viewmodel.CharacterViewModel

class AbilityShopActivity : AppCompatActivity() {
    private val characterViewModel: CharacterViewModel by lazy {
        CharacterViewModel.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ability_shop)
        var character = characterViewModel.setCharacter(this)
         loadImageFromAsset(findViewById(R.id.dexA1), "img/arrow-flights.png")
        loadImageFromAsset(findViewById(R.id.dexA2), "img/arrow-scope.png")
        loadImageFromAsset(findViewById(R.id.dexA3), "img/dodging.png")
        loadImageFromAsset(findViewById(R.id.intA1), "img/snowflake-1.png")
        loadImageFromAsset(findViewById(R.id.intA2), "img/thorn.png")
        loadImageFromAsset(findViewById(R.id.intA3), "img/brain-freeze.png")
        loadImageFromAsset(findViewById(R.id.strA1), "img/hammer-break.png")
        loadImageFromAsset(findViewById(R.id.strA2), "img/axe-swing.png")
        loadImageFromAsset(findViewById(R.id.strA3), "img/checked-shield.png")

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

        fun buyAbility(abilityId: Int){
            if (character._essence.get()!! >= 1000) {
                character.setEssence(-1000)
                character.addAbility(abilityId)
            } else {notEnoughEssenceDialog(this)}
        }

        fun buyDialog(context: Context, abilityId: Int, desc: String = ""){
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.popup_buy_ability, null)
            builder.setView(dialogView)
            val dialog = builder.create()
            dialogView.findViewById<TextView>(R.id.buyAbilityDesc).text = desc
            dialogView.findViewById<Button>(R.id.buyAbilityYes).setOnClickListener {
                buyAbility(abilityId)
                dialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.buyAbilityNo).setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        findViewById<ImageView>(R.id.dexA1).setOnClickListener {
            buyDialog(this,1)
        }
        findViewById<ImageView>(R.id.dexA2).setOnClickListener {
            buyDialog(this,2)
        }
        findViewById<ImageView>(R.id.dexA3).setOnClickListener {
            buyDialog(this,3)
        }
        findViewById<ImageView>(R.id.intA1).setOnClickListener {
            buyDialog(this,6, "Заморозьте одного противника")
        }
        findViewById<ImageView>(R.id.intA2).setOnClickListener {
            buyDialog(this,7, "Поразите всех ваших врагов шипастой лозой")
        }
        findViewById<ImageView>(R.id.intA3).setOnClickListener {
            buyDialog(this,8)
        }
        findViewById<ImageView>(R.id.strA1).setOnClickListener {
            buyDialog(this,9)
        }
        findViewById<ImageView>(R.id.strA2).setOnClickListener {
            buyDialog(this,10)
        }
        findViewById<ImageView>(R.id.strA3).setOnClickListener {
            buyDialog(this,11)
        }










        findViewById<ImageView>(R.id.abilityShopBack).setOnClickListener {
            val intent = Intent(this, HubActivity::class.java)
            startActivity(intent)
        }
    }
}