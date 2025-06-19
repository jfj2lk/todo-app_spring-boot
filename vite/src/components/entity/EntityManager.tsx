import { Button } from "@/components/ui/button";
import { Circle } from "lucide-react";
import { ReactNode, useState } from "react";
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

const entityName = "Entity";
const entityIcon = <Circle />;

export const TriggerButton = (props: { children: ReactNode }) => {
  return (
    <Button variant={"ghost"} size={"icon"} className="trigger-btn">
      {props.children}
    </Button>
  );
};

const EntityManager = () => {
  const [entities, setEntities] = useState<EntityType[]>(entityDatas);

  return (
    <div className="entity-manager">
      <EntityHeader title={entityName} />
      <EntityList entities={entities} icon={entityIcon} />
    </div>
  );
};

export { EntityManager };
