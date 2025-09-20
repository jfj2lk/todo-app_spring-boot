import { BaseButton } from "@/components/common/BaseButton";
import { ReactNode } from "react";
import { modeType } from "../EntityManagerProvider";
import { EntityModal } from "../form/EntityModal";

const EntityActionButton = (props: {
  children: ReactNode;
  mode: modeType;
  entity?: any;
}) => {
  return (
    <EntityModal mode={props.mode} entity={props.entity}>
      <BaseButton>{props.children}</BaseButton>
    </EntityModal>
  );
};

export { EntityActionButton };
