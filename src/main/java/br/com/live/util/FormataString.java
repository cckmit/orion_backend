package br.com.live.util;

import org.apache.commons.text.StringEscapeUtils;

import java.nio.charset.StandardCharsets;

public class FormataString {

    // Converte String para representação UTF8 - Não utilizar em String que contém tag html.
    public static String convertUtf8(String text){
        return StringEscapeUtils.escapeHtml4(text);
    }

    // Converte String para representação UTF8 - Utilizar quando possuir tag html..
    public static String convertUtf8Html(String text){
        return new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
