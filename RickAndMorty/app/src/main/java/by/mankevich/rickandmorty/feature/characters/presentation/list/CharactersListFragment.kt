package by.mankevich.rickandmorty.feature.characters.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.characters.CharacterEntity
import by.mankevich.rickandmorty.feature.base.UISupportService
import by.mankevich.rickandmorty.feature.characters.presentation.detail.CharacterDetailFragment

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
        charactersAdapter = CharactersAdapter(emptyList()){
            UISupportService.showCharacterDetailFragment(parentFragmentManager, it.id)
            /*val characterDetailFragment = CharacterDetailFragment.newInstance(it)
            UISupportService.getInstance().showDetailFragment(parentFragmentManager, characterDetailFragment)
            parentFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, characterDetailFragment)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                addToBackStack(null)
                commit()
            }*/
        }
        charactersDiffUtilCallback =
            CharactersDiffUtilCallback(charactersAdapter!!.entitiesList, emptyList())
        recyclerView = view.findViewById(R.id.recycler_list)
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = charactersAdapter
    }

    private fun updateUI(characters: List<CharacterEntity>) {
        UISupportService.updateRecyclerView(characters, charactersAdapter!!, charactersDiffUtilCallback)
    }

    companion object {
        fun newInstance(): CharactersListFragment {
            return CharactersListFragment()
        }
    }
}