package com.shinjaehun.spinnercolorpicker

class ColorObject(var name: String, var hex: String, var contrastHex: String ) {
    var hexHash: String = "#$hex"
    var contrastHexHash : String = "#$contrastHex"
}