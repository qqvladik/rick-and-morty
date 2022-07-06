package by.mankevich.rickandmorty.feature.locations.presentation.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.mankevich.rickandmorty.R

class FilterLocationsFragment : Fragment() {

    private lateinit var editType: EditText
    private lateinit var editDimension: EditText
    private lateinit var buttonApply: Button
    private lateinit var buttonCancel: Button
    private val filterLocationsViewModel: FilterLocationsViewModel by lazy {
        ViewModelProvider(this).get(FilterLocationsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter_locations, container, false)
        initView(view)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        return view
    }

    private fun initView(view: View) {
        editType = view.findViewById(R.id.filter_edit_location_type)
        editDimension = view.findViewById(R.id.filter_edit_location_dimension)
        buttonApply = view.findViewById(R.id.button_location_apply)
        buttonCancel = view.findViewById(R.id.button_location_cancel)

        buttonApply.setOnClickListener{
            onApplyClick()
        }

        buttonCancel.setOnClickListener{
            requireActivity().onBackPressed()
        }

        val filter = filterLocationsViewModel.filter
        editType.setText(filter.type)
        editDimension.setText(filter.dimension)
    }

    private fun onApplyClick(){
        val filter = filterLocationsViewModel.filter
        filter.type = editType.text.toString()
        filter.dimension = editDimension.text.toString()
        requireActivity().onBackPressed()
    }

    companion object {
        fun newInstance() =
            FilterLocationsFragment()
    }
}