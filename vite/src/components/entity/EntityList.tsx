import { ReactNode } from "react";
import { EntityItem } from "./EntityItem";
import { EntityType } from "./EntityManager";

const EntityList = (props: {
  entities: EntityType[];
  icon: ReactNode;
  entityName: string;
}) => {
  return (
    <div>
      {props.entities.map((entity) => (
        <EntityItem
          key={entity.id}
          entity={entity}
          icon={props.icon}
          entityName={props.entityName}
        />
      ))}
    </div>
  );
};

export { EntityList };
