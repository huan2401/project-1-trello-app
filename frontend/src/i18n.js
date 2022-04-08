import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import viTrans from 'assets/i18n/vi.json';
import enTrans from 'assets/i18n/en.json';
import { getCurrentLanguage } from "./utils/i18n";

i18n.use(initReactI18next).init({
    resources: {
        vi: { translation: viTrans },
        en: { translation: enTrans },
    },
    fallbackLng: getCurrentLanguage(),
    debug: true,

    interpolation: {
        escapeValue: false, // not needed for react as it escapes by default
    }
})

export default i18n;
