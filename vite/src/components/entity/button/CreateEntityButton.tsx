import { Plus } from "lucide-react";
import { ReactNode } from "react";
import { EntityActionButton } from "./EntityActionButton";

const CreateEntityButton = (props: { children?: ReactNode }) => {
  return (
    <EntityActionButton mode="CREATE">
      {props.children ?? <Plus />}
    </EntityActionButton>
  );
};

export { CreateEntityButton };
