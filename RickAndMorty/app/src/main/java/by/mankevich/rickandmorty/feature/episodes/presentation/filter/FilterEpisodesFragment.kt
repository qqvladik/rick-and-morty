package by.mankevich.rickandmorty.feature.episodes.presentation.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.characters.presentation.filter.FilterCharactersViewModel
import by.mankevich.rickandmorty.library.repository.filter.FilterCharacters
import by.mankevich.rickandmorty.library.repository.filter.FilterEpisodes

class FilterEpisodesFragment : Fragment() {

    private lateinit var spinnerSeason: Spinner
    private lateinit var editEpisode: EditText
    private lateinit var buttonApply: Button
    private lateinit var buttonCancel: Button
    private val filterEpisodesViewModel: FilterEpisodesViewModel by lazy {
        ViewModelProvider(this).get(FilterEpisodesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter_episodes, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        spinnerSeason = view.findViewById(R.id.spinner_episode_season)
        editEpisode = view.findViewById(R.id.filter_edit_episode_num)
        buttonApply = view.findViewById(R.id.button_episode_apply)
        buttonCancel = view.findViewById(R.id.button_episode_cancel)

        buttonApply.setOnClickListener{
            onApplyClick()
        }

        buttonCancel.setOnClickListener{
            requireActivity().onBackPressed()
        }

        val adapterSeasons = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            FilterEpisodes.SEASONS_LIST
        )
        adapterSeasons.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSeason.adapter = adapterSeasons
        val filter = filterEpisodesViewModel.filter
        setSelection(
            spinnerSeason,
            FilterEpisodes.SEASONS_LIST,
            filter.season
        )

        editEpisode.setText(filter.episodeNum)
    }

    private fun setSelection(spinner: Spinner, textList: List<String>, text: String) {
        spinner.setSelection(
            textList.indexOf(
                correctText(
                    text
                )
            )
        )
    }

    private fun correctText(text: String): String {
        return if (text == "any") {
            ""
        } else {
            text
        }
    }

    private fun onApplyClick(){
        val filter = filterEpisodesViewModel.filter
        filter.season = correctText(spinnerSeason.selectedItem.toString())
        var episodeNum: String = editEpisode.text.toString()
        if(episodeNum.length==1&&episodeNum!="0"&&episodeNum!="1"){
            episodeNum = "0".plus(episodeNum)
        }
        filter.episodeNum = correctText(episodeNum)
        requireActivity().onBackPressed()
    }


    companion object {
        fun newInstance() =
            FilterEpisodesFragment()
    }
}