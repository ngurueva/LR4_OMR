package com.example.lr4_omr

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lr4_omr.databinding.ActivityMainBinding
import com.example.lr4_omr.databinding.ListItemSongBinding
import java.io.Serializable
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var allSongs = mutableListOf<Song>()
    var favorites = mutableListOf<Song>()
    var albums = mutableListOf<Album>()
    var artists = mutableListOf<Artist>()
    val ALL_SONGS_REQUEST_CODE = 100
    private lateinit var songAdapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        songAdapter = SongAdapter(this, allSongs)
        binding.listViewSongs.adapter = songAdapter

        binding.albumRecycler.layoutManager = LinearLayoutManager(this)


        binding.buttonAdd.setOnClickListener { addSong() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show_favorites -> {
                showFavorites()
                return true
            }
            R.id.action_show_all_songs -> {
                showAllSongs()
                return true
            }
            R.id.action_albums -> {
                showAlbums()
                return true
            }
            R.id.action_artists -> {
                showArtists()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showFavorites() {
        val intent = Intent(this, FavoritesActivity::class.java)
        val favoritesBundle = Bundle()
        favoritesBundle.putSerializable("favorites", favorites as Serializable?)
        intent.putExtra("favoritesBundle", favoritesBundle)
        startActivity(intent)
    }

    private fun showAllSongs() {
        val intent = Intent(this, AllSongsActivity::class.java)
        val allSongsBundle = Bundle()
        allSongsBundle.putSerializable("allSongs", allSongs as Serializable?)
        intent.putExtra("allSongsBundle", allSongsBundle)
        startActivity(intent)
    }

    fun showAlbums() {
        val intent = Intent(this, AlbumActivity::class.java)
        val albumsBundle = Bundle()
        albumsBundle.putSerializable("albums", albums as Serializable?)
        intent.putExtra("albumsBundle", albumsBundle)
        startActivity(intent)
    }

    fun showArtists() {
        val intent = Intent(this, ArtistActivity::class.java)
        val artistsBundle = Bundle()
        artistsBundle.putSerializable("artists", artists as Serializable?)
        intent.putExtra("artistsBundle", artistsBundle)
        startActivity(intent)
    }

    private fun addSong() {
        val title = binding.editTextTitle.text.toString()
        val artist = binding.editTextArtist.text.toString()
        val album = binding.editTextAlbum.text.toString()
        val isFavorite = binding.checkBoxFavorite.isChecked

        if (title.isEmpty() || artist.isEmpty() || album.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        if (!albums.any { it.title == album && it.artist == artist }) {
            val newAlbum = Album(album, artist)
            albums.add(newAlbum)
        }

        if (!artists.any { it.name == artist }) {
            val newArtist = Artist(artist)
            artists.add(newArtist)
        }

        val newSong = Song(title, artist, album, isFavorite)
        allSongs.add(newSong)


        if (isFavorite) {
            favorites.add(newSong)
        }

        songAdapter.updateSongs(allSongs.takeLast(5))

        binding.editTextTitle.setText("")
        binding.editTextArtist.setText("")
        binding.editTextAlbum.setText("")
        binding.checkBoxFavorite.isChecked = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ALL_SONGS_REQUEST_CODE && resultCode == RESULT_OK) {
            val bundle = data?.extras
            allSongs = bundle?.getSerializable("allSongs") as? MutableList<Song> ?: mutableListOf()
            albums = bundle?.getSerializable("albums") as? MutableList<Album> ?: mutableListOf()
            artists = bundle?.getSerializable("artists") as? MutableList<Artist> ?: mutableListOf()
            favorites = bundle?.getSerializable("favorites") as? MutableList<Song> ?: mutableListOf()

            // Обновляем адаптер в `MainActivity`
            songAdapter.updateSongs(allSongs)
        }
    }



    private inner class SongAdapter(context: AppCompatActivity, songs: List<Song>) :
        ArrayAdapter<Song>(context, 0, songs) {

        private var displayedSongs = songs

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val binding = if (convertView == null) {
                ListItemSongBinding.inflate(LayoutInflater.from(context), parent, false)
            } else {
                ListItemSongBinding.bind(convertView)
            }

            val song = getItem(position)!!
            binding.songTitle.text = song.title
            binding.songArtist.text = song.artist
            binding.songAlbum.text = song.album

            if (song.isFavorite) {
                binding.songTitle.setTextColor(resources.getColor(R.color.yellow))
            } else {
                binding.songTitle.setTextColor(resources.getColor(android.R.color.black))
            }

            return binding.root
        }

        fun updateSongs(newSongs: List<Song>) {
            displayedSongs = newSongs
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return displayedSongs.size
        }

        override fun getItem(position: Int): Song? {
            return displayedSongs[position]
        }
    }
}
