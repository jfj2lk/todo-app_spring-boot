import { RootState } from "@/store";
import { labelFormSchema, LabelType } from "@/types/label";
import {
  createAsyncThunk,
  createEntityAdapter,
  createSlice,
} from "@reduxjs/toolkit";
import axios from "axios";
import { z } from "zod";

// 全件取得
export const getAllLabels = createAsyncThunk("getAllLabels", () => {
  return axios.get("/api/labels").then((response) => response.data.data);
});

// 作成
export const createLabel = createAsyncThunk(
  "createLabel",
  (values: z.infer<typeof labelFormSchema>) => {
    return axios
      .post("/api/labels", values)
      .then((response) => response.data.data);
  },
);

// 更新
export const updateLabel = createAsyncThunk(
  "updateLabel",
  (payload: { id: number; values: z.infer<typeof labelFormSchema> }) => {
    console;
    return axios
      .patch(`/api/labels/${payload.id}`, payload.values)
      .then((response) => response.data.data);
  },
);

// 削除
export const deleteLabel = createAsyncThunk("deleteLabel", (id: number) => {
  return axios
    .delete(`/api/labels/${id}`)
    .then((response) => response.data.data);
});

const labelAdapter = createEntityAdapter<LabelType>();
const initialState = labelAdapter.getInitialState();
export const labelSlice = createSlice({
  name: "labels",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getAllLabels.fulfilled, (state, action) => {
      console.log(state, action);
      labelAdapter.setAll(state, action);
    });

    builder.addCase(createLabel.fulfilled, (state, action) => {
      console.log(state, action);
      labelAdapter.addOne(state, action);
    });

    builder.addCase(updateLabel.fulfilled, (state, action) => {
      console.log(state, action);
      labelAdapter.setOne(state, action);
    });

    builder.addCase(deleteLabel.fulfilled, (state, action) => {
      console.log(state, action);
      labelAdapter.removeOne(state, action);
    });
  },
});

export const labelReducer = labelSlice.reducer;
export const labelSelectors = labelAdapter.getSelectors(
  (state: RootState) => state.labels,
);
