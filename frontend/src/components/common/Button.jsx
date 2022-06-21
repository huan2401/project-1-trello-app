import React from "react";
import {Button} from 'antd';
import {useTranslation} from 'react-i18next';
import {getCurrentLanguage, changeLanguage} from 'utils/i18n';

function ButtonCustom() {

  const {t} = useTranslation();

  const handleChangeLg = () => {
    console.log(getCurrentLanguage())
    if (getCurrentLanguage() === "vi") {
      changeLanguage("en");
    } else {
      changeLanguage("vi");
    }
  }

  return (
    <>
      <Button type="primary">{t('button')}</Button>
      <br/>
      <br/>
      <Button type="primary" onClick={handleChangeLg}>Thay đổi ngôn ngữ</Button>
    </>
  );
}

export default ButtonCustom;
