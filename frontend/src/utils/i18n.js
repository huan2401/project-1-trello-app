
export const getCurrentLanguage = () => {
    return localStorage.getItem("i18n");
};

export const changeLanguage = (language) => {
    if (language === getCurrentLanguage()) return;
    localStorage.setItem("i18n", language);
    window.location.reload();
};