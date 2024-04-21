import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity

class menuMoodAndEnergyView : Fragment() {

    private lateinit var btnDatosMoodAndEnergy: ImageButton
    private lateinit var btnGraficaMoodAndEnergy: ImageButton
    private lateinit var tvUltimoDatoMoodAndEnergy: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuMoodAndEnergy = inflater.inflate(R.layout.fragment_menu_mood_and_energy, container, false)

        btnDatosMoodAndEnergy = menuMoodAndEnergy.findViewById(R.id.imgbtn_irADatosMoodAndEnergy)
        btnGraficaMoodAndEnergy = menuMoodAndEnergy.findViewById(R.id.imgbtn_irAGraficaMoodAndEnergy)
        tvUltimoDatoMoodAndEnergy = menuMoodAndEnergy.findViewById(R.id.tv_ultimoDatoMoodAndEnergy)

        btnDatosMoodAndEnergy.setOnClickListener {
            findNavController().navigate(R.id.action_menuMoodAndEnergyView_to_introducirMoodAndEnergy)
        }

        btnGraficaMoodAndEnergy.setOnClickListener {
            findNavController().navigate(R.id.action_menuMoodAndEnergyView_to_graficoMoodAndEnergyView)
        }





        return menuMoodAndEnergy
    }

    override fun onResume() {
        super.onResume()
        actualizarUltimoDatoMoodAndEnergy()
    }

    private fun actualizarUltimoDatoMoodAndEnergy() {
        MainActivity.usuario?.id?.let { idUsuario ->
            val ultimoDatoMoodAndEnergy = MainActivity.databaseHelper?.obtenerUltimoEstado(idUsuario)
            val fechaMedicion = ultimoDatoMoodAndEnergy?.fechaRealizacion ?: getString(R.string.txt_fecha_desconocida)

            val estadoAnimo = ultimoDatoMoodAndEnergy?.estadoAnimo?.toIntOrNull() ?: 0
            val energia = ultimoDatoMoodAndEnergy?.energia?.toIntOrNull() ?: 0

            val textoEstadoAnimo = "$estadoAnimo/6"
            val textoEnergia = "$energia/3"

            val textoFinal = "$textoEstadoAnimo\n$textoEnergia\n${getString(R.string.txt_fechaDeLaMedicion)}: $fechaMedicion\n"
            tvUltimoDatoMoodAndEnergy.text = textoFinal
        }
    }
}
