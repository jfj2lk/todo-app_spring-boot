import { ChevronDown } from "lucide-react";
import { EntityIcon } from "../entity/button/EntityIcon";

const CollapsibleTriggerButton = () => {
  return (
    <EntityIcon>
      <ChevronDown className="transition-transform group-data-[state=open]/collapsible:rotate-180" />
    </EntityIcon>
  );
};

export { CollapsibleTriggerButton };
