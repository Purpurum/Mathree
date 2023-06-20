package com.purp.mathree.model

data class Progress(
    var monstersSlayed: Int = 0,
    var tries: Int = 0,
    var collectedBossItem1: Boolean = false,
    var collectedBossItem2: Boolean = false,
    var collectedBossItem3: Boolean = false,
    var completedGame: Boolean = false,
    var storyDialogStartShown: Boolean = false,
    var storyDialogDarkShown: Boolean = false,
    var storyDialogBloodShown: Boolean = false,
    var storyDialogTechShown: Boolean = false,
    var storyDialogCompleteShown: Boolean = false,
    var storyDialogHubShown: Boolean = false
)