import { ReactNode } from "react";
import { modeType } from "../EntityManagerProvider";
import { EntityModal } from "../form/EntityModal";
import { EntityIcon } from "./EntityIcon";

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
