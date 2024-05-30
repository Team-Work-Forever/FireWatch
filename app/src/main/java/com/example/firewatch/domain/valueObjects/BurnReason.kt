package com.example.firewatch.domain.valueObjects

enum class BurnReason(val value: String) {
    SANITARY_BURN("sanitaryBurn"),
    AGRITORAL_WASTE_MANAGEMENT("agritoralWasteManagement"),
    FORESTRY_WASTE_MANAGEMENT("forestryWasteManagement"),
    BUSH_MANAGEMENT("bushManagement"),
    OTHERS("others");

    companion object {
        fun get(value: String): BurnReason? {
            return BurnReason.entries.find { it.value == value }
        }
    }
}