package by.mankevich.rickandmorty.feature.characters.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.library.db.CharacterEntity
import by.mankevich.rickandmorty.feature.base.UISupportService

class CharactersListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var charactersDiffUtilCallback: CharactersDiffUtilCallback
    private var charactersAdapter: CharactersAdapter? = null
    private val charactersListViewModel: CharactersListViewModel by lazy {
        ViewModelProvider(this).get(CharactersListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersListViewModel.loadCharacters()
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
        charactersListViewModel.charactersLiveData.observe(
            viewLifecycleOwner
        ) { characters ->
            characters?.let {
                updateUI(characters)
            }
        }
    }

    private fun initRecyclerView(view: View) {
        charactersAdapter = CharactersAdapter(emptyList()) {
            UISupportService.showCharacterDetailFragment(parentFragmentManager, it.id)
        }
        charactersDiffUtilCallback =
            CharactersDiffUtilCallback(charactersAdapter!!.entitiesList, emptyList())
        recyclerView = view.findViewById(R.id.recycler_list)
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = charactersAdapter
    }

    private fun updateUI(characters: List<CharacterEntity>) {
        UISupportService.updateRecyclerView(
            characters,
            charactersAdapter!!,
            charactersDiffUtilCallback
        )
    }

    companion object {
        fun newInstance(): CharactersListFragment {
            return CharactersListFragment()
        }
    }
}