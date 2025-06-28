import { ReactNode } from "react";
import { EntityIcon } from "./EntityIcon";
import { modeType } from "./EntityManagerProvider";
import { EntityModal } from "./EntityModal";

const EntityActionButton = (props: {
  children: ReactNode;
  mode: modeType;
  entity?: any;
}) => {
  return (
    <EntityModal mode={props.mode} entity={props.entity}>
      <EntityIcon>{props.children}</EntityIcon>
    </EntityModal>
  );
};

export { EntityActionButton };
