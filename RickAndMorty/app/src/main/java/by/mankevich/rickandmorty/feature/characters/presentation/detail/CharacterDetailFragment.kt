package by.mankevich.rickandmorty.feature.characters.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.episodes.EpisodeEntity
import by.mankevich.rickandmorty.feature.base.InitUpdateViewService
import by.mankevich.rickandmorty.feature.episodes.presentation.list.EpisodesAdapter
import by.mankevich.rickandmorty.feature.episodes.presentation.list.EpisodesDiffUtilCallback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CharacterDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var textName: TextView
    private lateinit var textStatus: TextView
    private lateinit var textType: TextView
    private lateinit var textGender: TextView
    private lateinit var imageCharacter: ImageView
    private lateinit var buttonOrigin: Button
    private lateinit var buttonLocation: Button
    private lateinit var recyclerEpisodes: RecyclerView

    private lateinit var episodesDiffUtilCallback: EpisodesDiffUtilCallback
    private var episodesAdapter: EpisodesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_character_detail, container, false)

        initView(view)

        return view
    }

    private fun initView(view: View){
        textName = view.findViewById(R.id.text_character_name)
        textStatus = view.findViewById(R.id.text_status)
        textType = view.findViewById(R.id.text_character_type)
        textGender = view.findViewById(R.id.text_gender)
        imageCharacter = view.findViewById(R.id.character_image)
        buttonOrigin = view.findViewById(R.id.button_origin)
        buttonLocation = view.findViewById(R.id.button_character_location)
        recyclerEpisodes = view.findViewById(R.id.recycler_character_episodes)
        InitUpdateViewService.getInstance().designRecyclerView(requireContext(), recyclerEpisodes, 2)

        episodesAdapter = EpisodesAdapter(emptyList())
        episodesDiffUtilCallback =
            EpisodesDiffUtilCallback(episodesAdapter!!.entitiesList, emptyList())

        recyclerEpisodes.adapter = episodesAdapter
    }

    private fun updateRecyclerEpisodes(
        episodes: List<EpisodeEntity>
    ) {
        InitUpdateViewService.getInstance()
            .updateRecyclerView(episodes, episodesAdapter!!, episodesDiffUtilCallback)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CharacterDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}