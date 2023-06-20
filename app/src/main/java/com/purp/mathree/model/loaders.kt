package com.purp.mathree.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.util.Log
import androidx.databinding.ObservableField
import java.io.*

class EntityLoader(private val context: Context) {
    val entities: MutableList<Entity> = mutableListOf()
    var entityRemovedListener: ((Entity) -> Unit)? = null

    fun loadEntitiesFromJson(context: Context, jsonFilePath: String): MutableList<EntityGroup> {
        val inputStream = context.assets.open(jsonFilePath)
        val jsonText = inputStream.bufferedReader().use(BufferedReader::readText)
        val entityType = object : TypeToken<List<EntityGroup>>() {}.type
        return Gson().fromJson(jsonText, entityType)
    }

    fun loadFilteredEntities(castleType: String = "Dark", difficulty: String = "Easy"): MutableList<Entity> {
        val filteredGroups = loadEntitiesFromJson(context,"Enemies.json").filter { it.castleType == castleType && it.difficulty == difficulty }
        val randomGroup = filteredGroups.randomOrNull()
        Log.d("ГРУППА","${randomGroup}")
        var entities = randomGroup?.monsters.orEmpty() as MutableList<Entity>

        entities.forEach { entity ->
            val health = entity.maxhealth
            entity._health = ObservableField(health)
        }
        return entities
    }

    fun removeEntity(entity: Entity) {
        entities.remove(entity)
        entityRemovedListener?.invoke(entity)
    }

}