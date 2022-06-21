import React from "react";
import { Navigate, useRoutes } from "react-router-dom";

import Login from "components/common/Login/Login";
import Signup from "components/common/Signup/Signup";
import Home from "containers/Home/Home";
import Setup from "components/common/Setup/Setup";

// const Home = lazy(() => import("containers/Home/Home"));

/*
 * If route has children, it's a parent menu (not link to any pages)
 * You can change permissions to your IAM's permissions
 */
export default function Router() {
    return useRoutes([
        {
            path: "login",
            element: <Login />,
        },
        {
            path: "signup",
            element: <Signup />,
        },
        {
            path: "/",
            element: <Home />,
        },
        {
            path: "/setup",
            element: <Setup />,
        },
    ]);
}
