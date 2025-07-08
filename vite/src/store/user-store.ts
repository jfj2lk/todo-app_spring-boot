import { RootState } from "@/store";
import { userFormSchema, UserType } from "@/types/user";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { z } from "zod";

// ユーザー取得
export const getUser = createAsyncThunk("user/getUser", async () => {
  const response = await axios.get("/api/auth/user");
  return response.data.data;
});

// ユーザー作成
export const createUser = createAsyncThunk(
  "user/createUser",
  async (values: z.infer<typeof userFormSchema>) => {
    const response = await axios.post("/api/auth/signup", values);
    return response.data.data;
  },
);

// ユーザー更新
export const updateUser = createAsyncThunk(
  "user/updateUser",
  async (payload: { id: number; values: z.infer<typeof userFormSchema> }) => {
    const response = await axios.patch("/api/auth/user", payload.values);
    return response.data.data;
  },
);

// ユーザー削除
export const deleteUser = createAsyncThunk("user/deleteUser", async () => {
  await axios.delete("/api/auth/user");
  return null;
});

// Slice
export const userSlice = createSlice({
  name: "user",
  initialState: null as UserType | null,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(getUser.fulfilled, (_, action) => action.payload)
      .addCase(updateUser.fulfilled, (_, action) => action.payload);
  },
});

export const userReducer = userSlice.reducer;
export const selectUser = (state: RootState) => state.user;
