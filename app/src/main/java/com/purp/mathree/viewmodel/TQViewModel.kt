package com.purp.mathree.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.purp.mathree.model.TQEncounter


class TQViewModel : ViewModel() {
    private var dialogues: MutableLiveData<List<TQEncounter>> = MutableLiveData()

    fun loadDialoguesFromJson(context: Context?) {
        val json = context?.assets?.open("TQData.json")?.bufferedReader().use { it?.readText() }
        val dialoguesList = Gson().fromJson<List<TQEncounter>>(json, object : TypeToken<List<TQEncounter>>() {}.type)
        dialogues.value = dialoguesList
    }

    fun getDialogues(): LiveData<List<TQEncounter>> {
        return dialogues
    }
}