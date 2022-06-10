package id.indocyber.themoviedatabaseapplication.fragments.movie_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.themoviedatabaseapplication.databinding.LayoutMovieDetailsFragmentReviewItemBinding
import id.indocyber.common.entity.movie.review.Result

class MovieDetailsReviewsPagingAdapter :
    PagingDataAdapter<Result, MovieDetailsReviewsViewHolder>(differ) {

    override fun onBindViewHolder(holder: MovieDetailsReviewsViewHolder, position: Int) {
        holder.binding.data = getItem(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieDetailsReviewsViewHolder = MovieDetailsReviewsViewHolder(
        LayoutMovieDetailsFragmentReviewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    companion object {
        val differ = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

class MovieDetailsReviewsViewHolder(val binding: LayoutMovieDetailsFragmentReviewItemBinding) :
    RecyclerView.ViewHolder(binding.root)