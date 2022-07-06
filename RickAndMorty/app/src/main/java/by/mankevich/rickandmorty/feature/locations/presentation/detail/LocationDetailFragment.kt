package by.mankevich.rickandmorty.feature.locations.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.adapter.CharactersAdapter
import by.mankevich.rickandmorty.library.db.entity.LocationEntity
import by.mankevich.rickandmorty.feature.base.UISupportService
import by.mankevich.rickandmorty.feature.adapter.CharactersDiffUtilCallback
import by.mankevich.rickandmorty.feature.base.BaseFragment
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity

private const val ARG_LOCATION_ID = "location_id"

class LocationDetailFragment : BaseFragment() {

    private lateinit var textName: TextView
    private lateinit var textType: TextView
    private lateinit var textDimension: TextView
    private lateinit var textNetwork: TextView
    private lateinit var recyclerResidents: RecyclerView
    private val locationDetailViewModel: LocationDetailViewModel by lazy {
        ViewModelProvider(this).get(LocationDetailViewModel::class.java)
    }

    private lateinit var charactersDiffUtilCallback: CharactersDiffUtilCallback
    private var charactersAdapter: CharactersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationId: Int = arguments?.getInt(ARG_LOCATION_ID) as Int
        locationDetailViewModel.loadLocation(locationId)
        locationDetailViewModel.setIsConnect(isConnect())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_location_detail, container, false)
        initView(view)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationDetailViewModel.locationLiveData.observe(viewLifecycleOwner) { location ->
            location?.let {
                updateUI(location)
            }
        }
        locationDetailViewModel.charactersLiveData.observe(viewLifecycleOwner) { characters ->
            updateRecyclerCharacters(characters)
        }
    }

    private fun initView(view: View) {
        textName = view.findViewById(R.id.text_location_name)
        textType = view.findViewById(R.id.text_location_type)
        textDimension = view.findViewById(R.id.text_dimension)
        textNetwork = view.findViewById(R.id.text_location_network_unavailable)
        recyclerResidents = view.findViewById(R.id.recycler_location_residents)
        UISupportService.designRecyclerView(requireContext(), recyclerResidents, 2)

        charactersAdapter = CharactersAdapter(emptyList()) {
            UISupportService.showCharacterDetailFragment(parentFragmentManager, it.id)
        }
        charactersDiffUtilCallback =
            CharactersDiffUtilCallback(charactersAdapter!!.entitiesList!!, emptyList())
        recyclerResidents.adapter = charactersAdapter
    }

    private fun updateUI(location: LocationEntity) {
        textName.text = location.name
        textType.text = location.type
        textDimension.text = location.dimension
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
        fun newInstance(locationId: Int) =
            LocationDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_LOCATION_ID, locationId)
                }
            }
    }
}