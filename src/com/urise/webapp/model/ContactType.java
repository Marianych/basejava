package com.urise.webapp.model;

public enum ContactType {
    PHONE("тел.:"),
    MOBILE("Мобильный номер:"),
    HOMEPHONE("Домашний номер:"),
    SKYPE("Skype"),
    EMAIL("Электронная почта:"),
    LINKEDIN("Профиль LinkedIn:"),
    GITHUB("Профиль GitHub:"),
    STACKOVERFLOW("Профиль StackOverflow:"),
    HOMEPAGE("Домашняя страница:");

    private String title;

    public String getTitle() {
        return title;
    }

    ContactType(String title) {
        this.title = title;
    }
}
