import { EntityItem } from "./EntityItem";
import { useEntityManagerPropsContext } from "./logic/entity-context";

const EntityList = () => {
  const { entities } = useEntityManagerPropsContext();
  return (
    <div>
      {entities.map((entity) => (
        <EntityItem key={entity.id} entity={entity} />
      ))}
    </div>
  );
};

export { EntityList };
