package com.lydone.okna_service_android_app.presentation.common.windowrecyclerview

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.ViewHolderWindowBinding
import com.lydone.okna_service_android_app.domain.model.Window
import com.lydone.okna_service_android_app.presentation.cart.converter.GlassUnitTypeToStringResConverter
import com.lydone.okna_service_android_app.presentation.cart.converter.OptionsToStringConverter
import com.lydone.okna_service_android_app.presentation.cart.converter.SashTypeListToStringConverter
import com.lydone.okna_service_android_app.presentation.cart.converter.WindowTypeToStringResConverter
import com.lydone.okna_service_android_app.presentation.common.MaterialTypeToStringResConverter
import com.lydone.okna_service_android_app.presentation.common.WindowTypeToDrawableResConverter
import java.util.*

class WindowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderWindowBinding.bind(itemView)

    fun bind(window: Window, onChangeButtonClicked: ((Window) -> Unit)?, onDeleteButtonClicked: ((Window) -> Unit)?) {
        with(binding) {
            windowTypeTextView.text = with(itemView.context) {
                getString(
                    R.string.window_type_placeholder,
                    getString(WindowTypeToStringResConverter.convert(window.windowType))
                )
            }
            imageView.setImageResource(WindowTypeToDrawableResConverter.convert(window.windowType))
            dimensionsTextView.text =
                itemView.context.getString(R.string.width_height_placeholder, window.width, window.height)
            materialTypeTextView.text = with(itemView.context) {
                getString(
                    R.string.material_type_placeholder,
                    getString(MaterialTypeToStringResConverter.convertToTitleString(window.materialType))
                        .toLowerCase(Locale.getDefault())
                )
            }
            sashesTextView.text = itemView.context.getString(
                R.string.sashes_placeholder,
                SashTypeListToStringConverter.convert(itemView.context, window.sashes)
            )
            glassUnitTypeTextView.text = with(itemView.context) {
                getString(
                    R.string.glass_unit_type_placeholder,
                    getString(GlassUnitTypeToStringResConverter.convert(window.glassUnitType))
                        .toLowerCase(Locale.getDefault())
                )
            }
            optionsTextView.text = itemView.context.getString(
                R.string.options_placeholder, OptionsToStringConverter.convert(
                    context = itemView.context,
                    isWindowsillIncluded = window.isWindowsillIncluded,
                    isEbbIncluded = window.isEbbIncluded,
                    isSlopeIncluded = window.isSlopeIncluded,
                    isLaminationIncluded = window.isLaminationIncluded,
                    isMosquitoNetIncluded = window.isMosquitoNetIncluded
                )
            )
            changeButton.isVisible = onChangeButtonClicked != null
            deleteButton.isVisible = onDeleteButtonClicked != null
            changeButton.setOnClickListener { onChangeButtonClicked?.invoke(window) }
            deleteButton.setOnClickListener { onDeleteButtonClicked?.invoke(window) }
        }
    }
}