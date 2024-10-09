package com.holoo.map.utils

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.carto.styles.LineStyle
import com.carto.styles.LineStyleBuilder
import com.carto.styles.MarkerStyle
import com.carto.styles.MarkerStyleBuilder
import com.carto.utils.BitmapUtils
import com.holoo.map.utils.extension.toBitmap
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.model.Marker

object MapUtils {
    // This method gets a LatLng as input and adds a marker on that position
    fun createMarker(
        loc: LatLng,
        context: Context,
        @DrawableRes icon: Int = org.neshan.mapsdk.R.drawable.ic_cluster_marker_blue,
        iconSize: Float = 30f,
    ): Marker {
        val markStCr = MarkerStyleBuilder()
        markStCr.size = iconSize
        val drawable = ResourcesCompat.getDrawable(
            context.resources,
            icon,
            null
        )
        val bitmap = drawable!!.toBitmap()
        markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(bitmap)
        markStCr.setAnchorPoint(0f, 0f)
        // AnimationStyle object - that was created before - is used here
        val markSt: MarkerStyle = markStCr.buildStyle()

        // Creating marker
        return Marker(loc, markSt)
    }

    fun getLineStyle(): LineStyle? {
        val lineStCr = LineStyleBuilder()
        lineStCr.color =
            com.carto.graphics.Color(2.toShort(), 119.toShort(), 189.toShort(), 190.toShort())
        lineStCr.width = 4f
        lineStCr.stretchFactor = 0f
        return lineStCr.buildStyle()
    }
}