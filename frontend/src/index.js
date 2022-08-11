import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import "./index.less";
import reportWebVitals from "./reportWebVitals";
import { I18nextProvider } from "react-i18next";
import I18Provider from "providers/I18Provider";
import i18n from "i18n";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import store from "store";
import { PersistGate } from "redux-persist/integration/react";
import { persistStore } from "redux-persist";

import { ToastContainer } from "react-toastify";
import { injectStyle } from "react-toastify/dist/inject-style";
import "react-toastify/dist/ReactToastify.css";
if (typeof window !== "undefined") {
  injectStyle();
}

let persistor = persistStore(store);

ReactDOM.render(
  <BrowserRouter>
    <I18Provider>
      <Provider store={store}>
        <PersistGate loading={null} persistor={persistor}>
          {/* <Provider store={store}> */}
          <App />
          <ToastContainer />
          {/* </Provider> */}
        </PersistGate>
      </Provider>
    </I18Provider>
  </BrowserRouter>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
