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
import com.lydone.okna_service_android_app.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

@RequiresApi(Build.VERSION_CODES.N)
@HiltViewModel
class ArMeasurementViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private var redModelRenderable: ModelRenderable? = null

    private var greenModelRenderable: ModelRenderable? = null

    private var topLeftCornerNode: AnchorNode? = null

    private var topRightCornerNode: AnchorNode? = null

    private var bottomRightCornerNode: AnchorNode? = null

    private val addCornerButtonTextResMutableLiveData = MutableLiveData(R.string.point_top_left_corner)
    val addCornerButtonTextResLiveData: LiveData<Int> get() = addCornerButtonTextResMutableLiveData

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
            when {
                topLeftCornerNode == null -> {
                    topLeftCornerNode = cornerNode
                    addCornerButtonTextResMutableLiveData.value = R.string.point_top_right_corner
                }
                topRightCornerNode == null -> {
                    topRightCornerNode = cornerNode
                    addCornerButtonTextResMutableLiveData.value = R.string.point_bottom_right_corner
                }
                bottomRightCornerNode == null -> {
                    bottomRightCornerNode = cornerNode
                    isAddCornerButtonVisibleMutableLiveData.value = false
                }
            }
        }

    fun onRemovePreviousPointButtonClicked() = when {
        bottomRightCornerNode != null -> {
            bottomRightCornerNode?.let {
                isAddCornerButtonVisibleMutableLiveData.value = true
                addCornerButtonTextResMutableLiveData.value = R.string.point_bottom_right_corner
                bottomRightCornerNode = null
                it
            }
        }
        topRightCornerNode != null -> {
            topRightCornerNode?.let {
                addCornerButtonTextResMutableLiveData.value = R.string.point_top_right_corner
                topRightCornerNode = null
                it
            }
        }
        topLeftCornerNode != null -> {
            topLeftCornerNode?.let {
                addCornerButtonTextResMutableLiveData.value = R.string.point_top_left_corner
                topLeftCornerNode = null
                it
            }
        }
        else -> {
            null
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

    fun calculateWindowWidth() =
        calculateDistanceBetweenAnchorNodes(requireNotNull(topLeftCornerNode), requireNotNull(topRightCornerNode))

    fun calculateWindowHeight() =
        calculateDistanceBetweenAnchorNodes(requireNotNull(bottomRightCornerNode), requireNotNull(topRightCornerNode))

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
    }
}