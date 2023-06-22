package com.purp.mathree.model

data class Weapon(
    val id: Int = 0,
    val imagePath: String = "",
    val name: String = "",
    val type: String = "", // armor, consumable, weapon
    val isEquipable: Boolean = false,
    val damage: Int = 0,
    val statModifier: String = "", //dexterity, intelligence, strength
    val modifier: Double = 0.0
)

data class Armor(
    val id: Int = 0,
    val imagePath: String = "",
    val name: String = "",
    val type: String = "", // armor, consumable, weapon
    val isEquipable: Boolean = false,
    val defense: Int = 0,
    val statModifier: String = "", //dexterity, intelligence, strength
    val modifier: Double = 0.0 ,
    val slot: String = "" // head, chest, hands, legs
)

data class Consumable(
    val id: Int = 0,
    val imagePath: String = "",
    val name: String = "",
    val type: String = "", // armor, consumable, weapon
    val isEquipable: Boolean = false,
    val effect: String = "", // health, invincibility
    val value: Int = 0
)