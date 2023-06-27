package com.purp.mathree.view

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.purp.mathree.R
import com.purp.mathree.databinding.ItemEnemyBinding
import com.purp.mathree.model.Ability
import com.purp.mathree.model.Entity
import com.purp.mathree.model.PlayerCharacter
import com.purp.mathree.model.Progress
import com.purp.mathree.viewmodel.CharacterViewModel
import com.purp.mathree.viewmodel.EntityViewModel

interface EnemiesSizeChangeListener {
    fun onEnemiesSizeChanged(newSize: Int)
}

class EntityAdapter(private val enemies: MutableList<Entity>,
                    private val entityViewModel: EntityViewModel,
                    private val abilityAdapter: AbilityAdapter,
                    private val character: PlayerCharacter,
                    private val gameProgress: Progress
                    )  :
    RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {

    var enemiesSizeChangeListener: EnemiesSizeChangeListener? = null
    var selecteddAbility: Ability? = null

    fun settEnemiesSizeChangeListener(listener: EnemiesSizeChangeListener?) {
        enemiesSizeChangeListener = listener
    }

    fun setSelectedAbility(ability: Ability?){
        selecteddAbility = ability
        Log.d("Получаю","${selecteddAbility?.name}")

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemEnemyBinding = ItemEnemyBinding.inflate(inflater, parent, false)
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val enemy = enemies[position]
        holder.bind(enemy)
        val selectedAbility: Ability? = abilityAdapter.getSelectedAbility()

        fun animateBackgroundColor(color: Int) {
            val startColor = Color.TRANSPARENT
            val endColor = color

            val animator = ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)
            animator.apply {
                duration = 100 // Animation duration in milliseconds
                repeatCount = 1 // Number of times to repeat the animation
                repeatMode = ValueAnimator.REVERSE // Reverse the animation on repeat
                interpolator = AccelerateDecelerateInterpolator()

                addUpdateListener { animation ->
                    val color = animation.animatedValue as Int
                    holder.itemView.setBackgroundColor(color)
                }
            }
            animator.start()
        }

        fun enemyTurn(){
            for (enemy in enemies){
                if (enemy.isStunned == false){
                    character.setHealth(-1*enemy.damage)

                }
                else if (enemy.isStunned == true) {
                    holder.itemView.setBackgroundColor(Color.parseColor("#0000FF"))
                }
            }
            for (ability in abilityAdapter.getAbilities()){
                if (ability.cooldownCounter > 0){
                    ability.cooldownCounter -= 1

                }
            }
        }

        holder.itemView.setOnClickListener {

            selecteddAbility?.let { ability ->
                if (ability.cooldownCounter == 0) {
                    Log.d("АБИЛИТИ ЧОЗЕН!", ability.name)
                    val enemiesCopy = enemies.toList()
                    val enemiesToRemove = mutableListOf<Entity>()
                    if (ability.AOE) {
                        for (enemy in enemiesCopy) {
                            entityViewModel.decreaseEntityHealth(enemy, ability.damage)
                            if (!enemy.isAlive) {
                                enemiesToRemove.add(enemy)
                                character.setEssence(enemy.essenceDrop)
                                gameProgress.monstersSlayed += 1

                            }
                        }
                        for (enemyToRemove in enemiesToRemove) {
                            entityViewModel.removeEntity(enemyToRemove)
                        }
                        if (ability.stun) {
                            enemy.isStunned = true
                            holder.itemView.setBackgroundColor(Color.parseColor("#0000FF"))
                        }
                    }
                    if (ability.stun) {
                        enemy.isStunned = true
                        holder.itemView.setBackgroundColor(Color.parseColor("#0000FF"))
                    }

                    ability.cooldownCounter = ability.cooldown+1
                    selecteddAbility = null
                    enemyTurn()
                } else {

                }
            } ?: run {
                entityViewModel.decreaseEntityHealth(enemy, character.strength)
                animateBackgroundColor(Color.RED)
                if (!enemy.isAlive) {
                    entityViewModel.removeEntity(enemy)
                    character.setEssence(enemy.essenceDrop)

                }
                enemyTurn()
            }
        }

    }

    override fun getItemCount(): Int {
        return enemies.size
    }

    inner class EntityViewHolder(private val binding: ItemEnemyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Entity) {
            binding.enemy = entity
            binding.executePendingBindings()
        }

        fun changeBackgroundColor(color: Int) {
            itemView.setBackgroundColor(color)
        }
    }



    fun removeEntity(entity: Entity) {
        val position = enemies.indexOf(entity)
        if (position != -1) {
            enemies.removeAt(position)
            val newSize = enemies.size
            enemiesSizeChangeListener?.onEnemiesSizeChanged(newSize)
            notifyItemRemoved(position)
        }
    }
}
