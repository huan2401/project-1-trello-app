import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import workSpaceService from "service/workspaceService";

// const initialState = {
//   id: undefined,
//   user: {
//     userId: undefined,
//     userName: "",
//     password: "",
//     activated: true,
//     verificationCode: "",
//     email: "",
//     firstName: "",
//     lastName: "",
//     sex: "",
//     avatarUrl: "",
//     phoneNumber: "",
//     region: "",
//   },
//   workspace: {
//     workspaceId: undefined,
//     workspaceTitle: "",
//     workspaceType: "",
//     visibility: "",
//     workspaceDescription: "",
//     deleted: false,
//   },
//   role: {
//     roleId: 1,
//     roleName: "",
//     roleType: "",
//     deleted: false,
//   },
//   deleted: false,
// };

const initialState = {
  data: [],
};
export const findWorkSpaceByUser = createAsyncThunk(
  "workSpace/findByUser",
  async (_, { rejectWithValue }) => {
    try {
      const response = await workSpaceService.findWorkSpaceByUser();
      return response.data;
    } catch (error) {
      return rejectWithValue();
    }
  }
);

const workSpaceSlice = createSlice({
  name: "workSpace",
  initialState,
  reducers: {},
  extraReducers: {
    [findWorkSpaceByUser.fulfilled]: (state, action) => {
      state.data = [...action.payload];
    },
    [findWorkSpaceByUser.rejected]: (state, action) => {
      state.data = [];
    },
  },
});

const { reducer } = workSpaceSlice;
// export const {  } = workSpaceSlice.actions;
export default reducer;
