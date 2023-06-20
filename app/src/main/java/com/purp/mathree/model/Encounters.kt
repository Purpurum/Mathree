package com.purp.mathree.model

import com.google.gson.Gson

data class Encounter(
    val name: String,
    val image: String,
    val description: String,
    val actions: List<Action>
) {
    data class Action(
        val name: String,
        val description: String,
        val nextActions: List<Action> = emptyList()
    )

    companion object {
        fun fromJson(json: String): Encounter {
            return Gson().fromJson(json, Encounter::class.java)
        }
    }
}