import { Pencil } from "lucide-react";
import { EntityActionButton } from "./EntityActionButton";

const UpdateEntityButton = (props: { entity: any }) => {
  return (
    <EntityActionButton mode="UPDATE" entity={props.entity}>
      <Pencil />
    </EntityActionButton>
  );
};

export { UpdateEntityButton };
