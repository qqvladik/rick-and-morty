package by.mankevich.rickandmorty.feature.characters.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.adapter.CharactersAdapter
import by.mankevich.rickandmorty.feature.base.UISupportService
import by.mankevich.rickandmorty.feature.adapter.MainLoadStateAdapter
import by.mankevich.rickandmorty.feature.base.BaseFragment
import by.mankevich.rickandmorty.library.repository.filter.FilterCharacters
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "RAMCharactersFragment"

class CharactersListFragment : BaseFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var charactersPagingAdapter: CharactersAdapter
    private val charactersListViewModel: CharactersListViewModel by lazy {
        ViewModelProvider(this).get(CharactersListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersListViewModel.setIsConnect(isConnect())
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
        lifecycleScope.launch {
            charactersListViewModel.data.collectLatest {
                charactersPagingAdapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_list_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextChange: $queryText")
                    charactersListViewModel.onSearchChanged(queryText)
                    return true
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: CharactersListFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: CharactersListFragment")
    }

    private fun initRecyclerView(view: View) {
        charactersPagingAdapter = CharactersAdapter{
            UISupportService.showCharacterDetailFragment(requireActivity().supportFragmentManager, it.id)
        }
        recyclerView = view.findViewById(R.id.recycler_list)
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = charactersPagingAdapter.withLoadStateFooter(MainLoadStateAdapter())
    }

    companion object {
        fun newInstance(): CharactersListFragment {
            return CharactersListFragment()
        }
    }
}