import { CreateEntityButton } from "./CreateEntityButton";
import { EntityTitle } from "./EntityTitle";

const EntityHeader = (props: { title: string }) => {
  return (
    <div className="entity-manager_header">
      <EntityTitle>{props.title}</EntityTitle>
      <CreateEntityButton />
    </div>
  );
};

export { EntityHeader };
