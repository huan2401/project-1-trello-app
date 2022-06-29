// import { createSlice } from "@reduxjs/toolkit";

// const initialState = { login: false };

// const loginSlice = createSlice({
//   name: "login",
//   initialState,
//   reducers: {
//     loginSuccess(state) {
//       window.localStorage.setItem("login", JSON.stringify(true));
//       return {
//         ...state,
//         login: true,
//       };
//     },
//     loginFailure(state) {
//       window.localStorage.setItem("login", JSON.stringify(false));
//       return {
//         ...state,
//         login: false,
//       };
//     },
//   },
// });

// export const { loginSuccess, loginFailure } = loginSlice.actions;
// export default loginSlice.reducer;


import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

import AuthService from "../service/auth.service";

const user = JSON.parse(localStorage.getItem("user"));

export const register = createAsyncThunk(
  "auth/register",
  async ({ username, email, password }, thunkAPI) => {
    try {
      const response = await AuthService.register(username, email, password);
      // thunkAPI.dispatch(setMessage(response.data.message));
      return response.data;
    } catch (error) {
      // const message =
      //   (error.response &&
      //     error.response.data &&
      //     error.response.data.message) ||
      //   error.message ||
      //   error.toString();
      // thunkAPI.dispatch(setMessage(message));
      console.log("erorr register", error);
      return thunkAPI.rejectWithValue();
    }
  }
);

export const login = createAsyncThunk(
  "auth/login",
  async ({ username, password }, thunkAPI) => {
    try {
      const data = await AuthService.login(username, password);
      return { user: data };
    } catch (error) {
      // const message =
      //   (error.response &&
      //     error.response.data &&
      //     error.response.data.message) ||
      //   error.message ||
      //   error.toString();
      // thunkAPI.dispatch(setMessage(message));
      console.log("erorr login", error);
      return thunkAPI.rejectWithValue();
    }
  }
);

export const logout = createAsyncThunk("auth/logout", async () => {
  await AuthService.logout();
});

const initialState = user
  ? { login: true, user }
  : { login: false, user: null };

const authSlice = createSlice({
  name: "auth",
  initialState,
  extraReducers: {
    [register.fulfilled]: (state, action) => {
      state.login = false;
    },
    [register.rejected]: (state, action) => {
      state.login = false;
    },
    [login.fulfilled]: (state, action) => {
      state.login = true;
      state.user = action.payload.user;
    },
    [login.rejected]: (state, action) => {
      state.login = false;
      state.user = null;
    },
    [logout.fulfilled]: (state, action) => {
      state.login = false;
      state.user = null;
    },
  },
});

const { reducer } = authSlice;
export default reducer;
