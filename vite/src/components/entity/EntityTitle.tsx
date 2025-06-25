import { useEntityManagerPropsContext } from "./logic/entity-context";

const EntityTitle = () => {
  const { entityName } = useEntityManagerPropsContext();
  return <div>{entityName}</div>;
};

export { EntityTitle };
