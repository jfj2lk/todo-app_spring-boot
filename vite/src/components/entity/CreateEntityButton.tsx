import { Plus } from "lucide-react";

import { entityObject, EntityType } from "./EntityManager";
import { EntityTriggerButton } from "./EntityTriggerButton";

const CreateEntityButton = (props: {
  entityName: string;
  entity: EntityType;
}) => {
  return (
    <EntityTriggerButton
      mode="CREATE"
      entityName={props.entityName}
      main={""}
      entity={entityObject}
    >
      <Plus />
    </EntityTriggerButton>
  );
};

export { CreateEntityButton };
