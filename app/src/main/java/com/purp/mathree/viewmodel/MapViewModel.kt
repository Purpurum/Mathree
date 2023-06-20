package com.purp.mathree.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.purp.mathree.R
import com.purp.mathree.model.Tile
import com.purp.mathree.viewmodel.CharacterViewModel

class MapViewModel : ViewModel() {
    private val _map: MutableLiveData<List<Tile>> = MutableLiveData()
    val map: LiveData<List<Tile>>
        get() = _map

    init {
        _map.value = createMap()
    }

    private fun createMap(floorLevel: Int = 1): MutableList<Tile> {
        val tileList = mutableListOf<Tile>()
        var counter = 0
        repeat(63) {
            if (counter <= 11){
                tileList.add(Tile(floorLevel, 1))
                counter++
            }
            else if (11 < counter && counter <= 22){
                tileList.add(Tile(floorLevel, 2))
                counter++
            }
            else if (counter == 23){
                tileList.add(Tile(floorLevel, 3))
                counter++
            }
            else if (counter == 24){
                tileList.add(Tile(floorLevel, 4, hasPlayer = true))
                counter++
            }
            else if (23 < counter && counter <= 33){
                tileList.add(Tile(floorLevel, (1..2).random()))
                counter++
            }
            else {
                tileList.add(Tile(floorLevel, 0))
                counter++
            }
        }

        val tileData = tileList.shuffled()
        for (tile in tileData) {
            if (tile.clicked) {
                when (tile.number) {
                    0 -> tile.drawable = R.drawable.blank
                    1 -> tile.drawable = R.drawable.enc
                    2 -> tile.drawable = R.drawable.mon
                    3 -> tile.drawable = R.drawable.boss

                }
            } else {
                if (tile.hasPlayer == false) {
                    tile.drawable = R.drawable.firstfloor
                }
                else{
                    tile.drawable = R.drawable.doorway
                }
            }
        }
        return tileData as MutableList<Tile>
    }

    fun updateTileClicked(tile: Tile, clicked: Boolean) {
        tile.clicked = clicked
        updateTileDrawable(tile)
    }

    fun zeroTileNumber(tile: Tile) {
        tile.number = if (tile.hasPlayer) 4 else 0
        updateTileDrawable(tile)

    }

    private fun updateTileDrawable(tile: Tile) {
        if (tile.clicked) {
            when (tile.number) {
                0 -> tile.drawable = R.drawable.blank
                1 -> tile.drawable = R.drawable.enc
                2 -> tile.drawable = R.drawable.mon
                3 -> tile.drawable = R.drawable.boss
                4 -> tile.drawable = R.drawable.player
            }
        } else {
            if (tile.hasPlayer == false) {
                tile.drawable = R.drawable.firstfloor
            }
        }

        _map.value = _map.value // Trigger LiveData update
    }
}