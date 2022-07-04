package by.mankevich.rickandmorty.feature.episodes.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.adapter.CharactersAdapter
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.feature.base.UISupportService
import by.mankevich.rickandmorty.feature.adapter.CharactersDiffUtilCallback
import by.mankevich.rickandmorty.feature.base.BaseFragment
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity

private const val ARG_EPISODE_ID = "episode_id"

class EpisodeDetailFragment : BaseFragment() {

    private lateinit var textName: TextView
    private lateinit var textDate: TextView
    private lateinit var textSeason: TextView
    private lateinit var textEpisodeNum: TextView
    private lateinit var textNetwork: TextView
    private lateinit var recyclerCharacters: RecyclerView

    private lateinit var charactersDiffUtilCallback: CharactersDiffUtilCallback
    private var charactersAdapter: CharactersAdapter? = null

    private val episodeDetailViewModel: EpisodeDetailViewModel by lazy {
        ViewModelProvider(this).get(EpisodeDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val episodeId: Int = arguments?.getInt(ARG_EPISODE_ID) as Int
        episodeDetailViewModel.loadEpisodeFull(episodeId)
        episodeDetailViewModel.setIsConnect(isConnect())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_episode_detail, container, false)
        initView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodeDetailViewModel.episodeLiveData.observe(viewLifecycleOwner) { episode ->
            episode?.let {
                updateUI(episode)
            }
        }
        episodeDetailViewModel.charactersLiveData.observe(viewLifecycleOwner) { characters ->
            updateRecyclerCharacters(characters)
        }
    }

    private fun initView(view: View) {
        textName = view.findViewById(R.id.text_episode_name)
        textDate = view.findViewById(R.id.text_date)
        textSeason = view.findViewById(R.id.text_season)
        textEpisodeNum = view.findViewById(R.id.text_episode_num)
        textNetwork = view.findViewById(R.id.text_episode_network_unavailable)
        recyclerCharacters = view.findViewById(R.id.recycler_episode_characters)
        UISupportService.designRecyclerView(requireContext(), recyclerCharacters, 2)

        charactersAdapter = CharactersAdapter(emptyList()) {
            UISupportService.showCharacterDetailFragment(parentFragmentManager, it.id)
        }
        charactersDiffUtilCallback =
            CharactersDiffUtilCallback(charactersAdapter!!.entitiesList!!, emptyList())
        recyclerCharacters.adapter = charactersAdapter
    }

    private fun updateUI(episode: EpisodeEntity) {
        textName.text = episode.name
        textDate.text = episode.airDate
        textSeason.text = episode.getSeasonNum()
        textEpisodeNum.text = episode.getEpisodeNum()
        textNetwork.isGone=isConnect()
    }

    private fun updateRecyclerCharacters(characters: List<CharacterEntity>) {
        UISupportService.updateRecyclerView(
            characters,
            charactersAdapter!!,
            charactersDiffUtilCallback
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(episodeId: Int) =
            EpisodeDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_EPISODE_ID, episodeId)
                }
            }
    }
}