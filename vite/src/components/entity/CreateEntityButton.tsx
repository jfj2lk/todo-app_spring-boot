import { Plus } from "lucide-react";
import { EntityActionButton } from "./EntityActionButton";
import { useEntityManagerPropsContext } from "./logic/entity-context";

const CreateEntityButton = () => {
  const { createEntityDefaults } = useEntityManagerPropsContext();

  return (
    <EntityActionButton mode="CREATE" entity={createEntityDefaults}>
      <Plus />
    </EntityActionButton>
  );
};

export { CreateEntityButton };
