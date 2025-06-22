import { EntityType } from "@/components/entity/EntityManager";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

const entityDatas: EntityType[] = [
  { id: 1, name: "entity1", description: "desc1" },
  { id: 2, name: "entity2", description: "desc2" },
  { id: 3, name: "entity3", description: "desc3" },
];

export const entitySlice = createSlice({
  name: "entities",
  initialState: entityDatas as EntityType[],
  reducers: {
    initialized: (state, action: PayloadAction<EntityType[]>) => {
      return action.payload;
    },

    added: (state, action: PayloadAction<EntityType>) => {
      return [...state, action.payload];
    },

    updated: (state, action: PayloadAction<EntityType>) => {
      return state.map((todo) =>
        todo.id === action.payload.id ? action.payload : todo,
      );
    },

    deleted: (state, action: PayloadAction<number>) => {
      return state.filter((todo) => todo.id !== action.payload);
    },
  },
});

export const entityActions = entitySlice.actions;
export const entityReducer = entitySlice.reducer;
