package br.com.matreis.rendimentodesoja.helper

fun Int.checkZeroOrValue() : String {
    return if(this == 0){
        ""
    }else{
        this.toString()
    }
}

fun Double.convertInchesToMeters(): Double {
    return this / 39.37
}

fun Double.convertLbToKg(): Double {
    return this / 2.205
}

fun Double.convertFeetToMeters(): Double {
    return this / 3.28
}

fun Double.convertMetersToFeet(): Double {
    return this * 3.28
}

fun Double.convertEstimateToImperialSystem(): Double {
    return this / 67.25
}

fun Double.convertEstimateToMetricSystem(): Double {
    return this * 67.25
}

fun Double.convertMetersToInches(): Double {
    return this * 39.37
}

fun Double.convertKgToLb(): Double {
    return this * 2.205

}
