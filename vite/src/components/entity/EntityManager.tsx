import { useState } from "react";
import { EntityHeader } from "./EntityHeader";
import { EntityList } from "./EntityList";

type EntityType = {
  id: number;
  name: string;
  description: string;
};

const entityDatas: EntityType[] = [
  { id: 1, name: "entity1", description: "desc1" },
  { id: 2, name: "entity2", description: "desc2" },
  { id: 3, name: "entity3", description: "desc3" },
];

const EntityManager = () => {
  const [entities, setEntities] = useState<EntityType[]>(entityDatas);

  return (
    <div>
      <EntityHeader />
      <EntityList />
    </div>
  );
};

export { EntityManager };
