package com.purp.mathree.model

import android.util.Log
import androidx.databinding.ObservableField
import java.time.chrono.HijrahEra

data class PlayerCharacter(
    var isAlive: Boolean = true,
    val name: String = "",
    val characterClass: String = "",
    var _essence: ObservableField<Int> = ObservableField(0),
    val icon: String = "",
    val damageType: String = "Physical",
    val abilities: List<Int> = emptyList(),
    val strength: Int = 0,
    val intelligence: Int = 0,
    val dexterity: Int = 0,
    val level: Int = 21-(strength+intelligence+dexterity),
    var physicalResistance: Float = (strength/100).toFloat(),
    var magicalResistance: Float = (strength/100).toFloat(),
    val maxhealth: Int = (100*(strength*0.1)).toInt(),
    var _health: ObservableField<Int> = ObservableField(maxhealth),
    var equippedHelmet: Armor? = null,
    var equippedChestPiece: Armor? = null,
    var equippedGloves: Armor? = null,
    var equippedBoots: Armor? = null,
    var equippedWeapon: Weapon? = null,
    var itemsInInventory: MutableList<Any> = mutableListOf()
)  {

    val health: Int
        get() = _health.get() ?: 0

    val essence: Int
        get() = _essence.get() ?: 0

    fun setHealth(amount: Int) {
        val newHealth = health + amount
        _health.set(newHealth)

        Log.d("НОВОЕ ЗНАЧЕНИЕ ЗДОРОВЬЯ","${_health.get()} И ${health}")
        if (newHealth <= 0) {
            isAlive = false
            Log.d("СОСТОЯНИЕ", "ЖИЗНЬ - ${isAlive}")
        }
    }

    fun setEssence(amount: Int) {
        val newEssence = essence + amount
        _essence.set(newEssence)
        Log.d("НОВОЕ ЗНАЧЕНИЕ ЭССЕНЦИИ","${_essence} И ${essence}")
    }

    /*fun giveItem(itemId: Int){
        if (itemsInInventory != null) {
            itemsInInventory.add(itemId)
        }
        else {
            itemsInInventory = mutableListOf()
            itemsInInventory.add(itemId)
        }
        Log.d("ДОБАВЛЯЮ ПРЕДМЕТ!", "ИД ПРЕДМЕТА: ${itemId}, СПИСОК ПРЕДМЕТОВ:${itemsInInventory}")
    }*/

}
