package by.mankevich.rickandmorty.feature.characters.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.library.repository.filter.FilterCharacters

class FilterCharactersFragment : Fragment() {

    private lateinit var spinnerStatus: Spinner
    private lateinit var spinnerSpecies: Spinner
    private lateinit var spinnerGender: Spinner
    private lateinit var editType: EditText
    private lateinit var buttonApply: Button
    private lateinit var buttonCancel: Button
    private val filterCharactersViewModel: FilterCharactersViewModel by lazy {
        ViewModelProvider(this).get(FilterCharactersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter_character, container, false)
        initView(view)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        return view
    }

    private fun initView(view: View) {
        spinnerStatus = view.findViewById(R.id.spinner_character_status)
        spinnerSpecies = view.findViewById(R.id.spinner_character_species)
        spinnerGender = view.findViewById(R.id.spinner_character_gender)
        editType = view.findViewById(R.id.filter_edit_character_type)
        buttonApply = view.findViewById(R.id.button_character_apply)
        buttonCancel = view.findViewById(R.id.button_character_cancel)

        buttonApply.setOnClickListener{
            onApplyClick()
        }

        buttonCancel.setOnClickListener{
            requireActivity().onBackPressed()
        }

        val adapterStatus = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            FilterCharacters.CHARACTERS_STATUS_LIST
        )
        val adapterSpecies = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            FilterCharacters.CHARACTERS_SPECIES_LIST
        )
        val adapterGender = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            FilterCharacters.CHARACTERS_GENDER_LIST
        )

        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterSpecies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerStatus.adapter = adapterStatus
        spinnerSpecies.adapter = adapterSpecies
        spinnerGender.adapter = adapterGender

        val filter = filterCharactersViewModel.filter
        setSelection(
            spinnerStatus,
            FilterCharacters.CHARACTERS_STATUS_LIST,
            filter.status
        )
        setSelection(
            spinnerSpecies,
            FilterCharacters.CHARACTERS_SPECIES_LIST,
            filter.species
        )
        setSelection(
            spinnerGender,
            FilterCharacters.CHARACTERS_GENDER_LIST,
            filter.gender
        )
        editType.setText(filter.type)
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
        val filter = filterCharactersViewModel.filter
        filter.status = correctText(spinnerStatus.selectedItem.toString())
        filter.species = correctText(spinnerSpecies.selectedItem.toString())
        filter.gender = correctText(spinnerGender.selectedItem.toString())
        filter.type = editType.text.toString()
        requireActivity().onBackPressed()
    }

    companion object {
        fun newInstance(): FilterCharactersFragment {
            return FilterCharactersFragment()
        }
    }

}