import { entityActions, entitySelectors } from "@/reducer/entitySlice";
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

const entityDatas: EntityType[] = [
  { id: 1, name: "entity1", description: "desc1" },
  { id: 2, name: "entity2", description: "desc2" },
  { id: 3, name: "entity3", description: "desc3" },
];

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
    const fetchAllEntities = () => {
      dispatch(entityActions.initialized(entityDatas));
    };
    fetchAllEntities();
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
