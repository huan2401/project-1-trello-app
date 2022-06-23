import { lazy } from "react";

const Home = lazy(() => import("containers/Home"));
// const Login = lazy(() => import("../components/common/Login/Login.js"));
// const Signup = lazy(() => import("../components/common/Signup/Signup"));

/*
 * If route has children, it's a parent menu (not link to any pages)
 * You can change permissions to your IAM's permissions
 */
const routes = [
    {
        path: "/login",
        title: "Login",
        component: Login,
    },
    // {
    //     path: "/signup",
    //     title: "Sign Up",
    //     component: Signup,
    // },
    // {
    //     path: "/",
    //     title: "Home",
    //     component: Home,
    // },
];

export default routes;
