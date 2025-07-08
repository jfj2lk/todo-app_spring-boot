import { Trash2 } from "lucide-react";
import { ReactNode } from "react";
import { EntityActionButton } from "./EntityActionButton";

const DeleteEntityButton = (props: { children?: ReactNode; entity: any }) => {
  return (
    <EntityActionButton mode="DELETE" entity={props.entity}>
      {props.children ?? <Trash2 />}
    </EntityActionButton>
  );
};

export { DeleteEntityButton };
