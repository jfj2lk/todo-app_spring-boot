import { Plus } from "lucide-react";

import { EntityTriggerButton } from "./EntityTriggerButton";

const CreateEntityButton = (props: { entityName: string }) => {
  return (
    <EntityTriggerButton mode="CREATE" entityName={props.entityName} main={""}>
      <Plus />
    </EntityTriggerButton>
  );
};

export { CreateEntityButton };
