import { combineReducers, configureStore } from "@reduxjs/toolkit";
import storage from "redux-persist/lib/storage";
import { persistReducer, persistStore } from "redux-persist";
import authReducer from "./slices/authSlice";
import workSpaceReducer from "./slices/workspaceSlice";
import boardReducer from "slices/boardSlice";
import columnReducer from "slices/columnSlice";
import taskReducer from "slices/taskSlice";
import thunk from "redux-thunk";

const reducers = combineReducers({
  auth: authReducer,
  workSpace:workSpaceReducer,
  board: boardReducer,
  column: columnReducer,
  task: taskReducer,
});

const persistConfig = {
  key: "root",
  storage,
  blacklist: ['workSpace','board','column','task']
};

const persistedReducer = persistReducer(persistConfig, reducers);

const store = configureStore({
  reducer: persistedReducer,
  // devTools: process.env.NODE_ENV !== "production",
  devTools: true,
  middleware: [thunk],
});
export default store;
