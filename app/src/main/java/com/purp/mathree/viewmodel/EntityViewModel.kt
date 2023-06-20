package com.purp.mathree.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.purp.mathree.model.Entity
import com.purp.mathree.model.EntityLoader
import com.purp.mathree.view.EntityAdapter


class EntityViewModel(application: Application) : AndroidViewModel(application) {

    private val entityLoader: EntityLoader
    private var entityAdapter: EntityAdapter? = null

    init {
        val context = application.applicationContext
        entityLoader = EntityLoader(context)
        entityLoader.entityRemovedListener = { entity ->
            entityAdapter?.removeEntity(entity)
        }
    }

    fun setEntityAdapter(adapter: EntityAdapter) {
        entityAdapter = adapter
    }

    fun getEnemies(): MutableList<Entity> {
        return getFilteredEntities()
    }

    fun decreaseEntityHealth(entity: Entity, amount: Int) {
        entity.decreaseHealth(amount)
        if (!entity.isAlive) {
            removeEntity(entity)
        }
    }

    fun removeEntity(entity: Entity) {
        entityLoader.removeEntity(entity)
    }

    fun getFilteredEntities(): MutableList<Entity> {
        return entityLoader.loadFilteredEntities("Dark")
    }
}
