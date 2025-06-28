import { Trash2 } from "lucide-react";
import { EntityActionButton } from "./EntityActionButton";

const DeleteEntityButton = (props: { entity: any }) => {
  return (
    <EntityActionButton mode="DELETE" entity={props.entity}>
      <Trash2 />
    </EntityActionButton>
  );
};

export { DeleteEntityButton };
