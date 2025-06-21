import { ReactNode } from "react";
import { DeleteEntityButton } from "./DeleteEntityButton";
import { EntityIcon } from "./EntityIcon";
import { EntityType } from "./EntityManager";
import { EntityModal } from "./EntityModal";
import { EntityName } from "./EntityName";
import { UpdateEntityButton } from "./UpdateEntityButton";

const EntityItem = (props: {
  entity: EntityType;
  icon: ReactNode;
  entityName: string;
}) => {
  return (
    <div className="entity-manager_list-item">
      <EntityModal
        mode="UPDATE"
        entityName={props.entityName}
        entity={props.entity}
        main={""}
      >
        <div className="entity-manager_list-item_info-group">
          <EntityIcon icon={props.icon} />
          <EntityName name={props.entity.name} />
        </div>
      </EntityModal>

      <div className="entity-manager_list-item_action-group">
        <UpdateEntityButton
          entityName={props.entityName}
          entity={props.entity}
        />
        <DeleteEntityButton
          entityName={props.entityName}
          entity={props.entity}
        />
      </div>
    </div>
  );
};

export { EntityItem };
