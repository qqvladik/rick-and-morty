package by.mankevich.rickandmorty.feature.episodes.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.adapter.EpisodesAdapter
import by.mankevich.rickandmorty.feature.base.BaseFragment
import by.mankevich.rickandmorty.feature.base.UISupportService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodesListFragment : BaseFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var episodesPagingAdapter: EpisodesAdapter
    private val episodesListViewModel: EpisodesListViewModel by lazy {
        ViewModelProvider(this).get(EpisodesListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        episodesListViewModel.setIsConnect(isConnect())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        setHasOptionsMenu(true)
        initRecyclerView(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            episodesListViewModel.data.collectLatest {
                episodesPagingAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView(view: View) {
        episodesPagingAdapter = EpisodesAdapter{
            UISupportService.showEpisodeDetailFragment(parentFragmentManager, it.id)
        }
        recyclerView = view.findViewById(R.id.recycler_list)
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = episodesPagingAdapter
    }

    companion object {
        fun newInstance(): EpisodesListFragment {
            return EpisodesListFragment()
        }
    }
}