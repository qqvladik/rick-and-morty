package by.mankevich.rickandmorty.feature.episodes.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.characters.CharacterEntity
import by.mankevich.rickandmorty.domain.episodes.EpisodeEntity
import by.mankevich.rickandmorty.feature.base.UISupportService
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersAdapter
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersDiffUtilCallback

private const val ARG_EPISODE_ID = "episode_id"

class EpisodeDetailFragment : Fragment() {

    private lateinit var episode: EpisodeEntity
    private lateinit var textName: TextView
    private lateinit var textDate: TextView
    private lateinit var textSeason: TextView
    private lateinit var textEpisodeNum: TextView
    private lateinit var recyclerCharacters: RecyclerView

    private lateinit var charactersDiffUtilCallback: CharactersDiffUtilCallback
    private var charactersAdapter: CharactersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val episodeId: Int
        arguments?.let {
            episodeId = it.getInt(ARG_EPISODE_ID)
        }
        //todo load episode
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_episode_detail, container, false)

        initView(view)

        return view
    }

    private fun initView(view: View){
        textName = view.findViewById(R.id.text_episode_name)
        textDate = view.findViewById(R.id.text_date)
        textSeason = view.findViewById(R.id.text_season)
        textEpisodeNum = view.findViewById(R.id.text_episode_num)
        recyclerCharacters = view.findViewById(R.id.recycler_episode_characters)
        UISupportService.designRecyclerView(requireContext(), recyclerCharacters, 2)

        charactersAdapter = CharactersAdapter(emptyList()){
            UISupportService.showCharacterDetailFragment(parentFragmentManager, it.id)
        }
        charactersDiffUtilCallback = CharactersDiffUtilCallback(charactersAdapter!!.entitiesList, emptyList())
        recyclerCharacters.adapter = charactersAdapter
    }

    private fun updateRecyclerEpisodes(characters: List<CharacterEntity>) {
        UISupportService.updateRecyclerView(characters, charactersAdapter!!, charactersDiffUtilCallback)
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