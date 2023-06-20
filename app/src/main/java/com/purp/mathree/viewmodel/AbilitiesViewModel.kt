package com.purp.mathree.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.purp.mathree.model.Ability
import java.io.BufferedReader
import java.io.File

class AbilitiesViewModel: ViewModel() {
    fun loadAbilitiesFromJson(context: Context, jsonFilePath: String): List<Ability> {
        val inputStream = context.assets.open(jsonFilePath)
        val jsonText = inputStream.bufferedReader().use(BufferedReader::readText)
        val abilityType = object : TypeToken<List<Ability>>() {}.type
        return Gson().fromJson(jsonText, abilityType)
    }
}