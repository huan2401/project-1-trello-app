import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import taskService from "service/task.service";

const initialState = {
  allTaskByColumn: [],
  task: {},
};
export const findAllTaskByColumn = createAsyncThunk(
  "task/findAllTaskByColumn",
  async (column_id, { rejectWithValue }) => {
    try {
      const response = await taskService.findAllTaskByColumn(column_id);
      return response.data;
    } catch (error) {
      return rejectWithValue();
    }
  }
);

export const findByTaskId = createAsyncThunk(
  "task/findByTaskId",
  async (task_id, { rejectWithValue }) => {
    try {
      const response = await taskService.findByTaskId(task_id);
      return response.data;
    } catch (error) {
      return rejectWithValue();
    }
  }
);

const taskSlice = createSlice({
  name: "task",
  initialState,
  reducers: {},
  extraReducers: {
    [findAllTaskByColumn.fulfilled]: (state, action) => {
      state.allTaskByColumn = [...action.payload];
    },
    [findAllTaskByColumn.rejected]: (state, action) => {
      state.allTaskByColumn = [];
    },

    [findByTaskId.fulfilled]: (state, action) => {
      state.task = { ...action.payload };
    },
    [findByTaskId.rejected]: (state, action) => {
      state.task = {};
    },
  },
});

const { reducer } = taskSlice;
// export const {  } = boardSlice.actions;
export default reducer;
