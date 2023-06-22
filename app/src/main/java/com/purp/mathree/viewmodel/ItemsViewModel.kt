package com.purp.mathree.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.purp.mathree.model.*
import java.io.BufferedReader

class ItemsViewModel(context: Context): ViewModel() {
    var itemsList = loadItemsFromJson(context)

    fun loadWeaponsFromJson(context: Context): List<Weapon> {
        val inputStream = context.assets.open("weapons.json")
        val jsonText = inputStream.bufferedReader().use(BufferedReader::readText)
        val itemType = object : TypeToken<List<Weapon>>() {}.type
        return Gson().fromJson(jsonText, itemType)
    }

    fun loadArmorsFromJson(context: Context): List<Armor> {
        val inputStream = context.assets.open("armors.json")
        val jsonText = inputStream.bufferedReader().use(BufferedReader::readText)
        val itemType = object : TypeToken<List<Armor>>() {}.type
        return Gson().fromJson(jsonText, itemType)
    }

    fun loadConsumablesFromJson(context: Context): List<Consumable> {
        val inputStream = context.assets.open("consumables.json")
        val jsonText = inputStream.bufferedReader().use(BufferedReader::readText)
        val itemType = object : TypeToken<List<Consumable>>() {}.type
        return Gson().fromJson(jsonText, itemType)
    }

    fun loadItemsFromJson(context: Context): List<Any>{
        val weapons = loadWeaponsFromJson(context)
        val armors = loadArmorsFromJson(context)
        val consumables = loadConsumablesFromJson(context)
        var itemList: MutableList<Any> = mutableListOf()

        for (weapon in weapons){
            itemList.add(weapon)
        }

        for (armor in armors){
            itemList.add(armor)
        }

        for (consumable in consumables){
            itemList.add(consumable)
        }

        return itemList
    }
}