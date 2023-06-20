package com.purp.mathree.model

class TQEncounter(
    val castleType: String, //"Dark", "Blood", "Tech"
    val imgPath: String,
    val type: String = "random", //"random", "story", "Cont"
    val id: Int,
    val text: String,
    val options: List<Option>
)

class Option(
    val text: String,
    val outcome: List<Outcome>,
    val ender: Boolean = false
)

class Outcome(
    val type: String, // "hp", "essence", "item", "open dialog", "change text"
    val value: Any
)