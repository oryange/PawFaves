package com.app.pawfaves.model.data.local

import android.content.Context
import com.app.pawfaves.utils.Constants.KEY_PREFERENCES
import com.app.pawfaves.utils.Constants.KEY_SHARED_PREFERENCES

class PawFavesSharedPreferencesImpl(context: Context) : PawFavesSharedPreferences {
    private val sharedPreferences =
        context.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val itemList = sharedPreferences.getStringSet(KEY_PREFERENCES, mutableSetOf())

    override fun addFavorite(newItem: String): Boolean {
        itemList?.add(newItem)
        editor.putStringSet(KEY_PREFERENCES, itemList).apply()
        return true
    }

    override fun removeFavorite(itemToRemove: String): Boolean {
        itemList?.remove(itemToRemove)
        editor.putStringSet(KEY_PREFERENCES, itemList)
        editor.apply()
        return true
    }

    override fun getFavorite(): List<String> = itemList?.toList() ?: emptyList()
}
