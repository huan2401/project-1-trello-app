import { createSlice } from "@reduxjs/toolkit";

const initialState = { login: false };

const loginSlice = createSlice({
  name: "login",
  initialState,
  reducers: {
    loginSuccess(state) {
      window.localStorage.setItem("login", JSON.stringify(true));
      return {
        ...state,
        login: true,
      };
    },
    loginFailure(state) {
      window.localStorage.setItem("login", JSON.stringify(false));
      return {
        ...state,
        login: false,
      };
    },
  },
});

export const { loginSuccess, loginFailure } = loginSlice.actions;
export default loginSlice.reducer;
