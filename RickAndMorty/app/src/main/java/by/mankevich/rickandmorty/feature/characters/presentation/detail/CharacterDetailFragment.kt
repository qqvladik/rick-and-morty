package by.mankevich.rickandmorty.feature.characters.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.feature.base.UISupportService
import by.mankevich.rickandmorty.feature.adapter.EpisodesAdapter
import by.mankevich.rickandmorty.feature.adapter.EpisodesDiffUtilCallback
import by.mankevich.rickandmorty.feature.base.BaseFragment
import com.squareup.picasso.Picasso

private const val ARG_CHARACTER_ID = "character_id"

class CharacterDetailFragment : BaseFragment() {

    private lateinit var textName: TextView
    private lateinit var textStatus: TextView
    private lateinit var textType: TextView
    private lateinit var textGender: TextView
    private lateinit var textNetwork: TextView
    private lateinit var imageCharacter: ImageView
    private lateinit var buttonOrigin: Button
    private lateinit var buttonLocation: Button
    private lateinit var recyclerEpisodes: RecyclerView

    private lateinit var episodesDiffUtilCallback: EpisodesDiffUtilCallback
    private var episodesAdapter: EpisodesAdapter? = null

    private val characterDetailViewModel: CharacterDetailViewModel by lazy {
        ViewModelProvider(this).get(CharacterDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val characterId: Int = arguments?.getInt(ARG_CHARACTER_ID) as Int
        characterDetailViewModel.loadCharacterFull(characterId)
        characterDetailViewModel.setIsConnect(isConnect())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_character_detail, container, false)

        initView(view)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun initView(view: View) {
        textName = view.findViewById(R.id.text_character_name)
        textStatus = view.findViewById(R.id.text_status)
        textType = view.findViewById(R.id.text_character_type)
        textGender = view.findViewById(R.id.text_gender)
        imageCharacter = view.findViewById(R.id.character_image)
        buttonOrigin = view.findViewById(R.id.button_origin)
        buttonLocation = view.findViewById(R.id.button_character_location)
        recyclerEpisodes = view.findViewById(R.id.recycler_character_episodes)
        textNetwork = view.findViewById(R.id.text_character_network_unavailable)

        UISupportService.designRecyclerView(requireContext(), recyclerEpisodes, 1)
        episodesAdapter = EpisodesAdapter(emptyList()) {
            UISupportService.showEpisodeDetailFragment(parentFragmentManager, it.id)
        }
        episodesDiffUtilCallback =
            EpisodesDiffUtilCallback(episodesAdapter!!.entitiesList!!, emptyList())
        recyclerEpisodes.adapter = episodesAdapter
    }

    private fun observeData() {
        characterDetailViewModel.characterLiveData.observe(
            viewLifecycleOwner
        ) { character ->
            character?.let {
                updateUI(character)
            }
        }
        characterDetailViewModel.episodesLiveData.observe(viewLifecycleOwner) { episodes ->
            updateRecyclerEpisodes(episodes)
        }
    }

    private fun updateUI(character: CharacterEntity) {
        textName.text = character.name
        textStatus.text = character.getStatusAndSpecies()
        textType.text = character.type
        textGender.text = character.gender
        textNetwork.isGone=isConnect()
        Picasso.get()
            .load(character.image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .fit()
            .into(imageCharacter)

        buttonOrigin.text = character.origin.name
        buttonLocation.text = character.location.name
        buttonOrigin.setOnClickListener {
            if (characterDetailViewModel.isOriginAvailable) {
                UISupportService.showLocationDetailFragment(
                    parentFragmentManager,
                    character.origin.id
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Origin unavailable because it is not cashed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        buttonLocation.setOnClickListener {
            if (characterDetailViewModel.isLocationAvailable) {
                UISupportService.showLocationDetailFragment(
                    parentFragmentManager,
                    character.location.id
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Location unavailable because it is not cashed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun updateRecyclerEpisodes(episodes: List<EpisodeEntity>) {
        UISupportService.updateRecyclerView(episodes, episodesAdapter!!, episodesDiffUtilCallback)
    }

    companion object {
        @JvmStatic
        fun newInstance(characterId: Int) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CHARACTER_ID, characterId)
                }
            }
    }
}