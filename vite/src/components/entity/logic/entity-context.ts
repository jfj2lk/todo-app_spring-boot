import { createContext, useContext } from "react";
import { z } from "zod";
import { EntityManagerPropsType } from "./entity-type";

export const EntityManagerPropsContext = createContext<EntityManagerPropsType>({
  entities: [],
  formSchema: z.object({}),
  getAllEntities: Function,
  createEntity: Function,
  updateEntity: Function,
  deleteEntity: Function,
  entityName: "",
  entityIcon: "",
  defaultFormValues: {},
});

export const useEntityManagerPropsContext = () =>
  useContext(EntityManagerPropsContext);
