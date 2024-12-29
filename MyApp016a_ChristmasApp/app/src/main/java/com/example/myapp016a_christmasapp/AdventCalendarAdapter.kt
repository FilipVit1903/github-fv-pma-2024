import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp016a_christmasapp.databinding.ItemDayBinding

class AdventCalendarAdapter(
    private val days: List<Int>,
    private val openedDays: MutableSet<Int>,
    private val onDayClick: (Int) -> Unit
) : RecyclerView.Adapter<AdventCalendarAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]
        val isOpened = openedDays.contains(day)
        holder.bind(day, isOpened, onDayClick)
    }

    override fun getItemCount() = days.size

    fun updateDayState(day: Int, isOpened: Boolean) {
        if (isOpened) openedDays.add(day)
        notifyDataSetChanged()
    }

    fun resetDayStates() {
        openedDays.clear()
        notifyDataSetChanged()
    }

    class DayViewHolder(private val binding: ItemDayBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: Int, isOpened: Boolean, onDayClick: (Int) -> Unit) {
            binding.dayTextView.text = day.toString()
            binding.dayTextView.alpha = if (isOpened) 0.5f else 1f
            binding.root.setOnClickListener {
                if (!isOpened) onDayClick(day)
            }
        }
    }
}
