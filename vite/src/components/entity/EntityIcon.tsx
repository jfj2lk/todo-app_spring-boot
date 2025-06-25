import { useEntityManagerPropsContext } from "./logic/entity-context";

const EntityIcon = () => {
  const { entityIcon } = useEntityManagerPropsContext();

  return <div className="entity-manager_list-item_icon">{entityIcon}</div>;
};

export { EntityIcon };
