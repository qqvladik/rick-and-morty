package by.mankevich.rickandmorty.feature.adapter.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R

class MainLoadStateAdapter : LoadStateAdapter<MainLoadStateAdapter.LoadStateViewHolder>() {
    inner class LoadStateViewHolder(private val loadView: View) :
        RecyclerView.ViewHolder(loadView) {
        fun bind() {
            val progressBar = loadView.findViewById<ProgressBar>(R.id.progress_recycler)
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.load_state_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind()
    }

}