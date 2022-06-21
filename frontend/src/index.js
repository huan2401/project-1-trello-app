import React from "react";
import ReactDOM from "react-dom";
import "./index.less";
import "index.scss";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { I18nextProvider } from "react-i18next";
import I18Provider from "providers/I18Provider";
import i18n from "i18n";
import { BrowserRouter } from "react-router-dom";

ReactDOM.render(
    <BrowserRouter>
        <I18Provider>
            <App />
        </I18Provider>
    </BrowserRouter>,
    document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
