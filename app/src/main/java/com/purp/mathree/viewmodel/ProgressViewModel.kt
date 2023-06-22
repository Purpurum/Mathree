package com.purp.mathree.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.purp.mathree.model.PlayerCharacter
import com.purp.mathree.model.Progress
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files.exists

class ProgressViewModel: ViewModel() {
    lateinit var gameProgress: Progress

    companion object {
        private var gameProgressInstance: ProgressViewModel? = null

        fun getInstance(): ProgressViewModel {
            if (gameProgressInstance == null) {
                Log.d("ПОЛУЧЕНИЕ ПЕРСОНАЖА","НАЧАЛО ПОЛУЧЕНИЯ ПЕРСОНАЖА!")
                gameProgressInstance = ProgressViewModel()
                Log.d("ПОЛУЧЕНИЕ ПЕРСОНАЖА","ПЕРСОНАЖ ПОЛУЧЕН!")
            }
            return gameProgressInstance as ProgressViewModel
        }
    }

    fun createProgressFile(context: Context){
        if (!File(context?.filesDir, "Progress.json").exists()) {
            val progress = Progress()
            saveProgressToJSON(context, progress)
        }
    }

    fun setProgress(context: Context?): Progress {
        if (!::gameProgress.isInitialized) {
            Log.d("ПЕРСОНАЖ ИНИЦИАЛИЗИРУЕТСЯ","НАЧАЛО ИНИЦИАЛИЗАЦИИ!")
            gameProgress = loadProgressFromJSON(context)
            Log.d("ПЕРСОНАЖ ИНИЦИАЛИЗИРУЕТСЯ","ИНИЦИАЛИЗИРОВАН!")
        }
        return gameProgress
    }

    fun saveProgressToJSON(context: Context?, gameProgress: Progress) {
        val gson = Gson()
        val json = gson.toJson(gameProgress)
        val fileName = "Progress.json"

        val file = File(context?.filesDir, fileName)
        try {
            val fileWriter = FileWriter(file)
            fileWriter.write(json)
            fileWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadProgressFromJSON(context: Context?): Progress {
        val fileName = "Progress.json"
        val file = File(context?.filesDir, fileName)
        val fileReader = FileReader(file)
        val gson = Gson()
        val gameProgress = gson.fromJson(fileReader, Progress::class.java)
        fileReader.close()
        return gameProgress
    }
}