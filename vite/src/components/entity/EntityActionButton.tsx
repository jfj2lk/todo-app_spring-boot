import { Button } from "@/components/ui/button";
import { ReactNode } from "react";
import { EntityModal } from "./EntityModal";

const EntityActionButton = (props: {
  children: ReactNode;
  mode: "CREATE" | "UPDATE" | "DELETE";
  entity?: any;
}) => {
  return (
    <EntityModal mode={props.mode} entity={props.entity}>
      <Button variant={"ghost"} size={"icon"} className="trigger-btn">
        {props.children}
      </Button>
    </EntityModal>
  );
};

export { EntityActionButton };
