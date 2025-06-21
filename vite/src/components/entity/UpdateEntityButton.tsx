import { Pencil } from "lucide-react";
import { EntityType } from "./EntityManager";
import { EntityTriggerButton } from "./EntityTriggerButton";

const UpdateEntityButton = (props: {
  entityName: string;
  entity: EntityType;
}) => {
  return (
    <EntityTriggerButton
      mode="UPDATE"
      entityName={props.entityName}
      main={""}
      entity={props.entity}
    >
      <Pencil />
    </EntityTriggerButton>
  );
};

export { UpdateEntityButton };
