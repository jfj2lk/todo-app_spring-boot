import { Button } from "@/components/ui/button";
import { ReactNode } from "react";
import { EntityType } from "./EntityManager";
import { EntityModal } from "./EntityModal";

const EntityTriggerButton = (props: {
  children: ReactNode;
  mode: "CREATE" | "UPDATE" | "DELETE";
  entity?: EntityType;
  entityName: string;
  main: ReactNode;
}) => {
  return (
    <EntityModal
      mode={props.mode}
      entity={props.entity}
      entityName={props.entityName}
      main={""}
    >
      <Button variant={"ghost"} size={"icon"} className="trigger-btn">
        {props.children}
      </Button>
    </EntityModal>
  );
};

export { EntityTriggerButton };
