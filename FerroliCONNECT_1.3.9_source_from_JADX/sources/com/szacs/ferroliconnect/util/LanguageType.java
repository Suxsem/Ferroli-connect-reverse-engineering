package com.szacs.ferroliconnect.util;

public enum LanguageType {
    CHINESE(LanguageUtil.LOGOGRAM_CHINESE),
    ENGLISH("en"),
    THAILAND("th"),
    ITALY(LanguageUtil.LOGOGRAM_ITALY);
    
    private String language;

    private LanguageType(String str) {
        this.language = str;
    }

    public String getLanguage() {
        String str = this.language;
        return str == null ? "" : str;
    }
}
