package com.lydone.okna_service_android_app.presentation.calculator.model

import android.app.Application
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ShapeFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.*

@RequiresApi(Build.VERSION_CODES.N)
@HiltViewModel
class ArMeasurementViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private var redModelRenderable: ModelRenderable? = null

    private var greenModelRenderable: ModelRenderable? = null

    private var corners = emptyList<AnchorNode>()

//    private var topLeftCornerNode: AnchorNode? = null
//
//    private var topRightCornerNode: AnchorNode? = null
//
//    private var bottomRightCornerNode: AnchorNode? = null
//
//    private val addCornerButtonTextResMutableLiveData = MutableLiveData(R.string.point_top_left_corner)
//    val addCornerButtonTextResLiveData: LiveData<Int> get() = addCornerButtonTextResMutableLiveData

    private val isAddCornerButtonVisibleMutableLiveData = MutableLiveData(true)
    val isAddCornerButtonVisibleLiveData: LiveData<Boolean> get() = isAddCornerButtonVisibleMutableLiveData

    init {
        MaterialFactory.makeTransparentWithColor(
            application.applicationContext,
            com.google.ar.sceneform.rendering.Color(Color.RED)
        ).thenAccept {
            redModelRenderable = ShapeFactory.makeSphere(0.01f, Vector3.zero(), it).apply {
                isShadowCaster = false
                isShadowReceiver = false
            }
        }
        MaterialFactory.makeTransparentWithColor(
            application.applicationContext,
            com.google.ar.sceneform.rendering.Color(Color.GREEN)
        ).thenAccept {
            greenModelRenderable = ShapeFactory.makeSphere(0.01f, Vector3.zero(), it).apply {
                isShadowCaster = false
                isShadowReceiver = false
            }
        }
    }

    fun onAddCornerNodeButtonClicked(pointerNode: AnchorNode) =
        AnchorNode(pointerNode.anchor).apply { renderable = greenModelRenderable }.also { cornerNode ->
            corners = corners + cornerNode
            isAddCornerButtonVisibleMutableLiveData.value = corners.size < MAX_CORNERS_COUNT
        }

    fun onRemovePreviousPointButtonClicked() = when (corners.size) {
        MAX_CORNERS_COUNT -> {
            val last = corners.last()
            isAddCornerButtonVisibleMutableLiveData.value = true
            corners = corners.dropLast(1)
            last
        }
        0 -> {
            null
        }
        else -> {
            val last = corners.last()
            corners = corners.dropLast(1)
            last
        }
    }

    fun createNode(hitResults: List<HitResult>) =
        if (hitResults.isNotEmpty()) {
            try {
                AnchorNode(hitResults.first().createAnchor()).apply {
                    renderable = redModelRenderable
                }
            } catch (e: Exception) {
                Log.e("AR", e.message, e)
                null
            }
        } else {
            null
        }

    fun calculateWindowWidth(): Int {
        var maxWidth = 0
        for (corner1 in corners) {
            val x1: Float
            val z1: Float
            requireNotNull(corner1.anchor?.pose).let { p1 ->
                x1 = p1.tx() * MILLIMETERS_IN_METER
                z1 = p1.tz() * MILLIMETERS_IN_METER
            }
            for (corner2 in corners) {
                val x2: Float
                val z2: Float
                requireNotNull(corner2.anchor?.pose).let { p2 ->
                    x2 = p2.tx() * MILLIMETERS_IN_METER
                    z2 = p2.tz() * MILLIMETERS_IN_METER
                }
                maxWidth = max(maxWidth, sqrt((x1 - x2).pow(2) + (z1 - z2).pow(2)).roundToInt())
            }
        }
        return maxWidth
    }

    fun calculateWindowHeight(): Int {
        var maxHeight = 0
        for (corner1 in corners) {
            val y1 = requireNotNull(corner1.anchor?.pose).ty() * MILLIMETERS_IN_METER
            for (corner2 in corners) {
                val y2 = requireNotNull(corner2.anchor?.pose).ty() * MILLIMETERS_IN_METER
                maxHeight = max(maxHeight, abs(y1 - y2).roundToInt())
            }
        }
        return maxHeight
    }

    private fun calculateDistanceBetweenAnchorNodes(n1: AnchorNode, n2: AnchorNode): Int {
        val p1 = requireNotNull(n1.anchor?.pose)
        val p2 = requireNotNull(n2.anchor?.pose)
        val x1 = p1.tx() * MILLIMETERS_IN_METER
        val x2 = p2.tx() * MILLIMETERS_IN_METER
        val y1 = p1.ty() * MILLIMETERS_IN_METER
        val y2 = p2.ty() * MILLIMETERS_IN_METER
        val z1 = p1.tz() * MILLIMETERS_IN_METER
        val z2 = p2.tz() * MILLIMETERS_IN_METER
        return sqrt((x1 - x2).pow(2) + (y1 - y2).pow(2) + (z1 - z2).pow(2)).roundToInt()
    }

    private companion object {
        private const val MILLIMETERS_IN_METER = 1000

        private const val MAX_CORNERS_COUNT = 4
    }
}