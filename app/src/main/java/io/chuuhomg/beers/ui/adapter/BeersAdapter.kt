package io.chuuhomg.beers.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.chuuhomg.beers.R
import io.chuuhomg.beers.data.remote.model.Beer
import io.chuuhomg.beers.util.loadUrl
import kotlinx.android.synthetic.main.list_beer_item.view.*

class BeersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val beers = mutableListOf<Beer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_beer_item, parent, false)
        return BeersViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BeersViewHolder) {
            holder.bind(beers[position])
        }
    }

    override fun getItemCount(): Int {
        return beers.size
    }

    fun addAll(beers: List<Beer>) {
        this.beers.clear()
        this.beers.addAll(beers)

        notifyDataSetChanged()
    }

    inner class BeersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(beer: Beer) {
            itemView.tvName.text = beer.name
            itemView.ivBeer.loadUrl(beer.image)
            itemView.tvAbv.text = beer.abv.toString()
            itemView.tvIbu.text = beer.ibu.toString()
            itemView.tvSrm.text = beer.srm.toString()
        }
    }
}