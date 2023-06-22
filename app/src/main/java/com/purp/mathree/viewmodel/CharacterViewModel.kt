package com.purp.mathree.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.purp.mathree.model.PlayerCharacter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class CharacterViewModel() : ViewModel() {

    lateinit var character: PlayerCharacter

    companion object {
        private var characterInstance: CharacterViewModel? = null

        fun getInstance(): CharacterViewModel {
            if (characterInstance == null) {
                Log.d("ПОЛУЧЕНИЕ ПЕРСОНАЖА","НАЧАЛО ПОЛУЧЕНИЯ ПЕРСОНАЖА!")
                characterInstance = CharacterViewModel()
                Log.d("ПОЛУЧЕНИЕ ПЕРСОНАЖА","ПЕРСОНАЖ ПОЛУЧЕН!")
            }
            return characterInstance as CharacterViewModel
        }
    }

    fun setCharacter(context: Context?): PlayerCharacter {
        if (!::character.isInitialized) {
            Log.d("ПЕРСОНАЖ ИНИЦИАЛИЗИРУЕТСЯ","НАЧАЛО ИНИЦИАЛИЗАЦИИ!")
            character = loadCharacterFromJSON(context)
            Log.d("ПЕРСОНАЖ ИНИЦИАЛИЗИРУЕТСЯ","ИНИЦИАЛИЗИРОВАН!")
        }
        return character
    }

    fun saveCreatedCharacterToJSON(context: Context, data: MutableList<Any>) {
        var character: PlayerCharacter = PlayerCharacter(
            name = data[0].toString(),
            characterClass = data[1].toString(),
            abilities = data[2] as MutableList<Int>,
            strength = data[3] as Int,
            intelligence = data[4] as Int,
            dexterity = data[5] as Int,
            icon = data[6] as String
            )
        val gson = Gson()
        val json = gson.toJson(character)
        val fileName = "Character.json"

        val file = File(context.filesDir, fileName)
        try {
            val fileWriter = FileWriter(file)
            fileWriter.write(json)
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun saveCharacterToJSON(context: Context, character: PlayerCharacter) {
        val gson = Gson()
        val json = gson.toJson(character)
        val fileName = "Character.json"

        val file = File(context.filesDir, fileName)
        try {
            val fileWriter = FileWriter(file)
            fileWriter.write(json)
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadCharacterFromJSON(context: Context?): PlayerCharacter {
        val fileName = "Character.json"
        val file = File(context?.filesDir, fileName)
        val fileReader = FileReader(file)
        val gson = Gson()
        val playerCharacter = gson.fromJson(fileReader, PlayerCharacter::class.java)
        fileReader.close()
        return playerCharacter
    }

    fun getDataForMenu(playerCharacter: PlayerCharacter): MutableList<Any> {
        var data : MutableList<Any> = mutableListOf()
        data.add(playerCharacter.name)
        data.add(playerCharacter.characterClass)
        data.add(playerCharacter.level)
        data.add(playerCharacter.strength)
        data.add(playerCharacter.dexterity)
        data.add(playerCharacter.intelligence)
        data.add(playerCharacter.icon)
        return data
    }
}
