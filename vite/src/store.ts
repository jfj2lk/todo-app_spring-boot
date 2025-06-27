import { configureStore } from "@reduxjs/toolkit";
import { useDispatch, useSelector, useStore } from "react-redux";
import { labelReducer } from "./store/labelStore";
import { projectReducer } from "./store/project-store";

export const store = configureStore({
  reducer: {
    labels: labelReducer,
    projects: projectReducer,
  },
});

export type AppStore = typeof store;
export type RootState = ReturnType<AppStore["getState"]>;
export type AppDispatch = AppStore["dispatch"];
export const useAppSelector = useSelector.withTypes<RootState>();
export const useAppDispatch = useDispatch.withTypes<AppDispatch>();
export const useAppStore = useStore.withTypes<AppStore>();
