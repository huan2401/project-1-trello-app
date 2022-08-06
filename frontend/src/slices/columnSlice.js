import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import columnService from "service/column.service";

const initialState = {
  data: [],
};
export const findAllColumnByBoard = createAsyncThunk(
  "column/findAllColumnByBoard",
  async (boardId, { rejectWithValue }) => {
    try {
      const response = await columnService.findAllColumnByBoard(boardId);
      return response.data;
    } catch (error) {
      return rejectWithValue();
    }
  }
);

const columnSlice = createSlice({
  name: "column",
  initialState,
  reducers: {},
  extraReducers: {
    [findAllColumnByBoard.fulfilled]: (state, action) => {
      state.data = [...action.payload];
    },
    [findAllColumnByBoard.rejected]: (state, action) => {
      state.data = [];
    },
  },
});

const { reducer } = columnSlice;
// export const {  } = boardSlice.actions;
export default reducer;
