package com.example.lr4_omr

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lr4_omr.databinding.ListItemSongBinding
import androidx.recyclerview.widget.ItemTouchHelper


// Обновленный класс SongAdapter
class SongAdapter(
    public var songs: List<Song>,
    private val onItemClick: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    private var displayedSongs = songs
    inner class SongViewHolder(private val binding: ListItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.songTitle.text = song.title
            binding.songArtist.text = song.artist
            binding.songAlbum.text = song.album

            itemView.setOnClickListener {
                onItemClick(song)
            }

            // Обработка выделения избранных песен
            if (song.isFavorite) {
                binding.songTitle.setTextColor(itemView.resources.getColor(R.color.yellow))
            } else {
                binding.songTitle.setTextColor(itemView.resources.getColor(android.R.color.black))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ListItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songs[position])
    }



    override fun getItemCount(): Int {
        return songs.size
    }

    fun removeAt(position: Int) {
        songs = songs.toMutableList().also { it.removeAt(position) }
        notifyItemRemoved(position)
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateSongs(newSongs: List<Song>) {
        displayedSongs = newSongs
        notifyDataSetChanged()
    }

    fun getCount(): Int {
        return displayedSongs.size
    }

    fun getItem(position: Int): Song? {
        return displayedSongs[position]
    }
}
