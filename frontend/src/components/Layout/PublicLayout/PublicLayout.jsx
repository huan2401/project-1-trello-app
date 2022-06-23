import React, { Suspense } from "react";
import { PublicRoute } from "routes";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const PublicLayout = () => {
  const navigate = useNavigate();
  const isLogin = useSelector((state) => state.login.login);
  if (isLogin) {
    navigate("/");
  }
  return (
    <>
      <Suspense fallback={null}>
        <PublicRoute />
      </Suspense>
    </>
  );
};
export default PublicLayout;
