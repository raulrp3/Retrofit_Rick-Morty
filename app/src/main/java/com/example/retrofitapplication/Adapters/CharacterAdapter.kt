package com.example.retrofitapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapplication.Models.Character
import com.example.retrofitapplication.R
import com.squareup.picasso.Picasso

class CharacterAdapter (
    private var characters: List<Character>,
    private var context: Context
): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = characters.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        return ViewHolder(inflater.inflate(R.layout.item_character_list, parent, false));
    }

    override fun getItemCount(): Int {
        return characters.count()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var ivImageCharacter: ImageView = view.findViewById(R.id.ivImageCharacter)
        var tvNameCharacter: TextView = view.findViewById(R.id.tvNameCharacter);

        fun bind(character: Character, context: Context) {
            ivImageCharacter.loadUrl(character.image)
            tvNameCharacter.text = character.name
        }

        private fun ImageView.loadUrl(url: String) {
            Picasso.with(context).load(url).into(this);
        }
    }
}