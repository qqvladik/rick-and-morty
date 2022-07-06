package by.mankevich.rickandmorty.feature.characters.presentation.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var charactersPagingAdapter: CharactersAdapter
    private val charactersListViewModel: CharactersListViewModel by lazy {
        ViewModelProvider(this).get(CharactersListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: CharactersListFragment")
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        setHasOptionsMenu(true)
        initRecyclerView(view)
        initSwipeRefresh(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charactersListViewModel.setIsConnect(isConnect())
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_filter -> {
                UISupportService.showFilterCharactersFragment(requireActivity().supportFragmentManager)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: CharactersListFragment")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: CharactersListFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: CharactersListFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroy: CharactersListFragment")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: CharactersListFragment")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: CharactersListFragment")
    }

    private fun initRecyclerView(view: View) {
        charactersPagingAdapter = CharactersAdapter{
            UISupportService.showCharacterDetailFragment(requireActivity().supportFragmentManager, it.id)
        }
        recyclerView = view.findViewById(R.id.recycler_list)
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = charactersPagingAdapter.withLoadStateFooter(MainLoadStateAdapter())
    }

    private fun initSwipeRefresh(view: View){
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            charactersListViewModel.setIsConnect(isConnect())
            charactersPagingAdapter.refresh()
            swipeRefresh.isRefreshing = false
        }
    }

    companion object {
        fun newInstance(): CharactersListFragment {
            return CharactersListFragment()
        }
    }
}