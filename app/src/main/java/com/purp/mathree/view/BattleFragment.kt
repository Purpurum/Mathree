package com.purp.mathree.view

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.purp.mathree.R
import com.purp.mathree.databinding.FragmentBattleBinding
import com.purp.mathree.model.Ability
import com.purp.mathree.model.Entity
import com.purp.mathree.model.PlayerCharacter
import com.purp.mathree.viewmodel.CharacterViewModel
import com.purp.mathree.viewmodel.EntityViewModel
import com.purp.mathree.viewmodel.AbilitiesViewModel
import com.purp.mathree.viewmodel.ProgressViewModel


class BattleFragment() : Fragment(), EnemiesSizeChangeListener,AbilitySelectionChangeListener {
    private lateinit var binding: FragmentBattleBinding
    private lateinit var entityViewModel: EntityViewModel
    private lateinit var entityAdapter: EntityAdapter
    private lateinit var abilityAdapter: AbilityAdapter
    private lateinit var playerCharacterViewModel: CharacterViewModel
    private lateinit var abilitiesViewModel: AbilitiesViewModel
    private lateinit var enemyGroupType: String
    private lateinit var castleType: String

    private val characterViewModel: CharacterViewModel by lazy {
        CharacterViewModel.getInstance()
    }
    private val progressViewModel: ProgressViewModel by lazy {
        ProgressViewModel.getInstance()
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        entityViewModel = ViewModelProvider(this)[EntityViewModel::class.java]
        abilitiesViewModel = ViewModelProvider(this)[AbilitiesViewModel::class.java]
        playerCharacterViewModel = ViewModelProvider(this)[CharacterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            enemyGroupType = it.getString("enemyGroupType", "")
            castleType = it.getString("castleType", "")
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_battle, container, false)
        setupRecyclerViews()

        return binding.root
    }

    private fun setupRecyclerViews() {
        val abilitiesLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView2.layoutManager = abilitiesLayoutManager
        val playerCharacter: PlayerCharacter = playerCharacterViewModel.loadCharacterFromJSON(requireContext())
        val abilitiesList: List<Int> = playerCharacter.abilities
        val abilities: List<Ability> = abilitiesViewModel.loadAbilitiesFromJson(requireContext(), "abilities.json")
        val filteredAbilities: List<Ability> = abilities.filter { it.id in abilitiesList }
        abilityAdapter = AbilityAdapter(filteredAbilities)
        abilityAdapter.setAbilitySelectionChangeListener(this)
        binding.recyclerView2.adapter = abilityAdapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager

        var character = characterViewModel.setCharacter(context)
        var gameProgress = progressViewModel.setProgress(context as Context)

        entityAdapter = EntityAdapter(entityViewModel.getEnemies(castleType, enemyGroupType), entityViewModel, abilityAdapter, character, gameProgress)
        entityAdapter.settEnemiesSizeChangeListener(this)
        binding.recyclerView.adapter = entityAdapter
        entityViewModel.setEntityAdapter(entityAdapter)
    }

    override fun onEnemiesSizeChanged(newSize: Int) {
        Log.d("tag", "$newSize")
        if (newSize == 0){
            val navController = NavHostFragment.findNavController(this)
            navController.popBackStack()
        }
    }

    override fun onAbilitySelectionChanged(selectedAbility: Ability?) {
        entityAdapter.setSelectedAbility(selectedAbility)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        entityAdapter.settEnemiesSizeChangeListener(null)
        Log.d("Боевой фрагмент","Боевой фрагмент стёрт")
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            }
        )
    }
}
