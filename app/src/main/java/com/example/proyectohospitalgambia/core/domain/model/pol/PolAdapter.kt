package com.example.proyectohospitalgambia.core.domain.model.pol

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.proyectohospitalgambia.R

/**
 * Clase PolAdapter.
 *
 * Esta clase es un adaptador personalizado para la lista de objetos Pol.
 *
 * @property pol La lista de objetos Pol.
 * @property context El contexto en el que se utiliza este adaptador.
 */
class PolAdapter(private val pol: List<Pol>, private val context: Context) : BaseAdapter() {

    /**
     * Método para obtener la vista de un elemento en la lista.
     *
     * @param position La posición del elemento en la lista.
     * @param convertView La vista reciclada.
     * @param parent El padre de la vista.
     * @return La vista del elemento en la lista.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.lista_pols, parent, false)
            holder = ViewHolder()
            holder.txtFechaPol = view.findViewById(R.id.txtFechaPol)
            holder.txtTipoPol = view.findViewById(R.id.txtTipoPol)
            holder.txtDatosPol = view.findViewById(R.id.txtDatosPol)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val currentPartida = pol[position]

        // Obtener la cadena JSON
        val jsonString = currentPartida.data

        // Encontrar la posición de "FechaInsercion"
        val indexFechaInsercion = jsonString.indexOf("\"FechaInsercion\"")

        // Obtener la subcadena a partir de la posición de "FechaInsercion" hasta el siguiente ","
        val fechaInsercion = if (indexFechaInsercion != -1) {
            val startIndex = jsonString.indexOf(
                ':',
                indexFechaInsercion
            ) + 2 // Sumar 3 para omitir ": " y comillas al principio
            val endIndex = jsonString.indexOf(',', startIndex)
            jsonString.substring(startIndex, endIndex).removeSurrounding("\"")
        } else {
            "Fecha de inserción no disponible"
        }

        // Encontrar la posición de "TipoPol"
        val indexTipoPol = jsonString.indexOf("\"TipoPol\"")

        // Obtener la subcadena a partir de la posición de "TipoPol" hasta el siguiente ","
        val tipoPol = if (indexTipoPol != -1) {
            val startIndex = jsonString.indexOf(
                ':',
                indexTipoPol
            ) + 2 // Sumar 3 para omitir ": " y comillas al principio
            val endIndex = jsonString.indexOf(',', startIndex)
            jsonString.substring(startIndex, endIndex).removeSurrounding("\"")
        } else {
            "Tipo de pol no disponible"
        }

        // Eliminar el campo "TipoPol" y su valor
        val jsonStringSinTipoPol = jsonString.replace("\"TipoPol\":\"[^\"]*\"[,]?".toRegex(), "")

        // Eliminar el campo "FechaInsercion" y su valor
        val jsonStringSinFechaInsercion =
            jsonStringSinTipoPol.replace("\"FechaInsercion\":\"[^\"]*\"[,]?".toRegex(), "")

        // Eliminar las llaves "{" y "}" del resultado
        val otrosDatos = jsonStringSinFechaInsercion.removePrefix("{").removeSuffix("}")

        holder.txtFechaPol.text = fechaInsercion.replace("\"", "")
        holder.txtTipoPol.text = tipoPol.replace("\"", "")
        holder.txtDatosPol.text = otrosDatos.replace("\"", "").replace(",", "\n")

        return view!!
    }

    /**
     * Método para obtener un elemento de la lista.
     *
     * @param position La posición del elemento en la lista.
     * @return El elemento en la posición especificada.
     */
    override fun getItem(position: Int): Any {
        return pol[position]
    }

    /**
     * Método para obtener el ID de un elemento de la lista.
     *
     * @param position La posición del elemento en la lista.
     * @return El ID del elemento en la posición especificada.
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * Método para obtener el número de elementos en la lista.
     *
     * @return El número de elementos en la lista.
     */
    override fun getCount(): Int {
        return pol.size
    }

    /**
     * Clase ViewHolder.
     *
     * Esta clase contiene las vistas que se van a utilizar en el adaptador.
     */
    private class ViewHolder {
        lateinit var txtFechaPol: TextView
        lateinit var txtTipoPol: TextView
        lateinit var txtDatosPol: TextView
    }
}