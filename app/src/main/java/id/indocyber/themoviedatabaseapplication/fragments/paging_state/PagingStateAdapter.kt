package id.indocyber.themoviedatabaseapplication.fragments.paging_state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.themoviedatabaseapplication.databinding.LayoutPagingStateBinding

class PagingStateAdapter(val retryAction: () -> Unit) : LoadStateAdapter<PagingStateViewHolder>() {
    override fun onBindViewHolder(holder: PagingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingStateViewHolder =
        PagingStateViewHolder(
            LayoutPagingStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), retryAction
        ).apply {
            bind(loadState)
        }
}

class PagingStateViewHolder(val binding: LayoutPagingStateBinding, val retryAction : ()-> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        binding.retryButton.setOnClickListener {
            retryAction()
        }

        when (loadState) {
            is LoadState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.retryButton.visibility = View.GONE
            }
            else -> {
                binding.progressBar.visibility = View.GONE
                binding.retryButton.visibility = View.VISIBLE
            }
        }
    }
}