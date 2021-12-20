package com.proyecto.theroute

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var mPlaces: ArrayList<Place>
    private lateinit var mAdapter: PlacesAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.place_list)
        setupRecyclerView()
        generatePlaces()
    }
    /**
     * Sets up the RecyclerView: empty data set, item dividers, swipe to delete.
     */
    private fun setupRecyclerView() {
        mPlaces = arrayListOf()
        recycler.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        mAdapter = PlacesAdapter(mPlaces)
        recycler.adapter = mAdapter
    }

    /**
     * Generates mock contact data to populate the UI from a JSON file in the
     * assets directory, called from the options menu.
     */
    private fun generatePlaces() {
        val placesString = readPlaceJsonFile()
        try {
            val placesJson = JSONArray(placesString)
            for (i in 0 until placesJson.length()) {
                val placeJson = placesJson.getJSONObject(i)
                val place = Place(
                    placeJson.getString("namePlace"),
                    placeJson.getString("descriptionPlace"),
                    placeJson.getString("ratingPlace"),
                    placeJson.getString("photoPlaceUrl")
                )
                Log.d(TAG, "generatePlaces: $place")
                mPlaces.add(place)
            }

            mAdapter.notifyDataSetChanged()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * Reads a file from the assets directory and returns it as a string.
     *
     * @return The resulting string.
     */
    private fun readPlaceJsonFile(): String? {
        var placesString: String? = null
        try {
            val inputStream = assets.open("mock_places.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            placesString = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return placesString
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}