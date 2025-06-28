import { Plus } from "lucide-react";
import { EntityActionButton } from "./EntityActionButton";

const CreateEntityButton = () => {
  return (
    <EntityActionButton mode="CREATE">
      <Plus />
    </EntityActionButton>
  );
};

export { CreateEntityButton };
