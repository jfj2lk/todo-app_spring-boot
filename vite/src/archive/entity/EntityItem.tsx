import { DeleteEntityButton } from "./DeleteEntityButton";
import { EntityIcon } from "./EntityIcon";
import { EntityModal } from "./EntityModal";
import { EntityName } from "./EntityName";
import { useEntityManagerPropsContext } from "./logic/entity-context";
import { UpdateEntityButton } from "./UpdateEntityButton";

const EntityItem = (props: { entity: any }) => {
  const { entityIcon } = useEntityManagerPropsContext();

  return (
    <div className="entity-manager_list-item entity-manager_transition">
      <EntityModal mode="UPDATE" entity={props.entity}>
        <div className="entity-manager_list-item_info-group">
          <EntityIcon>{entityIcon}</EntityIcon>
          <EntityName name={props.entity.name} />
        </div>
      </EntityModal>

      <div className="entity-manager_list-item_action-group entity-manager_transition">
        <UpdateEntityButton entity={props.entity} />
        <DeleteEntityButton entity={props.entity} />
      </div>
    </div>
  );
};

export { EntityItem };
