import { ReactNode } from "react";
import { EntityItem } from "./EntityItem";
import { EntityType } from "./EntityManager";

const EntityList = (props: { entities: EntityType[]; icon: ReactNode }) => {
  return (
    <div>
      {props.entities.map((entity) => (
        <EntityItem key={entity.id} entity={entity} icon={props.icon} />
      ))}
    </div>
  );
};

export { EntityList };
