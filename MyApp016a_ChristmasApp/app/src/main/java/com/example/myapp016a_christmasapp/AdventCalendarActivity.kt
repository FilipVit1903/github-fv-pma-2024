import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp016a_christmasapp.databinding.ActivityAdventCalendarBinding

class AdventCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdventCalendarBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: AdventCalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdventCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("AdventCalendar", MODE_PRIVATE)

        // Data o dnech a jejich stavu
        val adventDays = (1..24).toList()
        val openedDays = loadOpenedDays()

        adapter = AdventCalendarAdapter(adventDays, openedDays) { day ->
            onDayClicked(day)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this, 4)
        binding.recyclerView.adapter = adapter

        // Reset tlačítko
        binding.resetButton.setOnClickListener {
            resetCalendar()
        }
    }

    private fun onDayClicked(day: Int) {
        saveDayOpened(day)
        adapter.updateDayState(day, true)
        showWishDialog(day)
    }

    private fun showWishDialog(day: Int) {
        val wishDialog = WishDialogFragment.newInstance(day)
        wishDialog.show(supportFragmentManager, "WishDialog")
    }

    private fun loadOpenedDays(): MutableSet<Int> {
        val openedDays = sharedPreferences.getStringSet("opened_days", emptySet()) ?: emptySet()
        return openedDays.map { it.toInt() }.toMutableSet()
    }

    private fun saveDayOpened(day: Int) {
        val openedDays = loadOpenedDays()
        openedDays.add(day)
        sharedPreferences.edit().putStringSet("opened_days", openedDays.map { it.toString() }.toSet()).apply()
    }

    private fun resetCalendar() {
        sharedPreferences.edit().remove("opened_days").apply()
        adapter.resetDayStates()
    }
}

