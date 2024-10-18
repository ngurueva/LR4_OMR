package com.example.lr4_omr

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.example.lr4_omr.databinding.ActivityAlbumBinding
import java.io.Serializable

class AlbumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumBinding
    private lateinit var albumAdapter: ArrayAdapter<Album>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val albumsBundle = intent.getBundleExtra("albumsBundle")
        val albums = albumsBundle?.getSerializable("albums") as? List<Album> ?: listOf()

        albumAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, albums)
        binding.listViewAlbums.adapter = albumAdapter
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
