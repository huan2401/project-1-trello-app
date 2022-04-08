import React from 'react';
import PropTypes from 'prop-types';
import i18n from 'i18n';
import { I18nextProvider } from 'react-i18next';

const I18Provider = ({ children }) => (
    <>
        <I18nextProvider i18n={i18n}>{children}</I18nextProvider>
    </>
);

I18Provider.propTypes = {
    children: PropTypes.any,
};

export default I18Provider;