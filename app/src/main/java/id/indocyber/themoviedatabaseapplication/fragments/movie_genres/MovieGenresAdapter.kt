package id.indocyber.themoviedatabaseapplication.fragments.movie_genres

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.indocyber.common.entity.genre.Genre
import id.indocyber.themoviedatabaseapplication.databinding.LayoutGenresFragmentItemBinding

class MovieGenresAdapter(
    val isSelected: (Long) -> Boolean
) : RecyclerView.Adapter<MovieGenresViewHolder>() {
    val dataDiffer = AsyncListDiffer<Genre>(this, differ)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenresViewHolder {
        return MovieGenresViewHolder(
            LayoutGenresFragmentItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ), dataDiffer
        )
    }

    override fun onBindViewHolder(holder: MovieGenresViewHolder, position: Int) {
        val data = dataDiffer.currentList[position]
        holder.binding.data = data
        holder.binding.isSelected = isSelected(getItemId(position))
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    fun submitData(data: List<Genre>) {
        dataDiffer.submitList(data)
    }

    override fun getItemId(position: Int): Long = dataDiffer.currentList[position].id.toLong()


    companion object {
        val differ = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return true
            }
        }
    }

    inner class MovieGenresItemKeyProvider() : ItemKeyProvider<Long>(SCOPE_CACHED) {
        override fun getKey(position: Int): Long? = dataDiffer.currentList[position].id.toLong()

        override fun getPosition(key: Long): Int =
            dataDiffer.currentList.indexOfFirst { it.id.toLong() == key }
    }

    fun getItemKeyProvider() = MovieGenresItemKeyProvider()

}

class MovieGenresViewHolder(
    val binding: LayoutGenresFragmentItemBinding,
    val list: AsyncListDiffer<Genre>
) :
    RecyclerView.ViewHolder(binding.root) {

    fun getItemDetail() = object : ItemDetailsLookup.ItemDetails<Long>() {
        override fun getPosition(): Int = bindingAdapterPosition

        override fun getSelectionKey(): Long? = list.currentList[bindingAdapterPosition].id.toLong()
    }
}


class MovieGenresItemDetailsLookUp(val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? =
        recyclerView.findChildViewUnder(e.x, e.y)?.let {
            (recyclerView.getChildViewHolder(it) as MovieGenresViewHolder).getItemDetail()
        }
}