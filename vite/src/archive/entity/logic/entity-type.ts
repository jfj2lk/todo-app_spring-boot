import { ReactNode } from "react";
import { ZodObject, ZodRawShape } from "zod";

export type EntityManagerPropsType = {
  entities: any[];
  formSchema: ZodObject<ZodRawShape>;
  defaultFormValues: Record<string, any>;
  getAllEntities: Function;
  createEntity: Function;
  updateEntity: Function;
  deleteEntity: Function;
  entityName: string;
  entityIcon: ReactNode;
};

export type modeType = "CREATE" | "UPDATE" | "DELETE";
