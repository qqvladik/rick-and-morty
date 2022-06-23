package by.mankevich.rickandmorty.feature.episodes.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.characters.CharacterEntity
import by.mankevich.rickandmorty.domain.episodes.EpisodeEntity
import by.mankevich.rickandmorty.feature.base.InitUpdateViewService
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersAdapter
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersDiffUtilCallback
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersListFragment

class EpisodesListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var episodesDiffUtilCallback: EpisodesDiffUtilCallback
    private var episodesAdapter: EpisodesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        setHasOptionsMenu(true)
        initRecyclerView(view)

        return view
    }

    private fun initRecyclerView(view: View) {
        episodesAdapter = EpisodesAdapter(emptyList())
        episodesDiffUtilCallback =
            EpisodesDiffUtilCallback(episodesAdapter!!.entitiesList, emptyList())

        recyclerView = view.findViewById(R.id.recycler_list)

        InitUpdateViewService.getInstance().designRecyclerView(requireContext(), recyclerView, 2)

        recyclerView.adapter = episodesAdapter
    }

    private fun updateUI(episodes: List<EpisodeEntity>) {
        InitUpdateViewService.getInstance()
            .updateRecyclerView(episodes, episodesAdapter!!, episodesDiffUtilCallback)
    }

    companion object {
        fun newInstance(): CharactersListFragment {
            return CharactersListFragment()
        }
    }
}