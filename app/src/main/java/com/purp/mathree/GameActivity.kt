package com.purp.mathree

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.purp.mathree.databinding.GameInterfaceBinding
import com.purp.mathree.model.Armor
import com.purp.mathree.model.Consumable
import com.purp.mathree.model.Weapon
import com.purp.mathree.view.loadImageFromAsset
import com.purp.mathree.viewmodel.CharacterViewModel
import com.purp.mathree.viewmodel.ItemsViewModel
import com.purp.mathree.viewmodel.ProgressViewModel

class GameActivity : AppCompatActivity() {
    private lateinit var binding: GameInterfaceBinding
    private lateinit var navController: NavController
    private val characterViewModel: CharacterViewModel by lazy {
        CharacterViewModel.getInstance()
    }
    private val progressViewModel: ProgressViewModel by lazy {
        ProgressViewModel.getInstance()
    }
    var character = characterViewModel.setCharacter(this)


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
            var iv = imageViewList[index]
            if (character.itemsInInventory.getOrNull(index) != null) {

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
            } else {loadImageFromAsset(iv, "img/knapsack.png")}
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var gameProgress = progressViewModel.setProgress(this)
        character._health.set(character.maxhealth)
        character.isAlive.set(true)
        progressViewModel.saveProgressToJSON(this,gameProgress)
        characterViewModel.saveCharacterToJSON(this, character)
        gameProgress.tries += 1
        character.itemsInInventory = mutableListOf()

        fun nameItem(item: Any): Any {
            when (item) {
                is Weapon -> {
                    return item.name
                }
                is Armor -> {
                    return item.name
                }
                is Consumable -> {
                    return item.name
                }
            }
            return item
        }

        @SuppressLint("SetTextI18n", "MissingInflatedId")
        fun showTapItemDialog(context: Context, item: Any) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.popup_item_tap, null)
            builder.setView(dialogView)
            val dialog = builder.create()
            when (item) {
                is Weapon -> {
                    dialogView.findViewById<TextView>(R.id.equipText).text =
                        "Вы хотите использовать это оружие - ${item.name}?"
                    if (character.equippedWeapon != null) {
                        dialogView.findViewById<TextView>(R.id.equipItemDescription).text =
                            "Название: ${character.equippedWeapon!!.name} -> ${item.name}\n" +
                                    "Урон: ${character.equippedWeapon!!.damage} -> ${item.damage}\n" +
                                    "Ключевой параметр: ${character.equippedWeapon!!.statModifier} -> ${item.statModifier}\n" +
                                    "Модификатор параметра: ${character.equippedWeapon!!.modifier} -> ${item.modifier}\n"

                        dialogView.findViewById<Button>(R.id.equipButtonYes).setOnClickListener {
                            character.itemsInInventory.add(character.equippedWeapon!!)
                            character.equippedWeapon = item
                            character.itemsInInventory.remove(item)

                            refreshItems()
                            dialog.dismiss()
                        }

                    } else {
                        dialogView.findViewById<TextView>(R.id.equipItemDescription).text =
                            "Название: ${item.name}\n" +
                                    "Урон: ${item.damage}\n" +
                                    "Ключевой параметр: ${item.statModifier}\n" +
                                    "Модификатор параметра: ${item.modifier}\n"

                        dialogView.findViewById<Button>(R.id.equipButtonYes).setOnClickListener {
                            character.equippedWeapon = item
                            character.itemsInInventory.remove(item)
                            refreshItems()
                            dialog.dismiss()
                        }
                    }
                }
                is Armor -> {
                    if (character.equippedWeapon != null) {
                        when (item.slot) {
                            "head" -> {
                                dialogView.findViewById<TextView>(R.id.equipItemDescription).text =
                                    "Название: ${character.equippedHelmet!!.name} -> ${item.name}\n" +
                                            "Урон: ${character.equippedHelmet!!.defense} -> ${item.defense}\n" +
                                            "Ключевой параметр: ${character.equippedHelmet!!.statModifier} -> ${item.statModifier}\n" +
                                            "Модификатор параметра: ${character.equippedHelmet!!.modifier} -> ${item.modifier}\n"
                            }
                            "chest" -> {
                                dialogView.findViewById<TextView>(R.id.equipItemDescription).text =
                                    "Название: ${character.equippedChestPiece!!.name} -> ${item.name}\n" +
                                            "Урон: ${character.equippedChestPiece!!.defense} -> ${item.defense}\n" +
                                            "Ключевой параметр: ${character.equippedChestPiece!!.statModifier} -> ${item.statModifier}\n" +
                                            "Модификатор параметра: ${character.equippedChestPiece!!.modifier} -> ${item.modifier}\n"
                            }
                            "hands" -> {
                                dialogView.findViewById<TextView>(R.id.equipItemDescription).text =
                                    "Название: ${character.equippedGloves!!.name} -> ${item.name}\n" +
                                            "Урон: ${character.equippedGloves!!.defense} -> ${item.defense}\n" +
                                            "Ключевой параметр: ${character.equippedGloves!!.statModifier} -> ${item.statModifier}\n" +
                                            "Модификатор параметра: ${character.equippedGloves!!.modifier} -> ${item.modifier}\n"
                            }
                            "legs" -> {
                                dialogView.findViewById<TextView>(R.id.equipItemDescription).text =
                                    "Название: ${character.equippedBoots!!.name} -> ${item.name}\n" +
                                            "Урон: ${character.equippedBoots!!.defense} -> ${item.defense}\n" +
                                            "Ключевой параметр: ${character.equippedBoots!!.statModifier} -> ${item.statModifier}\n" +
                                            "Модификатор параметра: ${character.equippedBoots!!.modifier} -> ${item.modifier}\n"
                            }
                        }
                        dialogView.findViewById<Button>(R.id.equipButtonYes).setOnClickListener {
                            when (item.slot) {
                                "head" -> {
                                    character.itemsInInventory.add(character.equippedHelmet!!)
                                    character.equippedHelmet = item
                                }
                                "chest" -> {
                                    character.itemsInInventory.add(character.equippedChestPiece!!)
                                    character.equippedChestPiece = item
                                }
                                "hands" -> {
                                    character.itemsInInventory.add(character.equippedGloves!!)
                                    character.equippedGloves = item
                                }
                                "legs" -> {
                                    character.itemsInInventory.add(character.equippedBoots!!)
                                    character.equippedBoots = item
                                }
                            }
                            character.itemsInInventory.remove(item)
                            refreshItems()
                            dialog.dismiss()
                        }
                    } else {
                        dialogView.findViewById<TextView>(R.id.equipItemDescription).text =
                            "Название: ${item.name}\n" +
                                    "Урон: ${item.defense}\n" +
                                    "Ключевой параметр: ${item.statModifier}\n" +
                                    "Модификатор параметра: ${item.modifier}\n"

                        dialogView.findViewById<Button>(R.id.equipButtonYes).setOnClickListener {
                            when (item.slot) {
                                "head" -> {
                                    character.itemsInInventory.add(character.equippedHelmet!!)
                                    character.equippedHelmet = item
                                }
                                "chest" -> {
                                    character.itemsInInventory.add(character.equippedChestPiece!!)
                                    character.equippedChestPiece = item
                                }
                                "hands" -> {
                                    character.itemsInInventory.add(character.equippedGloves!!)
                                    character.equippedGloves = item
                                }
                                "legs" -> {
                                    character.itemsInInventory.add(character.equippedBoots!!)
                                    character.equippedBoots = item
                                }
                            }
                            character.itemsInInventory.remove(item)
                            refreshItems()
                            dialog.dismiss()
                        }
                    }
                }
                is Consumable -> {
                    dialogView.findViewById<TextView>(R.id.equipText).text =
                        "Вы уверены, что хотите использовать ${item.name}?"
                    if (item.effect == "health") {
                        dialogView.findViewById<TextView>(R.id.equipItemDescription).text =
                            "Вам будет восстановлено ${item.value} здоровья."

                        dialogView.findViewById<Button>(R.id.equipButtonYes).setOnClickListener {
                            character.setHealth(item.value)
                            character.itemsInInventory.remove(item)
                            refreshItems()
                            dialog.dismiss()
                        }
                    } else if (item.effect == "invincibility") {
                        dialogView.findViewById<TextView>(R.id.equipItemDescription).text =
                            "Вы не будете получать урона в течении ${item.value} ходов."

                        dialogView.findViewById<Button>(R.id.equipButtonYes).setOnClickListener {
                            character.invincibilityCounter = item.value
                            character.itemsInInventory.remove(item)
                            refreshItems()
                            dialog.dismiss()
                        }
                    } else {
                        dialogView.findViewById<TextView>(R.id.equipItemDescription).text =
                            "Вам неизвестно, что сделает этот предмет."

                        dialogView.findViewById<Button>(R.id.equipButtonYes).setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                }
            }
            dialogView.findViewById<Button>(R.id.equipButtonNo).setOnClickListener {
                dialog.dismiss()
            }
            dialogView.findViewById<Button>(R.id.equipButtonThrowAway).setOnClickListener {
                character.itemsInInventory.remove(item)
                refreshItems()
                dialog.dismiss()
            }
            dialog.show()
        }

        @SuppressLint("MissingInflatedId")
        fun showTooMuchItemsDialog(context: Context, itemName: String) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.popup_too_mich_items, null)

            val dialogText = dialogView.findViewById<TextView>(R.id.dialogText)
            dialogText.text =
                "Вы понимаете, что у вас больше нет места в инвентаре! Что вы хотите выбросить?" +
                        " Или, быть может, вам вовсе и не нужен этот новый предмет - ${itemName}?"

            builder.setView(dialogView)

            val dialog = builder.create()
            dialog.setCancelable(false)
            val btnList: List<Button> = listOf(
                dialogView.findViewById(R.id.popItem1),
                dialogView.findViewById(R.id.popItem2),
                dialogView.findViewById(R.id.popItem3),
                dialogView.findViewById(R.id.popItem4),
                dialogView.findViewById(R.id.popItem5),
                dialogView.findViewById(R.id.popItem6),
                dialogView.findViewById(R.id.popItem7)
            )
            for (index in listOf<Int>(0, 1, 2, 3, 4, 5, 6)) {
                var item = character.itemsInInventory[index]
                if (index in (0..5)) {
                    btnList[index].text = "Выбросить ${nameItem(item)}"
                } else{
                    btnList[index].text = "Выбросить новый ${nameItem(item)}"
                }
                btnList[index].setOnClickListener {

                    when (item) {
                        is Weapon -> {
                            character.itemsInInventory.removeAt(index)
                            refreshItems()
                            dialog.dismiss()
                        }
                        is Armor -> {
                            character.itemsInInventory.removeAt(index)
                            refreshItems()
                            dialog.dismiss()
                        }
                        is Consumable -> {
                            character.itemsInInventory.removeAt(index)
                            refreshItems()
                            dialog.dismiss()
                        }
                    }
                }
            }

            dialog.show()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.game_interface)
        binding.lifecycleOwner = this
        binding.characterViewModel = characterViewModel
        var itemsViewModel = ItemsViewModel(this)
        character.setEssence(1000)

        Log.d("asdasd", "$character")
        refreshItems()

        findViewById<Button>(R.id.essenceHeal).setOnClickListener {
            var neededHealth: Int = character.maxhealth.minus(character._health.get()!!)
            var neededEssence: Int = character.maxhealth.minus(character._health.get()!!)
            if (neededEssence > character._essence.get()!!) {
                character.setHealth(character._essence.get()!!)
                character.setEssence(-1 * character._essence.get()!!)
            } else {
                character.setHealth(neededHealth)
                character.setEssence(-1 * neededEssence)
            }
        }

        findViewById<Button>(R.id.charData).setOnClickListener {
            Log.d("abilities", "${character.abilities}")
            Log.d("equippedHelmet", "${character.equippedHelmet}")
            Log.d("equippedChestPiece", "${character.equippedChestPiece}")
            Log.d("equippedGloves", "${character.equippedGloves}")
            Log.d("equippedBoots", "${character.equippedBoots}")
            Log.d("equippedWeapon", "${character.equippedWeapon}")
            Log.d("itemsInInventory", "${character.itemsInInventory}")

        }

        findViewById<ImageView>(R.id.inventoryItem1).setOnClickListener {
            val item = character.itemsInInventory[0]
            when (item) {
                is Weapon -> {
                    showTapItemDialog(this, item)
                }
                is Armor -> {
                    showTapItemDialog(this, item)
                }
                is Consumable -> {
                    showTapItemDialog(this, item)
                }
            }
        }
        findViewById<ImageView>(R.id.inventoryItem2).setOnClickListener {
            val item = character.itemsInInventory[1]
            when (item) {
                is Weapon -> {
                    showTapItemDialog(this, item)
                }
                is Armor -> {
                    showTapItemDialog(this, item)
                }
                is Consumable -> {
                    showTapItemDialog(this, item)
                }
            }
        }
        findViewById<ImageView>(R.id.inventoryItem3).setOnClickListener {
            val item = character.itemsInInventory[2]
            when (item) {
                is Weapon -> {
                    showTapItemDialog(this, item)
                }
                is Armor -> {
                    showTapItemDialog(this, item)
                }
                is Consumable -> {
                    showTapItemDialog(this, item)
                }
            }
        }
        findViewById<ImageView>(R.id.inventoryItem4).setOnClickListener {
            val item = character.itemsInInventory[3]
            when (item) {
                is Weapon -> {
                    showTapItemDialog(this, item)
                }
                is Armor -> {
                    showTapItemDialog(this, item)
                }
                is Consumable -> {
                    showTapItemDialog(this, item)
                }
            }
        }
        findViewById<ImageView>(R.id.inventoryItem5).setOnClickListener {
            val item = character.itemsInInventory[4]
            when (item) {
                is Weapon -> {
                    showTapItemDialog(this, item)
                }
                is Armor -> {
                    showTapItemDialog(this, item)
                }
                is Consumable -> {
                    showTapItemDialog(this, item)
                }
            }
        }
        findViewById<ImageView>(R.id.inventoryItem6).setOnClickListener {
            val item = character.itemsInInventory[5]
            when (item) {
                is Weapon -> {
                    showTapItemDialog(this, item)
                }
                is Armor -> {
                    showTapItemDialog(this, item)
                }
                is Consumable -> {
                    showTapItemDialog(this, item)
                }
            }
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("РЕФРЕШИЦА","РЕФРЕШИЦА")
            refreshItems()
            if (character.itemsInInventory.size > 6){
                showTooMuchItemsDialog(this, nameItem(character.itemsInInventory[6]).toString())
            }
        }
        character.isAlive.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val isAlive = character.isAlive.get()
                if (isAlive != null && !isAlive) {
                    character._health.set(0)
                    playDeath()
                }
            }
        })
    }
    private fun playDeath() {
        character._health.set(0)
        Thread.sleep(1000)
        val intent = Intent(this, DeathActivity::class.java)
        startActivity(intent)
        finish()

    }
    override fun onBackPressed() {}
}