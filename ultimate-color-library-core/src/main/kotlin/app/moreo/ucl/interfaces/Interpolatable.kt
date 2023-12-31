package app.moreo.ucl.interfaces

import app.moreo.ucl.Color
import app.moreo.ucl.ColorInterpolation

/**
 * An interface to mark a color as interpolatable
 * @param T The color type
 */
interface Interpolatable<T: Color>: Color {

    /**
     * Kotlin range to operator for color interpolation
     * @param other The color to interpolate to
     * @return The color interpolation
     */
    operator fun rangeTo(other: Color): ColorInterpolation<T>
}