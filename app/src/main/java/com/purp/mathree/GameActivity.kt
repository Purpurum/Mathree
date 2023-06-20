package com.purp.mathree

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.purp.mathree.databinding.GameInterfaceBinding
import com.purp.mathree.model.Armor
import com.purp.mathree.model.Consumable
import com.purp.mathree.model.PlayerCharacter
import com.purp.mathree.model.Weapon
import com.purp.mathree.view.loadImageFromAsset
import com.purp.mathree.viewmodel.CharacterViewModel
import com.purp.mathree.viewmodel.ItemsViewModel

class GameActivity : AppCompatActivity() {
    private lateinit var binding: GameInterfaceBinding
    private val characterViewModel: CharacterViewModel by lazy {
        CharacterViewModel.getInstance()
    }
    var character = characterViewModel.setCharacter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun refreshItems() {
            val itemSlot1: ImageView = findViewById(R.id.inventoryItem1)
            val itemSlot2: ImageView = findViewById(R.id.inventoryItem2)
            val itemSlot3: ImageView = findViewById(R.id.inventoryItem3)
            val itemSlot4: ImageView = findViewById(R.id.inventoryItem4)
            val itemSlot5: ImageView = findViewById(R.id.inventoryItem5)
            val itemSlot6: ImageView = findViewById(R.id.inventoryItem6)
            val imageViewList: List<ImageView> = listOf(
                itemSlot1, itemSlot2, itemSlot3,
                itemSlot4, itemSlot5, itemSlot6
            )

            for (index in listOf<Int>(0, 1, 2, 3, 4, 5)) {
                if (character.itemsInInventory.getOrNull(index) != null) {
                    var iv = imageViewList[index]
                    var item = character.itemsInInventory[index]
                    when (item) {
                        is Weapon -> {
                            loadImageFromAsset(iv, item.imagePath)
                        }
                        is Armor -> {
                            loadImageFromAsset(iv, item.imagePath)
                        }
                        is Consumable -> {
                            loadImageFromAsset(iv, item.imagePath)
                        }
                    }
                }
            }
        }

        binding = DataBindingUtil.setContentView(this, R.layout.game_interface)
        binding.lifecycleOwner = this
        binding.characterViewModel = characterViewModel
        var itemsViewModel = ItemsViewModel(this)
        character.itemsInInventory.add(itemsViewModel.itemsList[0])
        character.itemsInInventory.add(itemsViewModel.itemsList[itemsViewModel.itemsList.size-1])
        Log.d("asdasd","$character")
        refreshItems()

        findViewById<Button>(R.id.essenceHeal).setOnClickListener {
            var neededHealth: Int = character.maxhealth.minus(character._health.get()!!)
            var neededEssence: Int = character.maxhealth.minus(character._health.get()!!)
            if (neededEssence > character._essence.get()!!){
                character.setHealth(character._essence.get()!!)
                character.setEssence(-1*character._essence.get()!!)
            }
            else {
                character.setHealth(neededHealth)
                character.setEssence(-1*neededEssence)
            }
        }
        findViewById<Button>(R.id.charData).setOnClickListener {
            Log.d("isAlive", "${character.isAlive}")
            Log.d("name", "${character.name}")
            Log.d("characterClass", "${character.characterClass}")
            Log.d("_essence", "${character._essence}")
            Log.d("icon", "${character.icon}")
            Log.d("damageType", "${character.damageType}")
            Log.d("abilities", "${character.abilities}")
            Log.d("strength", "${character.strength}")
            Log.d("intelligence", "${character.intelligence}")
            Log.d("dexterity", "${character.dexterity}")
            Log.d("level", "${character.level}")
            Log.d("physicalResistance", "${character.physicalResistance}")
            Log.d("magicalResistance", "${character.magicalResistance}")
            Log.d("maxhealth", "${character.maxhealth}")
            Log.d("_health", "${character._health}")
            Log.d("equippedHelmet", "${character.equippedHelmet}")
            Log.d("equippedChestPiece", "${character.equippedChestPiece}")
            Log.d("equippedGloves", "${character.equippedGloves}")
            Log.d("equippedBoots", "${character.equippedBoots}")
            Log.d("equippedWeapon", "${character.equippedWeapon}")
            Log.d("itemsInInventory", "${character.itemsInInventory}")

        }

    }
    override fun onBackPressed() {}
}