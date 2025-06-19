import { Trash2 } from "lucide-react";
import { TriggerButton } from "./EntityManager";

const DeleteEntityButton = () => {
  return (
    <TriggerButton>
      <Trash2 />
    </TriggerButton>
  );
};

export { DeleteEntityButton };
