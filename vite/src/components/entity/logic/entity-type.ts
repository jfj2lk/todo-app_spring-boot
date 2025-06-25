import { ReactNode } from "react";

export type EntityManagerPropsType = {
  entities: any[];
  getAllEntities: Function;
  createEntity: Function;
  updateEntity: Function;
  deleteEntity: Function;
  entityName: string;
  entityIcon: ReactNode;
  createEntityDefaults: Record<string, any>;
};

export type modeType = "CREATE" | "UPDATE" | "DELETE";
