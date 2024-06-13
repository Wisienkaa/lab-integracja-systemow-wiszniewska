import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mojapka.amw_prime.R
import com.mojapka.amw_prime.databinding.FragmentDepartmentsBinding
import com.mojapka.amw_prime.DepartmentActivity
import android.widget.LinearLayout
import com.mojapka.amw_prime.databinding.FragmentContactBinding
import com.mojapka.amw_prime.databinding.FragmentDepartmentsBinding.*


class DepartmentsFragment : Fragment() {
    private var _binding: FragmentDepartmentsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        val view = binding.root
        val imageView = binding.ivLogo

        // Rozpocznij animację pojawiania się
        imageView.visibility = View.VISIBLE
        imageView.animate().alpha(1.0f).setDuration(1000).start()

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val departments = listOf(
            "Wydział Dowodzenia i Operacji Morskich" to R.string.opis_wydzial_dowodzenia,
            "Wydział Mechaniczno-Elektryczny" to R.string.opis_wydzial_mechaniczno_elektryczny,
            "Wydział Nauk Humanistycznych i Społecznych" to R.string.opis_wydzial_humanistyczny,
            "Wydział Nawigacji i Uzbrojenia Okrętowego" to R.string.opis_wydzial_nawigacji
        )

        val departmentsContainer = view.findViewById<LinearLayout>(R.id.departments_container)

        for ((departmentName, descriptionResource) in departments) {
            val departmentTextView = TextView(requireContext())
            departmentTextView.text = departmentName
            departmentTextView.setOnClickListener {
                val description = getString(descriptionResource)
                val intent = Intent(requireContext(), DepartmentActivity::class.java).apply {
                    putExtra("departmentName", departmentName)
                    putExtra("description", description)
                }
                startActivity(intent)
            }
            departmentsContainer.addView(departmentTextView)
        }
    }
}