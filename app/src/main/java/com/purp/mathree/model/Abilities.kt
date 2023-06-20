package com.purp.mathree.model

open class Ability(
    val id: Int,
    val name: String,
    val targetType: String = "Enemy",
    val damageType: String = "Magic",
    val damage: Int,
    val icon: String,
    val AOE: Boolean = false,
    val effective: List<String> = listOf(),
    val statmodifier: String = "intelligence",
    var modifier: Double = 0.25,
    var stun: Boolean = false,
    var cooldown: Int,
    var cooldownCounter: Int = 0
)

