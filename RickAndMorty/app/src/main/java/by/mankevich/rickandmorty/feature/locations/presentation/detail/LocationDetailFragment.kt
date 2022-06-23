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
import by.mankevich.rickandmorty.feature.base.InitUpdateViewService
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersAdapter
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersDiffUtilCallback

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocationDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var textName: TextView
    private lateinit var textType: TextView
    private lateinit var textDimension: TextView
    private lateinit var recyclerResidents: RecyclerView

    private lateinit var charactersDiffUtilCallback: CharactersDiffUtilCallback
    private var charactersAdapter: CharactersAdapter? = null

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

        val view = inflater.inflate(R.layout.fragment_location_detail, container, false)

        initView(view)

        return view
    }

    private fun initView(view: View){
        textName = view.findViewById(R.id.text_episode_name)
        textType = view.findViewById(R.id.text_location_type)
        textDimension = view.findViewById(R.id.text_dimension)
        recyclerResidents = view.findViewById(R.id.recycler_location_residents)
        InitUpdateViewService.getInstance().designRecyclerView(requireContext(), recyclerResidents, 2)

        charactersAdapter = CharactersAdapter(emptyList())
        charactersDiffUtilCallback =
            CharactersDiffUtilCallback(charactersAdapter!!.entitiesList, emptyList())
        recyclerResidents.adapter = charactersAdapter
    }

    private fun updateRecyclerEpisodes(
        characters: List<CharacterEntity>
    ) {
        InitUpdateViewService.getInstance()
            .updateRecyclerView(characters, charactersAdapter!!, charactersDiffUtilCallback)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LocationDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LocationDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}