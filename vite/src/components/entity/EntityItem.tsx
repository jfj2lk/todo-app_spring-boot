import { DeleteEntityButton } from "./DeleteEntityButton";
import { EditEntityButton } from "./EditEntityButton";
import { EntityIcon } from "./EntityIcon";
import { EntityName } from "./EntityName";

const EntityItem = () => {
  return (
    <div>
      <EntityIcon />
      <EntityName />
      <EditEntityButton />
      <DeleteEntityButton />
    </div>
  );
};

export { EntityItem };
