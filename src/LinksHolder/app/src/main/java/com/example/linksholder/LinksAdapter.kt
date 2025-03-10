package com.example.linksholder

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast


class LinksAdapter(
    context: Context,
    private val linksList: List<Pair<String, String>>
) : ArrayAdapter<Pair<String, String>>(context, R.layout.link_one, linksList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.link_one, parent, false)

        val shortName = view.findViewById<TextView>(R.id.short_link)
        val (name, link) = getItem(position)!!

        shortName.text = name

        shortName.setOnClickListener {
            openSafeLink(context, link)
        }

        return view
    }

    private fun openSafeLink(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            intent.setPackage("com.android.chrome")

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                intent.setPackage(null)
                context.startActivity(intent)
            }
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Error - browser didn't found", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error while opening file", Toast.LENGTH_SHORT).show()
        }
    }
}
