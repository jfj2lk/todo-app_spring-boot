import { CreateEntityButton } from "./CreateEntityButton";
import { EntityTitle } from "./EntityTitle";

const EntityHeader = () => {
  return (
    <div className="entity-manager_header">
      <EntityTitle />
      <CreateEntityButton />
    </div>
  );
};

export { EntityHeader };
