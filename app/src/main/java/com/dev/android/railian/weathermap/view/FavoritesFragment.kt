package com.dev.android.railian.weathermap.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.android.railian.weathermap.R
import com.dev.android.railian.weathermap.adapter.FavoritesAdapter
import com.dev.android.railian.weathermap.view_model.FavoritesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(), FavoritesView {
    override fun deleteLocationFromFavorites(id: Int) {
        viewModel.deleteSingleLocationFromFavorite(id)
    }

    private val viewModel: FavoritesFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoriteLocations()
        favoritesRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewModel.favoriteLocations().observe(this, Observer {
            if (it.isEmpty()) {
                showEmptyList()
            } else {
                if (favoritesRecyclerView.adapter == null) {
                    favoritesRecyclerView.adapter = FavoritesAdapter(it, this)
                } else {
                    (favoritesRecyclerView.adapter as FavoritesAdapter).updateList(it)
                }
                if (favoritesRecyclerView.visibility == View.GONE) {
                    emptyListLayout.visibility = View.GONE
                    favoritesRecyclerView.visibility = View.VISIBLE
                }
            }
        })
        /*viewModel.successDeleteLocations().observe(this, Observer {
            Snackbar.make(context, "successDeleteLocations", Snackbar.LENGTH_SHORT).show()
        })
        viewModel.successDeleteLocation().observe(this, Observer {
            Snackbar.make(context, "successDeleteLocation", Snackbar.LENGTH_SHORT).show()
        })
        viewModel.successBackupLocation().observe(this, Observer {
            Snackbar.make(context, "successBackupLocation", Snackbar.LENGTH_SHORT).show()
        })*/
        viewModel.favoritesIsEmpty().observe(this, Observer { isEmpty ->
            if (isEmpty) {
                showEmptyList()
            }
        })
    }

    private fun showEmptyList() {
        favoritesRecyclerView.visibility = View.GONE
        emptyListLayout.visibility = View.VISIBLE
    }
}
