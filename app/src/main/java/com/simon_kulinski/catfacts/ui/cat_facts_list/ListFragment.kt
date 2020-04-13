package com.simon_kulinski.catfacts.ui.cat_facts_list

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.simon_kulinski.catfacts.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.no_connection.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModel()

    private lateinit var adapter: ListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setUpRecyclerView()
        initDataAndSetObserver()
        catFactsListFragment_settingBtn.setOnClickListener {
            startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }

    private fun setUpRecyclerView() {
        adapter = ListAdapter()
        catFactsListFragment_recyclerView.adapter = adapter
    }

    private fun initDataAndSetObserver() {
        viewModel.liveDataListOfCatFactsResult.observe(
            viewLifecycleOwner, Observer {
                if (!it.hasError)
                    adapter.setUpDataSet(it.value!!)
                else Toast.makeText(
                    requireContext(),
                    it.error!!.message ?: "Unknown no_connection",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
        viewModel.progressBarLiveData.observe(viewLifecycleOwner, Observer {
            enabledProgressBar(it)
        })

        viewModel.isNetworkConnection.observe(viewLifecycleOwner, Observer {
            showNoInternetConnectionScreen(it)
        })
    }

    private fun enabledProgressBar(enabled: Boolean) {
        if (enabled) {
            requireActivity().progressBar.visibility = View.VISIBLE
        } else {
            requireActivity().progressBar.visibility = View.GONE
        }
    }

    private fun showNoInternetConnectionScreen(isConnected: Boolean) {
        if (isConnected) {
            catFactsListFragment_recyclerView.visibility = View.VISIBLE
            catFactDetailsFragment_noConnectionScreen.visibility = View.GONE
        } else {
            catFactsListFragment_recyclerView.visibility = View.GONE
            catFactDetailsFragment_noConnectionScreen.visibility = View.VISIBLE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_list_syn) {
            viewModel.getNewData()
        }
        return super.onOptionsItemSelected(item)
    }
}
