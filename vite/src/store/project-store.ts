import { RootState } from "@/store";
import { projectFormSchema, ProjectType } from "@/types/project";
import {
  createAsyncThunk,
  createEntityAdapter,
  createSlice,
} from "@reduxjs/toolkit";
import axios from "axios";
import { z } from "zod";

// 全件取得
export const getAllProjects = createAsyncThunk("getAllProjects", () => {
  return axios.get("/api/projects").then((response) => response.data.data);
});

// 作成
export const createProject = createAsyncThunk(
  "createProject",
  (values: z.infer<typeof projectFormSchema>) => {
    return axios
      .post("/api/projects", values)
      .then((response) => response.data.data);
  },
);

// 更新
export const updateProject = createAsyncThunk(
  "updateProject",
  (payload: { id: number; values: z.infer<typeof projectFormSchema> }) => {
    console;
    return axios
      .patch(`/api/projects/${payload.id}`, payload.values)
      .then((response) => response.data.data);
  },
);

// 削除
export const deleteProject = createAsyncThunk("deleteProject", (id: number) => {
  return axios
    .delete(`/api/projects/${id}`)
    .then((response) => response.data.data);
});

const projectAdapter = createEntityAdapter<ProjectType>();
const initialState = projectAdapter.getInitialState();
export const projectSlice = createSlice({
  name: "projects",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getAllProjects.fulfilled, (state, action) => {
      console.log(state, action);
      projectAdapter.setAll(state, action);
    });

    builder.addCase(createProject.fulfilled, (state, action) => {
      console.log(state, action);
      projectAdapter.addOne(state, action);
    });

    builder.addCase(updateProject.fulfilled, (state, action) => {
      console.log(state, action);
      projectAdapter.setOne(state, action);
    });

    builder.addCase(deleteProject.fulfilled, (state, action) => {
      console.log(state, action);
      projectAdapter.removeOne(state, action);
    });
  },
});

export const projectReducer = projectSlice.reducer;
export const projectSelectors = projectAdapter.getSelectors(
  (state: RootState) => state.projects,
);
