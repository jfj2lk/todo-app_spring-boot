import { CreateEntityButton } from "./CreateEntityButton";
import { EntityTitle } from "./EntityTitle";

const EntityHeader = () => {
  return (
    <div>
      <EntityTitle />
      <CreateEntityButton />
    </div>
  );
};

export { EntityHeader };
