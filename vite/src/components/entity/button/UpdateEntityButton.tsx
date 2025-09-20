import { Pencil } from "lucide-react";
import { ReactNode } from "react";
import { EntityActionButton } from "./EntityActionButton";

const UpdateEntityButton = (props: { children?: ReactNode; entity: any }) => {
  return (
    <EntityActionButton mode="UPDATE" entity={props.entity}>
      {props.children ?? <Pencil />}
    </EntityActionButton>
  );
};

export { UpdateEntityButton };
