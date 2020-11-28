package io.davidosemwota.cowrywiseconverter.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import io.davidosemwota.cowrywiseconverter.R
import io.davidosemwota.cowrywiseconverter.databinding.FakeSpinnerBinding

/**
 * A ViewGroup that imitates the look of a spinner and wraps it with
 * [MaterialCardView].
 */
class FakeSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var binding: FakeSpinnerBinding = FakeSpinnerBinding
        .inflate(LayoutInflater.from(context))
    companion object {
        private const val DEFAULT_ICON_BACKGROUND = Color.MAGENTA
    }

    var iconColor = DEFAULT_ICON_BACKGROUND

    var spinnerText = ""
        set(value) {
            field = value

            binding.spinnerText.text = value
        }

    init {
        addView(binding.root)

        context.theme.obtainStyledAttributes(attrs, R.styleable.FakeSpinner, 0, 0)
            .apply {

                try {
                    iconColor = getColor(
                        R.styleable.FakeSpinner_fake_spinner_icon_bacground,
                        DEFAULT_ICON_BACKGROUND
                    )
                    spinnerText = getString(R.styleable.FakeSpinner_fake_spinner_text).toString()

                    binding.spinnerText.text = spinnerText
                    binding.spinnerIcon.setBackgroundColor(iconColor)
                } finally {
                    recycle()
                }
            }
    }
}
