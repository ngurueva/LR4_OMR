package com.example.lr4_omr

import java.io.Serializable

data class Album(val title: String, val artist: String) : Serializable  {
    override fun toString(): String {
        return "$title - $artist"
    }
}
