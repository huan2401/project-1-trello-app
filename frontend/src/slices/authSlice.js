import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import AuthService from "../service/auth.service";

const user = JSON.parse(localStorage.getItem("user"));

export const register = createAsyncThunk(
  "auth/register",
  async ({ userName, password, email, firstName, lastName, sex }, thunkAPI) => {
    try {
      const response = await AuthService.register(
        userName,
        password,
        email,
        firstName,
        lastName,
        sex
      );
      console.log("sign up data res", response);
      return response.data;
    } catch (error) {
      console.log("erorr register", error);
      return thunkAPI.rejectWithValue();
    }
  }
);

export const login = createAsyncThunk(
  "auth/login",
  async ({ username, password }, thunkAPI) => {
    try {
      console.log("username pass", username, password);
      const data = await AuthService.login(username, password);
      return { user: data };
    } catch (error) {
      console.log("erorr login", error);
      return thunkAPI.rejectWithValue();
    }
  }
);

export const updateProfile = createAsyncThunk(
  "auth/updateProfile",
  async ({ email, firstName, lastName, sex, phoneNumber }, thunkAPI) => {
    try {
      const data = await AuthService.updateProfile(
        email,
        firstName,
        lastName,
        sex,
        phoneNumber
      );
      return { user: data };
    } catch (error) {
      console.log("erorr updateProfile", error);
      return thunkAPI.rejectWithValue();
    }
  }
);

export const changePassword = createAsyncThunk(
  "auth/changePassword",
  async ({ passWord, previousPassword }, thunkAPI) => {
    try {
      const data = await AuthService.changePassword(passWord, previousPassword);
      return { user: data };
    } catch (error) {
      console.log("erorr changePassword", error);
      return thunkAPI.rejectWithValue();
    }
  }
);

const initialState = user
  ? { login: true, user }
  : { login: false, user: null };

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    checkLogin(state, action) {
      if (action) {
        localStorage.setItem("login", true);
        return {
          ...state,
          login: true,
        };
      } else {
        return {
          ...state,
          login: false,
        };
      }
    },
    logout(state) {
      localStorage.removeItem("token");
      localStorage.removeItem("login");
      return {
        ...state,
        login: false,
      };
    },
  },
  extraReducers: {
    [register.fulfilled]: (state, action) => {
      state.login = false;
    },
    [register.rejected]: (state, action) => {
      state.login = false;
    },
    [login.fulfilled]: (state, action) => {
      state.login = true;
      state.user = action.payload.user.data.forUser;
    },
    [login.rejected]: (state, action) => {
      state.login = false;
      state.user = null;
    },
    [updateProfile.fulfilled]: (state, action) => {
      console.log("action updateProfile", action.payload);
      state.user = action.payload.user.data;
    },
    [changePassword.fulfilled]: (state, action) => {
      console.log("action changePassword", action.payload);
      state.user = action.payload.user.data;
    },
    // [logout.fulfilled]: (state, action) => {
    //   state.login = false;
    //   state.user = null;
    // },
  },
});

const { reducer } = authSlice;
export const { checkLogin, logout } = authSlice.actions;
export default reducer;
