package com.example.lr4_omr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.lr4_omr.databinding.ActivityAllSongsBinding

class AllSongsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllSongsBinding
    private lateinit var allSongsAdapter: ArrayAdapter<Song>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllSongsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val allSongsBundle = intent.getBundleExtra("allSongsBundle")
        val allSongs = allSongsBundle?.getSerializable("allSongs") as? List<Song> ?: listOf()

        allSongsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, allSongs)
        binding.listViewAllSongs.adapter = allSongsAdapter
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