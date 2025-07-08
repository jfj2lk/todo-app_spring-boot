import { RootState } from "@/store";
import { userFormSchema, UserType } from "@/types/user";
import {
  createAsyncThunk,
  createEntityAdapter,
  createSlice,
} from "@reduxjs/toolkit";
import axios from "axios";
import { z } from "zod";

// 全件取得
export const getUser = createAsyncThunk("getUser", () => {
  return axios.get("/api/auth/user").then((response) => response.data.data);
});

// 作成
export const createUser = createAsyncThunk(
  "createUser",
  (values: z.infer<typeof userFormSchema>) => {
    return axios
      .post("/api/auth/signup", values)
      .then((response) => response.data.data);
  },
);

// 更新
export const updateUser = createAsyncThunk(
  "updateUser",
  (payload: { id: number; values: z.infer<typeof userFormSchema> }) => {
    return axios
      .patch("/api/auth/user", payload.values)
      .then((response) => response.data.data);
  },
);

// 削除
export const deleteUser = createAsyncThunk("deleteUser", (id: number) => {
  return axios.delete("/api/auth/user").then((response) => response.data.data);
});

const userAdapter = createEntityAdapter<UserType>();
const initialState = userAdapter.getInitialState();
export const userSlice = createSlice({
  name: "users",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getUser.fulfilled, (state, action) => {
      userAdapter.setAll(state, action);
    });

    builder.addCase(createUser.fulfilled, (state, action) => {
      userAdapter.addOne(state, action);
    });

    builder.addCase(updateUser.fulfilled, (state, action) => {
      userAdapter.setOne(state, action);
    });

    builder.addCase(deleteUser.fulfilled, (state, action) => {
      userAdapter.removeOne(state, action);
    });
  },
});

export const userReducer = userSlice.reducer;
export const userSelectors = userAdapter.getSelectors(
  (state: RootState) => state.user,
);
