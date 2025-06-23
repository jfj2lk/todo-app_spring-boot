import { EntityType } from "@/components/entity/EntityManager";
import { RootState } from "@/store";
import { createEntityAdapter, createSlice } from "@reduxjs/toolkit";
import {
  createEntity,
  deleteEntity,
  getAllEntities,
  updateEntity,
} from "./entityApi";

const entityAdapter = createEntityAdapter<EntityType>();
const initialState = entityAdapter.getInitialState();

export const entitySlice = createSlice({
  name: "entities",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder.addCase(getAllEntities.fulfilled, (state, action) => {
      console.log(state, action);
      entityAdapter.setAll(state, action);
    });

    builder.addCase(createEntity.fulfilled, (state, action) => {
      console.log(state, action);
      entityAdapter.addOne(state, action);
    });

    builder.addCase(updateEntity.fulfilled, (state, action) => {
      console.log(state, action);
      entityAdapter.setOne(state, action);
    });

    builder.addCase(deleteEntity.fulfilled, (state, action) => {
      console.log(state, action);
      entityAdapter.removeOne(state, action);
    });
  },
});

export const entityReducer = entitySlice.reducer;

export const entitySelectors = entityAdapter.getSelectors(
  (state: RootState) => state.entities,
);
