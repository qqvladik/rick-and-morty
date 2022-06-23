package by.mankevich.rickandmorty.feature.characters.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.characters.CharacterEntity
import by.mankevich.rickandmorty.domain.locations.LocationEntity
import by.mankevich.rickandmorty.feature.base.InitUpdateViewService
import by.mankevich.rickandmorty.feature.locations.presentation.list.LocationsAdapter
import by.mankevich.rickandmorty.feature.locations.presentation.list.LocationsDiffUtilCallback

class CharactersListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var charactersDiffUtilCallback: CharactersDiffUtilCallback
    private var charactersAdapter: CharactersAdapter? = null

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
        charactersAdapter = CharactersAdapter(emptyList())
        charactersDiffUtilCallback =
            CharactersDiffUtilCallback(charactersAdapter!!.entitiesList, emptyList())

        recyclerView = view.findViewById(R.id.recycler_list)

        InitUpdateViewService.getInstance().designRecyclerView(requireContext(), recyclerView, 2)

        recyclerView.adapter = charactersAdapter
    }

    private fun updateUI(characters: List<CharacterEntity>) {
        InitUpdateViewService.getInstance()
            .updateRecyclerView(characters, charactersAdapter!!, charactersDiffUtilCallback)
    }

    companion object {
        fun newInstance(): CharactersListFragment {
            return CharactersListFragment()
        }
    }
}