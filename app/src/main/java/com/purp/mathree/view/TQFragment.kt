package com.purp.mathree.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.purp.mathree.GameActivity
import com.purp.mathree.R
import com.purp.mathree.model.Outcome
import com.purp.mathree.model.PlayerCharacter
import com.purp.mathree.model.TQEncounter
import com.purp.mathree.viewmodel.CharacterViewModel
import com.purp.mathree.viewmodel.TQViewModel
import kotlinx.coroutines.*

class TQFragment() : Fragment() {
    lateinit var tqViewModel: TQViewModel
    lateinit var questType: String
    lateinit var castleType: String

    private var delayingJob: Job? = null
    private val characterViewModel: CharacterViewModel by lazy {
        CharacterViewModel.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            questType = it.getString("questType", "")
            castleType = it.getString("castleType", "")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val TQView = inflater.inflate(R.layout.tq_fragment, container, false)
        return TQView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        tqViewModel = ViewModelProvider(this)[TQViewModel::class.java]
        var character = characterViewModel.setCharacter(context)
        tqViewModel.loadDialoguesFromJson(context)
        tqViewModel.getDialogues().observe(viewLifecycleOwner) { dialogues ->
            val filteredDialogues =
                dialogues.filter { it.type == questType && it.castleType == castleType }

            if (filteredDialogues.isNotEmpty()) {
                val chosenDialogue = filteredDialogues.random() //Диалог тута
                createDialog(chosenDialogue, character, view)
            }
        }

    }

    fun createDialog(chosenDialogue: TQEncounter, character: PlayerCharacter, view: View) {
        fun outcomeHandler(outcomes: List<Outcome>, index: Int) {
            for (outcome in outcomes) {
                when (outcome.type) { // "hp", "essence", "item", "open dialog", "change text"
                    "hp" -> {
                        var newValue = outcome.value as Double
                        var newValueInt = newValue.toInt()
                        Log.d("МЕНЯЮ ЗДОРОВЬЕ!", "РАЗМЕР ИЗМЕНЕНИЯ: ${outcome.value}")
                        character?.setHealth(newValueInt)
                    }
                    "essence" -> {
                        var newValue = outcome.value as Double
                        var newValueInt = newValue.toInt()
                        Log.d("МЕНЯЮ ЭССЕНЦИЮ!", "РАЗМЕР ИЗМЕНЕНИЯ: ${outcome.value}")
                        character.setEssence(newValueInt)
                    }
                    "item" -> {
                        Log.d("ПОЛУЧЕН ПРЕДМЕТ!", "ИД ПРЕДМЕТА: ${outcome.value}")
                        Log.d("ПОЛУЧЕН ПРЕДМЕТ!", "ПЕРСОНАЖ: ${character}")
                        Log.d("ПОЛУЧЕН ПРЕДМЕТ!", "ИНВЕНТАРЬ: ${character.itemsInInventory}")
                        var newValue = outcome.value as Double
                        var newValueInt = newValue.toInt()
                        character.giveItem(newValueInt, context)
                        Log.d("ДОБАВЛЯЮ ПРЕДМЕТ!", "ИД ПРЕДМЕТА: ${newValueInt}, СПИСОК ПРЕДМЕТОВ:${character.itemsInInventory}")
                    }
                    "open dialog" -> {
                        Log.d("ОТКРЫВАЮ ДИАЛОГ!", "ИД ДИАЛОГА: ${outcome.value}")
                        var newValue = outcome.value as Double
                        var newValueInt = newValue.toInt()
                        var dialogToOpen = tqViewModel.getDialogues().value?.filter { it.id == newValueInt }!![0]
                        createDialog(dialogToOpen, character, view)
                    }
                    "change text" -> {
                        Log.d("МЕНЯЮ ТЕКСТ!", "ТЕКСТ: ${outcome.value}")
                        view.findViewById<TextView>(R.id.Dialog_text).text =
                            outcome.value.toString()
                    }
                }
                if (chosenDialogue.options[index].ender) {
                    delayingJob?.cancel() // Cancel any existing job
                    val navController = NavHostFragment.findNavController(this)
                    delayingJob = CoroutineScope(Dispatchers.Main).launch {
                        delay(1000) // Delay for 2 seconds
                        navController.popBackStack()
                    }
                }
            }
        }
        //Сама функция начинается тут
        val drawable: Drawable? =
            Drawable.createFromStream(context?.assets?.open(chosenDialogue.imgPath), null)
        Glide.with(this)
            .load(drawable)
            .into(view.findViewById<ImageView>(R.id.Dialog_image))
        view.findViewById<TextView>(R.id.Dialog_text).text = chosenDialogue.text
        if (chosenDialogue.options.getOrNull(0) != null) {
            view.findViewById<Button>(R.id.Option1).text = chosenDialogue.options[0].text
            view.findViewById<Button>(R.id.Option1).setOnClickListener {
                var outcomes = chosenDialogue.options[0].outcome
                outcomeHandler(outcomes, 0)
            }
        }
        else {
            view.findViewById<Button>(R.id.Option1).visibility = View.GONE
        }
        if (chosenDialogue.options.getOrNull(1) != null) {
            view.findViewById<Button>(R.id.Option2).text = chosenDialogue.options[1].text
            view.findViewById<Button>(R.id.Option2).setOnClickListener {
                var outcomes = chosenDialogue.options[1].outcome
                outcomeHandler(outcomes, 1)
            }
        }
        else {
            view.findViewById<Button>(R.id.Option2).visibility = View.GONE
        }
        if (chosenDialogue.options.getOrNull(2) != null) {
            view.findViewById<Button>(R.id.Option3).text = chosenDialogue.options[2].text
            view.findViewById<Button>(R.id.Option3).setOnClickListener {
                var outcomes = chosenDialogue.options[2].outcome
                outcomeHandler(outcomes, 2)
            }
        }
        else {
            view.findViewById<Button>(R.id.Option3).visibility = View.GONE
        }
        if (chosenDialogue.options.getOrNull(3) != null) {
            view.findViewById<Button>(R.id.Option4).text = chosenDialogue.options[3].text
            view.findViewById<Button>(R.id.Option4).setOnClickListener {
                var outcomes = chosenDialogue.options[3].outcome
                outcomeHandler(outcomes, 3)
            }
        }
        else {
            view.findViewById<Button>(R.id.Option4).visibility = View.GONE
        }
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
