package com.purp.mathree.view

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.purp.mathree.R
import com.purp.mathree.model.Ability

interface AbilitySelectionChangeListener {
    fun onAbilitySelectionChanged(selectedAbility: Ability?)
}

class AbilityAdapter(private val abilityList: List<Ability>,
                     private var selectedAbility: Ability? = null) : RecyclerView.Adapter<AbilityAdapter.AbilityViewHolder>() {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
    private var abilitySelectionChangeListener: AbilitySelectionChangeListener? = null

    fun setAbilitySelectionChangeListener(listener: AbilitySelectionChangeListener?) {
        abilitySelectionChangeListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ability, parent, false)
        return AbilityViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        val ability = abilityList[position]
        holder.bind(ability)

        holder.itemView.setOnClickListener {
            val previousSelectedItemPosition = selectedItemPosition
            selectedAbility = ability
            Log.d("Пытаюсь это отправить","${selectedAbility!!.name}")
            notifyDataSetChanged()
            selectedItemPosition = if (selectedItemPosition == position) {
                RecyclerView.NO_POSITION
            } else {
                position
            }
            if (selectedItemPosition == RecyclerView.NO_POSITION){
                abilitySelectionChangeListener?.onAbilitySelectionChanged(null)
            }
            else{
                abilitySelectionChangeListener?.onAbilitySelectionChanged(selectedAbility)
            }
            notifyItemChanged(selectedItemPosition)
            notifyItemChanged(previousSelectedItemPosition)

        }
        if (selectedItemPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#AAAAAA"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"))
        }
    }

    fun getSelectedAbility(): Ability? {
        return selectedAbility
    }

    fun getAbilities(): List<Ability> {
        return abilityList
    }

    override fun getItemCount(): Int {
        return abilityList.size
    }

    inner class AbilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val damageTextView: TextView = itemView.findViewById(R.id.damageTextView)
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)


        fun bind(ability: Ability) {
            nameTextView.text = ability.name
            damageTextView.text = "Damage: ${ability.damage}"
            loadImageFromAsset(iconImageView, ability.icon)
            }
        }

}
