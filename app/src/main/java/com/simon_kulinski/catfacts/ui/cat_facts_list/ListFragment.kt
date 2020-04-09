package com.simon_kulinski.catfacts.ui.cat_facts_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.simon_kulinski.catfacts.R
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModel()
    private val navController by lazy { findNavController() }
    private lateinit var adapter: ListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        setUpRecyclerView()
        initDataAndSetObserver()
    }

    private fun setUpRecyclerView() {
        adapter = ListAdapter()
        catFactsListFragment_recyclerView.adapter = adapter
    }

    private fun initDataAndSetObserver() {
        viewModel.initData()
        viewModel.liveDataListOfCatFactsResult.observe(
            viewLifecycleOwner, Observer {
                hideProgressBar()
                if (!it.hasError)
                    adapter.setUpDataSet(it.value!!)
                else Toast.makeText(
                    requireContext(),
                    it.error!!.message ?: "Unknown error",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

    private fun showProgressBar() {
        catFactListsFragment_progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        catFactListsFragment_progressBar.visibility = View.GONE
    }

}
