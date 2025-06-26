import { ReactNode } from "react";
import { ZodObject, ZodRawShape } from "zod";

export type EntityManagerPropsType = {
  entities: any[];
  formSchema: ZodObject<ZodRawShape>;
  getAllEntities: Function;
  createEntity: Function;
  updateEntity: Function;
  deleteEntity: Function;
  entityName: string;
  entityIcon: ReactNode;
  defaultFormValues: Record<string, any>;
};

export type modeType = "CREATE" | "UPDATE" | "DELETE";
