package com.example.notebook.feature_notebook.presentation.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
        var output = ""
        var x = 0;
        while (x < trimmed.length) {
            if (x == 0 && (trimmed[x] == '8' || trimmed[x] == '7')) {
                output += "+7 ("
                x++
            }
            else {
                if(x==0) output +="+"
                output += trimmed[x]
                x++
                if(x==1) output += " ("
                if (x == 4) output += ") "
                if (x == 7 || x == 9) output += "-"
            }

        }


        val phoneNumberTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return offset
                if (offset <= 3) return offset + 3
                if (offset <= 6) return offset + 5
                if (offset <= 8) return offset + 6
                if (offset <= 10) return offset + 7
                return 18
            }

            override fun transformedToOriginal(offset: Int): Int {
                /*
                if (offset <=2) return offset
                if (offset <= 4) return offset - 1
                if (offset <= 8) return offset - 4
                if (offset <= 11) return offset - 6
                if (offset <= 14) return offset - 7
                 */
                return 11
            }
        }

        return TransformedText(
            AnnotatedString(output),
            phoneNumberTranslator
        )
    }
}