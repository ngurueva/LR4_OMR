package com.example.lr4_omr

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.example.lr4_omr.databinding.ActivityArtistBinding

class ArtistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtistBinding
    private lateinit var artistAdapter: ArrayAdapter<Artist>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val artistsBundle = intent.getBundleExtra("artistsBundle")
        val artists = artistsBundle?.getSerializable("artists") as? List<Artist> ?: listOf()

        artistAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, artists)
        binding.listViewArtists.adapter = artistAdapter
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
