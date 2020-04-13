package com.simon_kulinski.catfacts.ui.cat_fact_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.simon_kulinski.catfacts.R
import com.simon_kulinski.catfacts.domain.models.CatFact
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_screen.*
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment : Fragment() {

    private lateinit var id: String
    private val viewModel: DetailsViewModel by viewModel { parametersOf(id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getString(ID_KEY) ?: ""
        initDataAndSetObservers()
    }

    private fun initDataAndSetObservers() {
        viewModel.liveDataDetailsCatFact.observe(
            viewLifecycleOwner,
            Observer {
                if (!it.hasError)
                    bind(it.value!!)
                else showError(it.error!!)
            }
        )
        viewModel.progressBarLiveData.observe(viewLifecycleOwner, Observer {
            enabledProgressBar(it)
        })
        viewModel.initData()
    }

    private fun bind(catFact: CatFact) {
        catFactDetailsFragment_text.text = catFact.text
        catFactDetailsFragment_updated.text = catFact.getUpdateTimeAsFormattedString(
            SimpleDateFormat(
                "kk:mm dd/MM/yyy",
                Locale.getDefault()
            )
        )
    }

    private fun showError(exception: Exception) {
        val message = exception.message ?: getString(R.string.unknown_error)
        catFactDetailsFragment_cardView.visibility = View.GONE
        catFactDetailsFragment_errorScreen.visibility = View.VISIBLE
        errorScreen_message.text = message
    }

    private fun enabledProgressBar(enabled: Boolean) {
        if (enabled) {
            requireActivity().progressBar.visibility = View.VISIBLE
        } else {
            requireActivity().progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ID_KEY = "id_key"
    }

}
