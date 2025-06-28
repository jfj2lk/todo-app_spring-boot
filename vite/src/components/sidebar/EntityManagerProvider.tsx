import { useAppDispatch } from "@/store";
import { createContext, ReactNode, useContext, useEffect } from "react";
import z, { ZodObject, ZodRawShape } from "zod";

type EntityManagerPropsContextType = {
  entities: any[];
  getAllEntities: Function;
  createEntity: Function;
  updateEntity: Function;
  deleteEntity: Function;
  formSchema: ZodObject<ZodRawShape>;
  defaultFormValues: Record<string, any>;
  entityName: string;
  entityIcon: ReactNode;
};

const EntityManagerPropsContext = createContext<EntityManagerPropsContextType>({
  entities: [],
  getAllEntities: Function,
  createEntity: Function,
  updateEntity: Function,
  deleteEntity: Function,
  formSchema: z.object({}),
  defaultFormValues: {},
  entityName: "",
  entityIcon: "",
});

export const useEntityManagerPropsContext = () =>
  useContext(EntityManagerPropsContext);

export type modeType = "CREATE" | "UPDATE" | "DELETE";

const EntityManagerProvider = (
  props: EntityManagerPropsContextType & { children: ReactNode },
) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    const handleGetAllEntities = () => {
      dispatch(props.getAllEntities());
    };
    handleGetAllEntities();
  }, []);

  return (
    <EntityManagerPropsContext.Provider value={props}>
      {props.children}
    </EntityManagerPropsContext.Provider>
  );
};

export { EntityManagerProvider };
