package id.indocyber.themoviedatabaseapplication.fragments.movie_discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.movie.discover.Result
import id.indocyber.themoviedatabaseapplication.databinding.LayoutMovieDiscoverFragmentItemBinding

class MovieDiscoverPagingAdapter(val moveFragment: (Result) -> Unit) :
    PagingDataAdapter<Result, MovieDiscoverViewHolder>(differ) {

    override fun onBindViewHolder(holder: MovieDiscoverViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.data = data
        holder.binding.root.setOnClickListener {
            data?.let { moveFragment(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDiscoverViewHolder =
        MovieDiscoverViewHolder(
            LayoutMovieDiscoverFragmentItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    companion object {
        val differ = object : DiffUtil.ItemCallback<Result>() {
            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class MovieDiscoverViewHolder(val binding: LayoutMovieDiscoverFragmentItemBinding) :
    RecyclerView.ViewHolder(binding.root)