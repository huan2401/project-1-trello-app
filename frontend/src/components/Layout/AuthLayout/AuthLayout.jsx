import React, {Suspense} from "react";
import Header from "components/Layout/Header/Header";
import { useSelector } from "react-redux";
import { AuthRoute } from "routes";
import { useNavigate } from "react-router-dom";

const AuthLayout = () => {
  const navigate = useNavigate();
  const isLogin = useSelector((state) => state.login.login);
  if (!isLogin) {
    navigate("/login");
  }
  return (
    <>
      <Header />
      <Suspense fallback={null}>
        <AuthRoute />
      </Suspense>
    </>
  );
};
export default AuthLayout;
