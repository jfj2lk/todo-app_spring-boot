import { ReactNode } from "react";
import { EntityIcon } from "./EntityIcon";
import { EntityModal } from "./EntityModal";

const EntityActionButton = (props: {
  children: ReactNode;
  mode: "CREATE" | "UPDATE" | "DELETE";
  entity?: any;
}) => {
  return (
    <EntityModal mode={props.mode} entity={props.entity}>
      <EntityIcon className="entity-manager_action-btn">
        {props.children}
      </EntityIcon>
    </EntityModal>
  );
};

export { EntityActionButton };
