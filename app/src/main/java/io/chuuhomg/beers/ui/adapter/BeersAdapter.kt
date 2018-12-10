package io.chuuhomg.beers.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.chuuhomg.beers.R
import io.chuuhomg.beers.data.model.Beer
import io.chuuhomg.beers.data.model.BeerViewType
import io.chuuhomg.beers.util.loadUrl

class BeersAdapter(private val listener: OnClickBeerItemListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val beers = mutableListOf<Beer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BeerViewType.VIEW_TYPE_01.ordinal -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_beer_item_type01, parent, false)
                BeerType01ViewHolder(view)
            }
            BeerViewType.VIEW_TYPE_02.ordinal -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_beer_item_type02, parent, false)
                BeerType02ViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_beer_item_type03, parent, false)
                BeerType03ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BeerType01ViewHolder -> holder.bind(beers[position])
            is BeerType02ViewHolder -> holder.bind(beers[position])
            is BeerType03ViewHolder -> holder.bind(beers[position])
        }
    }

    override fun getItemCount(): Int {
        return beers.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }

    fun addAll(beers: List<Beer>) {
        this.beers.addAll(beers)

        notifyDataSetChanged()
    }

    private fun getItem(position: Int) = beers[position]

    fun clear() {
        beers.clear()

        notifyDataSetChanged()
    }

    abstract inner class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun setOnClickListener(tvName: TextView, ivBeer: ImageView, beer: Beer) {
            itemView.setOnClickListener { listener.onClickBeerItem(tvName, ivBeer, beer) }
        }
    }

    inner class BeerType01ViewHolder(view: View) : BaseViewHolder(view) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val ivBeer: ImageView = itemView.findViewById(R.id.ivBeer)
        private val tvAbv: TextView = itemView.findViewById(R.id.tvAbv)
        private val tvIbu: TextView = itemView.findViewById(R.id.tvIbu)
        private val tvSrm: TextView = itemView.findViewById(R.id.tvSrm)

        fun bind(beer: Beer) {
            tvName.text = beer.name
            ivBeer.loadUrl(beer.image)
            tvAbv.text = beer.abv.toString()
            tvIbu.text = beer.ibu.toString()
            tvSrm.text = beer.srm.toString()

            setOnClickListener(tvName, ivBeer, beer)
        }
    }

    inner class BeerType02ViewHolder(view: View) : BaseViewHolder(view) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val ivBeer: ImageView = itemView.findViewById(R.id.ivBeer)
        private val tvAbv: TextView = itemView.findViewById(R.id.tvAbv)
        private val tvIbu: TextView = itemView.findViewById(R.id.tvIbu)
        private val tvSrm: TextView = itemView.findViewById(R.id.tvSrm)

        fun bind(beer: Beer) {
            tvName.text = beer.name
            ivBeer.loadUrl(beer.image)
            tvAbv.text = beer.abv.toString()
            tvIbu.text = beer.ibu.toString()
            tvSrm.text = beer.srm.toString()

            setOnClickListener(tvName, ivBeer, beer)
        }
    }

    inner class BeerType03ViewHolder(view: View) : BaseViewHolder(view) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val ivBeer: ImageView = itemView.findViewById(R.id.ivBeer)
        private val tvTagLine: TextView = itemView.findViewById(R.id.tvTagLine)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        fun bind(beer: Beer) {
            tvName.text = beer.name
            ivBeer.loadUrl(beer.image)
            tvTagLine.text = beer.tagline
            tvDescription.text = beer.description

            setOnClickListener(tvName, ivBeer, beer)
        }
    }
}

interface OnClickBeerItemListener {
    fun onClickBeerItem(tvName: TextView, ivBeer: ImageView, beer: Beer)
}