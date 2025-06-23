import { getAllEntities } from "@/reducer/entityApi";
import { entitySelectors } from "@/reducer/entitySlice";
import { useAppDispatch, useAppSelector } from "@/store";
import { Circle } from "lucide-react";
import { useEffect } from "react";
import { EntityHeader } from "./EntityHeader";
import { EntityList } from "./EntityList";
import "./entity-manager.css";

export type EntityType = {
  id: number;
  name: string;
  description: string;
};

export const entityObject: EntityType = {
  id: 0,
  name: "",
  description: "",
};

const entityName = "Entity";
const entityIcon = <Circle />;

const EntityManager = () => {
  const entities = useAppSelector(entitySelectors.selectAll);
  const dispatch = useAppDispatch();

  useEffect(() => {
    const handleFetchAllEntities = () => {
      dispatch(getAllEntities());
    };
    handleFetchAllEntities();
  }, []);

  return (
    <div className="entity-manager">
      <EntityHeader title={entityName} entityName={entityName} />
      <EntityList
        entities={entities}
        icon={entityIcon}
        entityName={entityName}
      />
    </div>
  );
};

export { EntityManager };
