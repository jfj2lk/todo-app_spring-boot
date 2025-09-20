import { useAppDispatch } from "@/store";
import { createContext, ReactNode, useContext, useEffect } from "react";
import z, { ZodObject, ZodRawShape } from "zod";

export type EntityManagerPropsContextType = {
  entity: any;
  getEntity?: Function;
  createEntity?: Function;
  updateEntity?: Function;
  deleteEntity?: Function;
  formSchema: ZodObject<ZodRawShape>;
  defaultFormValues: Record<string, any>;
  labelName: string;
};

const EntityManagerPropsContext = createContext<EntityManagerPropsContextType>({
  entity: [],
  getEntity: Function,
  createEntity: Function,
  updateEntity: Function,
  deleteEntity: Function,
  formSchema: z.object({}),
  defaultFormValues: {},
  labelName: "",
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
      props.getEntity && dispatch(props.getEntity());
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
