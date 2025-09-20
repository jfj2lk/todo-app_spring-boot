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
    <EntityManagerPropsContext.Provider value={props}>
      <div className="entity-manager">
        <EntityHeader />
        <EntityList />
      </div>
    </EntityManagerPropsContext.Provider>
  );
};

export { EntityManager };
