import { ReactNode } from "react";
import { DeleteEntityButton } from "./DeleteEntityButton";
import { EditEntityButton } from "./EditEntityButton";
import { EntityIcon } from "./EntityIcon";
import { EntityType } from "./EntityManager";
import { EntityName } from "./EntityName";

const EntityItem = (props: { entity: EntityType; icon: ReactNode }) => {
  return (
    <div className="entity-manager_list-item">
      <div className="entity-manager_list-item_info-group">
        <EntityIcon icon={props.icon} />
        <EntityName name={props.entity.name} />
      </div>
      <div className="entity-manager_list-item_action-group">
        <EditEntityButton />
        <DeleteEntityButton />
      </div>
    </div>
  );
};

export { EntityItem };
