package com.purp.mathree.model

import androidx.databinding.ObservableField

data class EntityGroup(
    val castleType: String,
    val difficulty: String,
    val monsters: List<Entity>
)

data class Entity(
    var isAlive: Boolean = true,
    var isStunned: Boolean = false,
    val id: Int = 0,
    val name: String = "",
    val icon: String = "",
    val maxhealth: Int = 0,
    var _health: ObservableField<Int> = ObservableField(maxhealth),
    val mana: Int = 0,
    val castleType: String = "",
    val enemyType: String = "",
    val resistances: List<List<Float>> = emptyList(),
    val damageType: String = "",
    val damage: Int = 0,
    val abilities: List<Int> = emptyList(),
    val essenceDrop: Int = 0
)  {

    val health: ObservableField<Int>
        get() = _health

    fun decreaseHealth(amount: Int) {
        val newHealth = (_health.get() ?: 0) - amount
        _health.set(newHealth)

        if (newHealth <= 0) {
            isAlive = false
        }
    }
}