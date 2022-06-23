import React, { useState, useEffect } from "react";
import "./App.less";
import { useSelector } from "react-redux";
import AuthLayout from "components/Layout/AuthLayout/AuthLayout";
import PublicLayout from "components/Layout/PublicLayout/PublicLayout";

function App() {
  useEffect(() => {
    window.localStorage.setItem("login", JSON.stringify(false));
  }, []);
  const isLogin = useSelector((state) => state.login.login);
  return <>{isLogin ? <AuthLayout /> : <PublicLayout />}</>;
}

export default App;
