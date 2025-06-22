import { EntityType } from "@/components/entity/EntityManager";
import { RootState } from "@/store";
import { createEntityAdapter, createSlice } from "@reduxjs/toolkit";

const entityAdapter = createEntityAdapter<EntityType>();
const initialState = entityAdapter.getInitialState();

export const entitySlice = createSlice({
  name: "entities",
  initialState,
  reducers: {
    initialized: entityAdapter.setAll,
    added: entityAdapter.addOne,
    updated: entityAdapter.setOne,
    deleted: entityAdapter.removeOne,
  },
});

export const entityActions = entitySlice.actions;
export const entityReducer = entitySlice.reducer;

export const entitySelectors = entityAdapter.getSelectors(
  (state: RootState) => state.entities,
);
