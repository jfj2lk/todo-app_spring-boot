import { ReactNode } from "react";

const EntityIcon = (props: { icon: ReactNode }) => {
  return <div className="entity-manager_list-item_icon">{props.icon}</div>;
};

export { EntityIcon };
