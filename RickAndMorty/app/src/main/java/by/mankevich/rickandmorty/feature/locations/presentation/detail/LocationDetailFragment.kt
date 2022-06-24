package by.mankevich.rickandmorty.feature.locations.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.characters.CharacterEntity
import by.mankevich.rickandmorty.domain.characters.Location
import by.mankevich.rickandmorty.domain.locations.LocationEntity
import by.mankevich.rickandmorty.feature.base.UISupportService
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersAdapter
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersDiffUtilCallback

private const val ARG_LOCATION_ID = "location_id"

class LocationDetailFragment : Fragment() {

    private lateinit var location: Location
    private lateinit var textName: TextView
    private lateinit var textType: TextView
    private lateinit var textDimension: TextView
    private lateinit var recyclerResidents: RecyclerView

    private lateinit var charactersDiffUtilCallback: CharactersDiffUtilCallback
    private var charactersAdapter: CharactersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationId: Int
        arguments?.let {
            locationId = it.getInt(ARG_LOCATION_ID)
        }
        //todo load location
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_location_detail, container, false)

        initView(view)

        return view
    }

    private fun initView(view: View){
        textName = view.findViewById(R.id.text_episode_name)
        textType = view.findViewById(R.id.text_location_type)
        textDimension = view.findViewById(R.id.text_dimension)
        recyclerResidents = view.findViewById(R.id.recycler_location_residents)
        UISupportService.designRecyclerView(requireContext(), recyclerResidents, 2)

        charactersAdapter = CharactersAdapter(emptyList()){
            UISupportService.showCharacterDetailFragment(parentFragmentManager, it.id)
        }
        charactersDiffUtilCallback = CharactersDiffUtilCallback(charactersAdapter!!.entitiesList, emptyList())
        recyclerResidents.adapter = charactersAdapter
    }

    private fun updateRecyclerEpisodes(characters: List<CharacterEntity>) {
        UISupportService.updateRecyclerView(characters, charactersAdapter!!, charactersDiffUtilCallback)
    }

    companion object {
        @JvmStatic
        fun newInstance(locationId: Int) =
            LocationDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_LOCATION_ID, locationId)
                }
            }
    }
}