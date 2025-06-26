import { useAppDispatch } from "@/store";
import { useEffect } from "react";
import { EntityHeader } from "./EntityHeader";
import { EntityList } from "./EntityList";
import "./entity-manager.css";
import { EntityManagerPropsContext } from "./logic/entity-context";
import { EntityManagerPropsType } from "./logic/entity-type";

const EntityManager = (props: EntityManagerPropsType) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    // 全てのEntityを取得
    const handleGetAllEntities = () => {
      dispatch(props.getAllEntities());
    };
    handleGetAllEntities();
  }, []);

  return (
    <EntityManagerPropsContext.Provider
      value={{
        entities: props.entities,
        formSchema: props.formSchema,
        getAllEntities: props.getAllEntities,
        createEntity: props.createEntity,
        updateEntity: props.updateEntity,
        deleteEntity: props.deleteEntity,
        entityName: props.entityName,
        entityIcon: props.entityIcon,
        defaultFormValues: props.defaultFormValues,
      }}
    >
      <div className="entity-manager">
        <EntityHeader />
        <EntityList />
      </div>
    </EntityManagerPropsContext.Provider>
  );
};

export { EntityManager };
