package io.chuuhomg.beers.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.chuuhomg.beers.R
import io.chuuhomg.beers.data.remote.model.Beer
import io.chuuhomg.beers.data.remote.model.BeerViewType
import io.chuuhomg.beers.util.BeerFilterManger
import io.chuuhomg.beers.util.BeerFilterType
import io.chuuhomg.beers.util.loadUrl

class BeersAdapter(private val listener: OnClickBeerItemListener) : RecyclerView.Adapter<BeersAdapter.BeerBaseViewHolder>() {

    private val beers = mutableListOf<Beer>()
    private val filterBeers = mutableListOf<Beer>()
    private val beerFilterManger = BeerFilterManger()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerBaseViewHolder {
        return when (viewType) {
            BeerViewType.VIEW_TYPE_01.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_beer_item_type01, parent, false)
                BeerType01ViewHolder(view)
            }
            BeerViewType.VIEW_TYPE_02.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_beer_item_type02, parent, false)
                BeerType02ViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_beer_item_type03, parent, false)
                BeerType03ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: BeerBaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return filterBeers.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }

    fun addAll(beers: List<Beer>) {
        this.beers.addAll(beers)

        filterBeers.clear()
        filterBeers.addAll(beerFilterManger.getFilterList(this.beers))

        notifyDataSetChanged()
    }

    private fun getItem(position: Int) = filterBeers[position]

    fun clear() {
        beers.clear()
        filterBeers.clear()

        notifyDataSetChanged()
    }

    fun setBeerFilter(type: BeerFilterType, progress: Int) {
        filterBeers.clear()
        filterBeers.addAll(beerFilterManger.getFilterList(beers, Pair(type, progress)))

        notifyDataSetChanged()
    }

    abstract inner class BeerBaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(beer: Beer)

        fun setOnClickListener(tvName: TextView, ivBeer: ImageView, beer: Beer) {
            itemView.setOnClickListener { listener.onClickBeerItem(tvName, ivBeer, beer) }
        }
    }

    inner class BeerType01ViewHolder(view: View) : BeerBaseViewHolder(view) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val ivBeer: ImageView = itemView.findViewById(R.id.ivBeer)
        private val tvAbv: TextView = itemView.findViewById(R.id.tvAbv)
        private val tvIbu: TextView = itemView.findViewById(R.id.tvIbu)
        private val tvSrm: TextView = itemView.findViewById(R.id.tvSrm)

        override fun bind(beer: Beer) {
            tvName.text = beer.name
            ivBeer.loadUrl(beer.image)
            tvAbv.text = beer.abv.toString()
            tvIbu.text = beer.ibu.toString()
            tvSrm.text = beer.srm.toString()

            setOnClickListener(tvName, ivBeer, beer)
        }
    }

    inner class BeerType02ViewHolder(view: View) : BeerBaseViewHolder(view) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val ivBeer: ImageView = itemView.findViewById(R.id.ivBeer)
        private val tvAbv: TextView = itemView.findViewById(R.id.tvAbv)
        private val tvIbu: TextView = itemView.findViewById(R.id.tvIbu)
        private val tvSrm: TextView = itemView.findViewById(R.id.tvSrm)

        override fun bind(beer: Beer) {
            tvName.text = beer.name
            ivBeer.loadUrl(beer.image)
            tvAbv.text = beer.abv.toString()
            tvIbu.text = beer.ibu.toString()
            tvSrm.text = beer.srm.toString()

            setOnClickListener(tvName, ivBeer, beer)
        }
    }

    inner class BeerType03ViewHolder(view: View) : BeerBaseViewHolder(view) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val ivBeer: ImageView = itemView.findViewById(R.id.ivBeer)
        private val tvTagLine: TextView = itemView.findViewById(R.id.tvTagLine)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        override fun bind(beer: Beer) {
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