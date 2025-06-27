import { useEntityManagerPropsContext } from "./logic/entity-context";

const EntityTitle = () => {
  const { entityName } = useEntityManagerPropsContext();
  return <div className="entity-manager_header-title">{entityName}</div>;
};

export { EntityTitle };
