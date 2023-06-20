package com.purp.mathree.view

import androidx.navigation.fragment.findNavController
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.purp.mathree.R
import com.purp.mathree.model.Tile
import com.purp.mathree.viewmodel.MapViewModel
import kotlinx.coroutines.*
import java.util.*


class MapFragment : Fragment() {
    private val mapViewModel: MapViewModel by viewModels()
    private var tileZeroingJob: Job? = null
    private var timer: Timer? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.map_fragment, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val tileAdapter = TileAdapter()

        recyclerView.apply {
            val itemsPerRow = 7
            layoutManager = GridLayoutManager(requireContext(), itemsPerRow)
            adapter = tileAdapter
        }

        mapViewModel.map.observe(viewLifecycleOwner, Observer { map ->
            tileAdapter.setTiles(map)
        })

        tileAdapter.setOnTileClickListener { tile ->
            when (tile.number) {
                0 -> {
                    mapViewModel.updateTileClicked(tile, true)
                    Log.d("MapFragment", "Clicked tile number is ${tile.number}")

                }
                1 -> {
                    mapViewModel.updateTileClicked(tile, true)
                    val action = MapFragmentDirections.actionMapFragmentToTQFragment("random", "Dark")

                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(action)
                    }, 1000)
                    scheduleTileZeroing(tile)
                }
                2 -> {
                    mapViewModel.updateTileClicked(tile, true)
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(MapFragmentDirections.actionMapFragmentToBattleListFragment())}, 1000)
                    scheduleTileZeroing(tile)
                }
                3 -> {
                    mapViewModel.updateTileClicked(tile, true)
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(MapFragmentDirections.actionMapFragmentToBattleListFragment())}, 1000)
                    scheduleTileZeroing(tile)
                }
            }
        }
        return view
    }
    fun scheduleTileZeroing(tile: Tile) {
        tileZeroingJob?.cancel() // Cancel any existing job

        tileZeroingJob = CoroutineScope(Dispatchers.Main).launch {
            delay(1000) // Delay for 2 seconds
            mapViewModel.zeroTileNumber(tile)
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
