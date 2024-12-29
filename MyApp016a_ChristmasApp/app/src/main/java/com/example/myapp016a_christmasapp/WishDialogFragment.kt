import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class WishDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val day = arguments?.getInt(ARG_DAY) ?: 0
        val wish = getWishForDay(day)

        return AlertDialog.Builder(requireContext())
            .setTitle("Přání na den $day")
            .setMessage(wish)
            .setPositiveButton("Zavřít") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    private fun getWishForDay(day: Int): String {
        val wishes = listOf(
            "Krásný první prosincový den! 🎅",
            "Dnes je čas na horkou čokoládu ☕",
            "Nezapomeň na dárky! 🎁",
            "Den na rozsvícení stromečku 🎄",
            "🎶 Vánoční nálada je tady 🎶"
            // Přidej více přání
        )
        return wishes.getOrNull(day - 1) ?: "Šťastné a veselé Vánoce! 🌟"
    }

    companion object {
        private const val ARG_DAY = "day"

        fun newInstance(day: Int): WishDialogFragment {
            val fragment = WishDialogFragment()
            val args = Bundle()
            args.putInt(ARG_DAY, day)
            fragment.arguments = args
            return fragment
        }
    }
}
