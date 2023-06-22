package com.purp.mathree.model

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import com.purp.mathree.viewmodel.AbilitiesViewModel
import com.purp.mathree.viewmodel.ItemsViewModel
import java.time.chrono.HijrahEra
import kotlin.math.max

data class PlayerCharacter(
    val isAlive: ObservableField<Boolean> = ObservableField(true),
    var invincibilityCounter: Int = 0,
    val name: String = "",
    val characterClass: String = "",
    var _essence: ObservableField<Int> = ObservableField(0),
    val icon: String = "",
    val damageType: String = "Physical",
    val abilities: MutableList<Int> = mutableListOf(),
    var strength: Int = 0,
    var intelligence: Int = 0,
    var dexterity: Int = 0,
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
        if (invincibilityCounter == 0) {
            var newHealth = health + amount
            if (newHealth > maxhealth) {
                newHealth = maxhealth
            }
            _health.set(newHealth)

            Log.d("НОВОЕ ЗНАЧЕНИЕ ЗДОРОВЬЯ", "${_health.get()} И ${health}")
            if (newHealth <= 0) {
                isAlive.set(false)
                Log.d("СОСТОЯНИЕ", "ЖИЗНЬ - ${isAlive.get()}")
            }
        } else {
            Log.d("КРЕСТОВАЯ ЗАЩИТА", "СРАБОТАЛА")
            invincibilityCounter -= 1
        }
    }

    fun setEssence(amount: Int) {
        val newEssence = essence + amount
        _essence.set(newEssence)
        Log.d("НОВОЕ ЗНАЧЕНИЕ ЭССЕНЦИИ","${_essence} И ${essence}")
    }

    fun giveItem(itemId: Int, context: Context?){
        var context = context as Context
        var item: Any? = null
        if (itemId in 100..199){
            item = ItemsViewModel(context).loadArmorsFromJson(context).find { it.id == itemId }
        }
        if (itemId in 200..299){
            item = ItemsViewModel(context).loadWeaponsFromJson(context).find { it.id == itemId }
        }
        if (itemId in 300..399){
            item = ItemsViewModel(context).loadConsumablesFromJson(context).find { it.id == itemId }
        }
        if (item != null){
            itemsInInventory.add(item)
        } else {}

        Log.d("ДОБАВЛЯЮ ПРЕДМЕТ!", "ИД ПРЕДМЕТА: ${itemId}, СПИСОК ПРЕДМЕТОВ:${itemsInInventory}")
    }

    fun addAbility(abilityId: Int){
        abilities.add(abilityId)
    }

}
