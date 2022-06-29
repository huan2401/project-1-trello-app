import React, { useState, useEffect } from "react";
import "./App.less";
import { useSelector } from "react-redux";
import AuthLayout from "components/Layout/AuthLayout/AuthLayout";
import { Routes, Route } from "react-router-dom";
import Home from "containers/Home/Home";
import Setting from "containers/Setting/Setting";
import Login from "containers/Login/Login";
import Signup from "containers/Signup/Signup";
import NotFound from "containers/NotFound/NotFound";

function App() {
  useEffect(() => {}, []);
  const isLogin = useSelector((state) => state.auth.login);
  return (
    <Routes>
      <Route path="login" element={<Login />} />
      <Route path="signup" element={<Signup />} />

      <Route
        path="setting"
        element={
          <AuthLayout isAllowed={isLogin}>
            <Setting />
          </AuthLayout>
        }
      />
      <Route
        path="/"
        element={
          <AuthLayout isAllowed={isLogin}>
            <Home />
          </AuthLayout>
        }
      />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}

export default App;
