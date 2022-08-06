import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import boardService from "service/board.service";

const initialState = {
  data: [],
};
export const findAllBoard = createAsyncThunk(
  "board/findAllBoard",
  async (_, { rejectWithValue }) => {
    try {
      const response = await boardService.findAllBoard();
      return response.data;
    } catch (error) {
      return rejectWithValue();
    }
  }
);

const boardSlice = createSlice({
  name: "board",
  initialState,
  reducers: {},
  extraReducers: {
    [findAllBoard.fulfilled]: (state, action) => {
      state.data = [...action.payload];
    },
    [findAllBoard.rejected]: (state, action) => {
      state.data = [];
    },
  },
});

const { reducer } = boardSlice;
// export const {  } = boardSlice.actions;
export default reducer;
