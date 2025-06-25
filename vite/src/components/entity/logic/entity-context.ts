import { createContext, useContext } from "react";
import { EntityManagerPropsType } from "./entity-type";

export const EntityManagerPropsContext = createContext<EntityManagerPropsType>({
  entities: [],
  getAllEntities: Function,
  createEntity: Function,
  updateEntity: Function,
  deleteEntity: Function,
  entityName: "",
  entityIcon: "",
  createEntityDefaults: {},
});

export const useEntityManagerPropsContext = () =>
  useContext(EntityManagerPropsContext);
