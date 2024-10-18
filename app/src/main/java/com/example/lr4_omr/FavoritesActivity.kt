package com.example.lr4_omr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.lr4_omr.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var favoritesAdapter: ArrayAdapter<Song>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val favoritesBundle = intent.getBundleExtra("favoritesBundle")
        val favorites = favoritesBundle?.getSerializable("favorites") as? List<Song> ?: listOf()

        favoritesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, favorites)
        binding.listViewFavorites.adapter = favoritesAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}